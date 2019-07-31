package io.renren.modules.resource.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.resource.entity.ResourceCatalogEntity;
import io.renren.modules.resource.service.ResourceCatalogService;
import io.renren.modules.resource.utils.POIUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
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
public class ResourceCatalogController {
    @Autowired
    private ResourceCatalogService resourceCatalogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("resource:resourcecatalog:list")
    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = resourceCatalogService.queryPage(params);
        List<ResourceCatalogEntity> list = resourceCatalogService.selectList(null);
        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catalogId}")
//    @RequiresPermissions("resource:resourcecatalog:info")
    public R info(@PathVariable("catalogId") Long catalogId){
        ResourceCatalogEntity resourceCatalog = resourceCatalogService.selectById(catalogId);

        return R.ok().put("resourceCatalog", resourceCatalog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("resource:resourcecatalog:save")
    public R save(@RequestBody ResourceCatalogEntity resourceCatalog){
        resourceCatalogService.insert(resourceCatalog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("resource:resourcecatalog:update")
    public R update(@RequestBody ResourceCatalogEntity resourceCatalog){
        ValidatorUtils.validateEntity(resourceCatalog);
        resourceCatalogService.updateAllColumnById(resourceCatalog);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("resource:resourcecatalog:delete")
    public R delete(@RequestBody Long[] catalogIds){
//        resourceCatalogService.deleteBatchIds(Arrays.asList(catalogIds));
        List<ResourceCatalogEntity> list = new ArrayList<ResourceCatalogEntity>();
        list = resourceCatalogService.selectBatchIds(Arrays.asList(catalogIds));
        if(list != null && list.size() > 0){
            for(ResourceCatalogEntity resourceCatalogEntity : list){
                resourceCatalogEntity.setIsDeleted(1);
            }
            resourceCatalogService.updateBatchById(list);
        }
        return R.ok();
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
            String filePath = getClass().getClassLoader().getResource("TAB1/" + pathName).getPath();
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
        String realPath = request.getSession().getServletContext().getRealPath(
                "/upload/excel");
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
        String oneName;
        String twoName;
        String threeName;
        String type;
        for (int i = 1; i < sheet.getLastRowNum()+1; i++) {
            row = sheet.getRow(i);
            ResourceCatalogEntity catalogEntity = new ResourceCatalogEntity();
            oneName = POIUtils.getCellValue(row.getCell(0)).replace(" ","");
            twoName = POIUtils.getCellValue(row.getCell(1)).replace(" ","");
            threeName = POIUtils.getCellValue(row.getCell(2)).replace(" ","");
            type = POIUtils.getCellValue(row.getCell(3)).replace(" ","");
            if("信息资源".equals(type)){
                catalogEntity.setType(0);
            }else{
                catalogEntity.setType(1);
            }
            catalogEntity.setRemark(POIUtils.getCellValue(row.getCell(4)).replace(" ",""));
            catalogEntity.setUpdateTime(new Date());
            resourceCatalogService.insertCatalog(oneName,twoName,threeName,catalogEntity);
        }
        return R.ok();
    }
    /**
     * 导入目录
     */
    @RequestMapping("/downCatalog")
    public R downCatalog(HttpServletResponse response, HttpServletRequest request){
        return R.ok();
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
}
