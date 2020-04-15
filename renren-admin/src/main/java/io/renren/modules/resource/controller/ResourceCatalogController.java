package io.renren.modules.resource.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.resource.entity.CatalogDeptEntity;
import io.renren.modules.resource.entity.CatalogUserEntity;
import io.renren.modules.resource.entity.ResourceCatalogEntity;
import io.renren.modules.resource.service.CatalogDeptService;
import io.renren.modules.resource.service.CatalogUserService;
import io.renren.modules.resource.service.ResourceCatalogService;
import io.renren.modules.resource.utils.POIUtils;
import io.renren.modules.resource.vm.GrantVM;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.*;


/**
 *
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-07-25 16:57:52
 */
@RestController
@RequestMapping("resource/resourcecatalog")
public class ResourceCatalogController extends AbstractController{
    @Autowired
    private ResourceCatalogService resourceCatalogService;
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysDeptService deptService;
    @Autowired
    private CatalogUserService catalogUserService;
    @Autowired
    private CatalogDeptService catalogDeptService;
    /**
     * 列表
     */
    @SysLog(type = "3", content = "目录")
    @RequestMapping("/list")
//    //@RequiresPermissions("resource:resourcecatalog:list")
    public List<ResourceCatalogEntity> list(@RequestParam Map<String, Object> params){
//        PageUtils page = resourceCatalogService.queryPage(params);
        String name = (String) params.get("name");
        EntityWrapper<ResourceCatalogEntity> wrapper = new EntityWrapper<ResourceCatalogEntity>();
        wrapper
                .like(StringUtils.isNotEmpty(name), "name", name)
                .eq("is_deleted",0);
//        List<ResourceCatalogEntity> list = resourceCatalogService.selectList(wrapper);
        List<ResourceCatalogEntity> list = resourceCatalogService.selectUserList(wrapper,getUserId(),getDeptId());
        for(ResourceCatalogEntity resourceCatalogEntity : list){
            ResourceCatalogEntity parentResourceCatalogEntity = resourceCatalogService.selectById(resourceCatalogEntity.getParentId());
            if(parentResourceCatalogEntity != null){
                resourceCatalogEntity.setParentName(parentResourceCatalogEntity.getName());
            }
        }
        return list;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catalogId}")
//    //@RequiresPermissions("resource:resourcecatalog:info")
    public R info(@PathVariable("catalogId") Long catalogId){
        ResourceCatalogEntity resourceCatalog = resourceCatalogService.selectById(catalogId);
        ResourceCatalogEntity parentResourceCatalogEntity = resourceCatalogService.selectById(resourceCatalog.getParentId());
        if(parentResourceCatalogEntity != null){
            resourceCatalog.setParentName(parentResourceCatalogEntity.getName());
        }
        return R.ok().put("resourceCatalog", resourceCatalog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    //@RequiresPermissions("resource:resourcecatalog:save")
    public R save(@RequestBody ResourceCatalogEntity resourceCatalog){
        resourceCatalog.setCreateUserId(getUserId());
        resourceCatalog.setCreateUserName(getUser().getName());
        resourceCatalog.setCreateTime(new Date());
        resourceCatalog.setUpdateTime(new Date());
        resourceCatalogService.insert(resourceCatalog);

        //添加权限
        List<ResourceCatalogEntity> catalogList = new ArrayList<ResourceCatalogEntity>();
        List<CatalogUserEntity> catalogUserList = new ArrayList<CatalogUserEntity>();
        catalogList = resourceCatalogService.selectParentCatalogList(catalogList,resourceCatalog);
        for(ResourceCatalogEntity catalog : catalogList){
            if(catalogUserService.selectCount(new EntityWrapper<CatalogUserEntity>().eq("user_id",getUserId()).eq("catalog_id",catalog.getCatalogId())) == 0){
                catalogUserList.add(new CatalogUserEntity(catalog.getCatalogId(),getUserId()));
            }
        }
        if(catalogUserList != null && catalogUserList.size() > 0){
            catalogUserService.insertBatch(catalogUserList);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    //@RequiresPermissions("resource:resourcecatalog:update")
    public R update(@RequestBody ResourceCatalogEntity resourceCatalog){
//        ValidatorUtils.validateEntity(resourceCatalog);
//        resourceCatalogService.updateAllColumnById(resourceCatalog);//全部更新
        resourceCatalog.setUpdateTime(new Date());
        resourceCatalogService.updateById(resourceCatalog);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    //@RequiresPermissions("resource:resourcecatalog:delete")
    public R delete(@RequestBody Long catalogId){
        ResourceCatalogEntity resourceCatalogEntity = resourceCatalogService.selectById(catalogId);
        resourceCatalogEntity.setIsDeleted(1);
        resourceCatalogService.updateById(resourceCatalogEntity);
        /*List<ResourceCatalogEntity> list = new ArrayList<ResourceCatalogEntity>();
        list = resourceCatalogService.selectBatchIds(Arrays.asList(catalogIds));
        if(list != null && list.size() > 0){
            for(ResourceCatalogEntity resourceCatalogEntity : list){
                resourceCatalogEntity.setIsDeleted(1);
            }
            resourceCatalogService.updateBatchById(list);
        }*/
        return R.ok();
    }

    /**
     * 统计
     */
    @RequestMapping("/stat")
    public R stat(){
        int catalogAll = resourceCatalogService.selectCount(new EntityWrapper<ResourceCatalogEntity>());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        int catalogNew = resourceCatalogService.selectCount(new EntityWrapper<ResourceCatalogEntity>().gt("create_time", zero));

        return R.ok().put("catalogAll",catalogAll).put("catalogNew",catalogNew);
    }

    /**
     * 下载目录模板
     */
    @RequestMapping("/downTemplate")
    public void downTemplate(HttpServletResponse response, HttpServletRequest request){
        try{
            String pathName = "catalogTemplate.xlsx";
            String fileName = "资源目录导入模板.xlsx";
            String fn = URLEncoder.encode(fileName,"UTF-8");
            response.setHeader("Content-disposition","attachment;fileName=" + new String(fn.getBytes("UTF-8"),"iso-8859-1").replace(" ","_"));
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            String filePath = getClass().getClassLoader().getResource("tab1/" + pathName).getPath();
            System.out.println("模板文件地址" + filePath);
            FileInputStream input = new FileInputStream(filePath);
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[2048];
            int len;
            while((len = input.read(b)) != -1){
                out.write(b,0,len);
            }
            response.setHeader("Conten-Length",String.valueOf(input.getChannel().size()));
        }catch (Exception ex){
            System.out.println(ex);
        }
    }
    /**
     * 导入目录
     */
    @RequestMapping("/importCatalog")
    public R importCatalog(@RequestParam(value="file",required = false) MultipartFile file, HttpServletResponse response, HttpServletRequest request) throws Exception{
        // 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中
        /*String realPath = request.getSession().getServletContext().getRealPath(
                "/upload/excel");*/
        String realPath = getClass().getClassLoader().getResource("upload").getPath();
        // 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
        File xlsFile = new File(realPath, file.getOriginalFilename());
        FileUtils.copyInputStreamToFile(file.getInputStream(), xlsFile);
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(xlsFile));
        } catch (Exception ex) {
            workbook = new XSSFWorkbook(new FileInputStream(xlsFile));
        }
        //得到Excel工作表对象
        XSSFSheet sheet = workbook.getSheetAt(0);
        if (sheet != null) {
            //得到Excel工作表的行 数sheet.getLastRowNum()也是得到Excel工作表的行数
            int rowcount = sheet.getPhysicalNumberOfRows();
            System.out.println("有数据" + rowcount);
        } else {
            System.out.println("没有数据");
        }
        XSSFRow row = null;
        System.out.println(sheet.getLastRowNum());
        List<ResourceCatalogEntity> list = new ArrayList<ResourceCatalogEntity>();
        String type;
        for (int i = 1; i < sheet.getLastRowNum()+1; i++) {
            row = sheet.getRow(i);
            ResourceCatalogEntity catalogEntity = new ResourceCatalogEntity();
            catalogEntity.setOneName(POIUtils.getCellValue(row.getCell(1)).replace(" ",""));
            catalogEntity.setTwoName(POIUtils.getCellValue(row.getCell(2)).replace(" ",""));
            catalogEntity.setThreeName(POIUtils.getCellValue(row.getCell(3)).replace(" ",""));
            type = POIUtils.getCellValue(row.getCell(4)).replace(" ","");
            if("资源目录".equals(type)){
                catalogEntity.setType(0);
            }else{
                catalogEntity.setType(1);
            }
            catalogEntity.setRemark(POIUtils.getCellValue(row.getCell(5)).replace(" ",""));
            SysUserEntity user = getUser();
            catalogEntity.setCreateUserId(user.getUserId());
            catalogEntity.setCreateUserName(user.getUsername());
            catalogEntity.setCreateDeptId(user.getDeptId());
            catalogEntity.setCreateDeptName(deptService.selectById(getDeptId()).getName());
            catalogEntity.setCreateTime(new Date());
            catalogEntity.setUpdateTime(new Date());
            catalogEntity.setUpdateTime(new Date());
            resourceCatalogService.insertCatalog(catalogEntity);
            //添加权限
            List<ResourceCatalogEntity> catalogList = new ArrayList<ResourceCatalogEntity>();
            List<CatalogUserEntity> catalogUserList = new ArrayList<CatalogUserEntity>();
            catalogList = resourceCatalogService.selectParentCatalogList(catalogList,catalogEntity);
            for(ResourceCatalogEntity catalog : catalogList){
                if(catalogUserService.selectCount(new EntityWrapper<CatalogUserEntity>().eq("user_id",getUserId()).eq("catalog_id",catalog.getCatalogId())) == 0){
                    catalogUserList.add(new CatalogUserEntity(catalog.getCatalogId(),getUserId()));
                }
            }
            if(catalogUserList != null && catalogUserList.size() > 0){
                catalogUserService.insertBatch(catalogUserList);
            }
        }
        xlsFile.delete();
        return R.ok();
    }

    /**
     * 导出：定义表格的表名，标题以及获取数据
     * @param session
     * @param response
     * @throws Exception
     */
    @RequestMapping("/downCatalog")
    public void downCatalog(HttpSession session, HttpServletResponse response,
                         HttpServletRequest request) throws Exception{
        List<ResourceCatalogEntity> catalogList = resourceCatalogService.selectList(new EntityWrapper<ResourceCatalogEntity>().ne("parent_id","0"));
        if(catalogList != null && catalogList.size() >0){
            String[] headers = {"编号","一级目录名称", "二级目录名称","三级目录名称","目录类型","描述"};
            //表名
            String fileName = "资源目录";
            //调用导出表的方法，参数分别是标题栏，数据，表名，响应对象
            exportExcel(headers,catalogList,fileName,response,request,session);
        }else{
            //导出模板
            downTemplate(response,request);
        }

    }
    /**
     * 导出信息方法
     */
    public void exportExcel(String[] headers,Collection<ResourceCatalogEntity> dataset,
                            String fileName,HttpServletResponse response,HttpServletRequest request,HttpSession session) throws Exception {
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(5000);
        // 生成一个表格
        Sheet sheet = workbook.createSheet(fileName);
        // 设置表格默认列宽度为25个字节
        sheet.setDefaultColumnWidth((short) 30);
        // 产生表格标题行
        Row row = sheet.createRow(0);
        //创建表头
        for (short i = 0; i < headers.length; i++) {
            //定义表格的列
            Cell cell = row.createCell(i);
            //将标题行的数据循环放入对应的单元格
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            //将获取到的标题放入相应的列中
            cell.setCellValue(text);
        }
        //插入数据
        Iterator<ResourceCatalogEntity> it = dataset.iterator();
        //设置行索引
        int index = 0;
        while (it.hasNext()) {
            index++;
            //创建数据行
            row = sheet.createRow(index);
            //获取数据
            ResourceCatalogEntity t = resourceCatalogService.selectCatalog((ResourceCatalogEntity) it.next());

            Cell c = row.createCell(0);
            c.setCellValue(index);
            for (short i = 1; i < headers.length + 1; i++) {
                Cell cell = row.createCell(i);
                if(i == 1){
                    cell.setCellValue(t.getOneName());
                }else if(i == 2){
                    cell.setCellValue(t.getTwoName());
                }else if(i == 3){
                    cell.setCellValue(t.getThreeName());
                }else if(i == 4){
                    if(t.getType() != null){
                        if(t.getType() == 0){
                            cell.setCellValue("资源目录");
                        }else{
                            cell.setCellValue("服务目录");
                        }
                    }
                }else if(i == 5){
                    if(t.getRemark() != null){
                        cell.setCellValue(t.getRemark());
                    }
                }
            }
        }
        getExportedFile(workbook, fileName, response, request, session);
    }

    /**
     * 方法说明: 指定路径下生成EXCEL文件 ，设置编码方式以及文件格式
     * @return
     */
    public void getExportedFile(SXSSFWorkbook workbook, String name,
                                HttpServletResponse response,HttpServletRequest request,HttpSession session) throws Exception {
        BufferedOutputStream fos = null;
        try {
            String fileName = name /*+ df.format(new Date()) */+ ".xlsx";
            String codedfilename = null;
            try {
                String agent = request.getHeader("USER-AGENT");
                if (null != agent && -1 != agent.indexOf("MSIE") || null != agent
                        && -1 != agent.indexOf("Trident")) {// ie
                    String fileNames = java.net.URLEncoder.encode(fileName, "UTF8");
                    codedfilename = name;
                } else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
                    codedfilename = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-disposition", "attachment;filename="+codedfilename);
            workbook.write(response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
    /**
     * 停用
     */
    @RequestMapping("/stop")
    public R stop(@RequestParam Long catalogId){
        ResourceCatalogEntity resourceCatalogEntity = resourceCatalogService.selectById(catalogId);
        if(resourceCatalogEntity != null){
            resourceCatalogEntity.setIsUsed(0);
            resourceCatalogService.updateById(resourceCatalogEntity);
            return R.ok();
        }else{
            return R.error("没有找到目标目录");
        }
    }
    /**
     * 启用
     */
    @RequestMapping("/start")
    public R start(@RequestParam Long catalogId){
        ResourceCatalogEntity resourceCatalogEntity = resourceCatalogService.selectById(catalogId);
        if(resourceCatalogEntity != null){
            resourceCatalogEntity.setIsUsed(1);
            resourceCatalogService.updateById(resourceCatalogEntity);
            return R.ok();
        }else{
            return R.error("没有找到目标目录");
        }
    }

    /**
     * 获取授权信息
     */
    @RequestMapping("/selectGrant")
    public R grant(@RequestParam Long userId){
        //获取之前授权的用户列表
        List<CatalogUserEntity> cataLogUserList = catalogUserService.selectList(new EntityWrapper<CatalogUserEntity>().eq("user_id",userId));
        //之前授权的用户id列表
        List<Long> catalogIdList = new ArrayList<Long>();
        for(CatalogUserEntity catalogUserEntity : cataLogUserList){
            catalogIdList.add(catalogUserEntity.getCatalogId());
        }
        return R.ok().put("catalogIdList",catalogIdList);
    }

    /**
     * 获取授权信息
     */
    @RequestMapping("/selectOgGrant")
    public R selectOgGrant(@RequestParam Long organisationId){
        //获取之前授权的用户列表
        List<CatalogDeptEntity> cataLogDeptList = catalogDeptService.selectList(new EntityWrapper<CatalogDeptEntity>().eq("organisation_id",organisationId));
        //之前授权的用户id列表
        List<Long> catalogIdList = new ArrayList<Long>();
        for(CatalogDeptEntity catalogDeptEntity : cataLogDeptList){
            catalogIdList.add(catalogDeptEntity.getCatalogId());
        }
        return R.ok().put("catalogIdList",catalogIdList);
    }

    /**
     * 授权
     */
    @RequestMapping("/grant")
    public R grant(@RequestBody GrantVM grantVM){
        if(grantVM.getUserId() != null){
            if(grantVM.getCatalogIdList() != null && grantVM.getCatalogIdList().size() > 0){
                catalogUserService.delete(new EntityWrapper<CatalogUserEntity>().eq("user_id",grantVM.getUserId()));
                catalogUserService.addBatch(grantVM.getCatalogIdList(),grantVM.getUserId());
            }else{
                catalogUserService.delete(new EntityWrapper<CatalogUserEntity>().eq("user_id",grantVM.getUserId()));
            }
        }
        return R.ok();
    }
    /**
     * 授权
     */
    @RequestMapping("/grantOg")
    public R grantOg(@RequestBody GrantVM grantVM){
        if(grantVM.getOrganisationId() != null){
            if(grantVM.getCatalogIdList() != null && grantVM.getCatalogIdList().size() > 0){
                catalogDeptService.delete(new EntityWrapper<CatalogDeptEntity>().eq("organisation_id",grantVM.getOrganisationId()));
                catalogDeptService.addBatch(grantVM.getCatalogIdList(),grantVM.getOrganisationId());
            }else{
                catalogDeptService.delete(new EntityWrapper<CatalogDeptEntity>().eq("organisation_id",grantVM.getOrganisationId()));
            }
        }
        return R.ok();
    }
}
