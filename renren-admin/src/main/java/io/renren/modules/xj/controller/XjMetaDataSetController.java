package io.renren.modules.xj.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.resource.utils.POIUtils;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.xj.entity.*;
import io.renren.modules.xj.service.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 元数据集表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
@RestController
@RequestMapping("xj/xjmetadataset")
public class XjMetaDataSetController extends AbstractController {
    @Autowired
    private XjMetaDataSetService xjMetaDataSetService;
    @Autowired
    private XjMetaDataService xjMetaDataService;
    @Autowired
    private XjMeteSetCategoryService xjMeteSetCategoryService;
    /**
     * 元数据集版本的
     */
    @Autowired
    private XjMeteSetVersionService xjMeteSetVersionService;
    /**
     * 中间表
     */
    @Autowired
    private XjMeteSetMiddleService xjMeteSetMiddleService;
    /**
     * 中间表的版本表
     */
    @Autowired
    private XjMeteSetMiddleVersionService xjMeteSetMiddleVersionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjmetadataset:list")
    public List<XjMetaDataSetEntity> list(@RequestParam Map<String, Object> params){
        List<XjMetaDataSetEntity> list = xjMetaDataSetService.selectList(null);
        return list;
    }

    /**
     * 条件查询（根据元数据分类编号或者分类名称）
     */
    @RequestMapping("/queryList")
    //@RequiresPermissions("resource:metecategory:list")
    public R queryList(@RequestParam Map<String, Object> params){
        PageUtils page=xjMetaDataSetService.searchFindByMeteDataSetNumberOrName(params);
            return R.ok().put("page",page);
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{meteSetId}")
    //@RequiresPermissions("xj:xjmetadataset:info")
    public R info(@PathVariable("meteSetId") Long meteSetId){
        XjMetaDataSetEntity xjMetaDataSet = xjMetaDataSetService.selectById(meteSetId);
        List<XjMeteSetMiddleEntity> aList= xjMeteSetMiddleService.selectList(new EntityWrapper<XjMeteSetMiddleEntity>().eq("mete_set_id",meteSetId));
        if(aList.size()>0&&aList!=null) {
            Set<Long> meteIds = new HashSet<>();
            for (XjMeteSetMiddleEntity a : aList) {
                meteIds.add(a.getMeteId());
            }
            List<XjMetaDataEntity> meteDataList = xjMetaDataService.selectBatchIds(meteIds);
            xjMetaDataSet.setMeteDataList(meteDataList);
        }
        return R.ok().put("xjMetaDataSet", xjMetaDataSet);
    }



    /**
     * 元数据集的历史版本查询
     */
    @RequestMapping("/historyInfo/{meteSetId}")
    //@RequiresPermissions("xj:xjmetadataset:info")
    public R historyInfo(@PathVariable("meteSetId") Long meteSetId){
        //先对历史版本进行判断
        List<XjMeteSetVersionEntity> hList= new ArrayList<>();
        hList= xjMeteSetVersionService.selectList(new EntityWrapper<XjMeteSetVersionEntity>().eq("mete_set_id",meteSetId));
        return R.ok().put("hList",hList);
    }














    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjmetadataset:save")
    public R save(@RequestBody XjMetaDataSetEntity xjMetaDataSet){
        /**
         * 保存元数据集的想换信息
         */
        XjMetaDataSetEntity xjMetaDataSetEntity=new XjMetaDataSetEntity();
        xjMetaDataSetEntity.setMeteCategorySetId(xjMetaDataSet.getMeteCategorySetId());
        xjMetaDataSetEntity.setCnName(xjMetaDataSet.getCnName());
        xjMetaDataSetEntity.setEuName(xjMetaDataSet.getEuName());
        xjMetaDataSetEntity.setEuShortName(xjMetaDataSet.getEuShortName());
        xjMetaDataSetEntity.setReviewState(xjMetaDataSet.getReviewState());
        xjMetaDataSet.setCurrentVersion("v1.0");
        xjMetaDataSet.setCreateDate(new Date());
        xjMetaDataSet.setUpdateTime(new Date());
        xjMetaDataSet.setCreateUserId(getUser().getUserId());
        xjMetaDataSetService.insert(xjMetaDataSetEntity);
        /**
         * 之后遍历元数据集下的元数据的信息，然后将元数据集与元数据的信息保存到中间表
         */
        List<XjMetaDataEntity> meteList = xjMetaDataSet.getMeteDataList();
        if(meteList != null && meteList.size() > 0){
            List<XjMeteSetMiddleEntity> aList = new ArrayList<>();
            for(XjMetaDataEntity mete : meteList){
                XjMeteSetMiddleEntity a = new XjMeteSetMiddleEntity();
                a.setMeteId(mete.getMeteId());
                a.setMeteSetId(xjMetaDataSet.getMeteSetId());
                a.setMeteSetCname(xjMetaDataSet.getCnName());
                a.setMeteSetEname(xjMetaDataSet.getEuName());
                a.setMeteSetEuShortName(xjMetaDataSet.getEuShortName());
                a.setMeteSetNumber(xjMetaDataSet.getMeteSetNumber());
                a.setMeteCname(mete.getCnName());
                a.setMeteEname(mete.getEuName());
                a.setMeteEuShortName(mete.getEuShortName());
                a.setMeteCname(mete.getMeteNumber());
                a.setMeteDataType(mete.getDataType());
                a.setMeteDataLength(mete.getDataLength());
                a.setMeteDefinition(mete.getDefinition());
                a.setMeteRange(mete.getRange());
                a.setMeteRangeDescription(mete.getRangeDescription());
                a.setCreateDate(new Date());
                a.setUpdateTime(new Date());
                a.setCreateUserId(getUser().getUserId());
                a.setVersionNumber("v1.0");
                aList.add(a);
            }
            xjMeteSetMiddleService.insertBatch(aList);
        }

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjmetadataset:update")
    public R update(@RequestBody XjMetaDataSetEntity xjMetaDataSet){
        //判断数量都从中间表判断
        //原元数据数量
        int count= xjMeteSetMiddleService.selectCount(new EntityWrapper<XjMeteSetMiddleEntity>().eq("mete_set_id",xjMetaDataSet.getMeteSetId()));
        //现元数据数量
        List<XjMetaDataEntity> medaList=xjMetaDataSet.getMeteDataList();
        int b;
        if(medaList==null){
            b=0;
        }else {
            b=medaList.size();
        }
        if(count!=b){
            //
            /**如果数量不同
             * 1.获取旧的元数据集模型
             * 2.将旧的元数据集模型保存到元数据集版本表中
             */
            XjMetaDataSetEntity oldSet = xjMetaDataSetService.selectById(xjMetaDataSet.getMeteSetId());
            XjMeteSetVersionEntity setVersion=new XjMeteSetVersionEntity();
            setVersion.setMeteSetId(oldSet.getMeteSetId());
            setVersion.setMeteSetNumber(oldSet.getMeteSetNumber());
            setVersion.setCnName(oldSet.getCnName());
            setVersion.setEuName(oldSet.getEuName());
            setVersion.setVersionNumber(oldSet.getCurrentVersion());
            setVersion.setMeteCategorySetId(oldSet.getMeteCategorySetId());
            setVersion.setCreateUserId(oldSet.getCreateUserId());
            setVersion.setCreateDate(oldSet.getCreateDate());
            setVersion.setUpdateTime(oldSet.getUpdateTime());
            xjMeteSetVersionService.insert(setVersion);
            /**保存新的元数据集模型。
             * 1.获取元数据集版本表中setid相等的数量
             * 2.设置当前版本
             */
            int c = xjMeteSetVersionService.selectCount(
                    new EntityWrapper<XjMeteSetVersionEntity>()
                            .eq("mete_set_id",xjMetaDataSet.getMeteSetId())
            );
            xjMetaDataSet.setCurrentVersion("v" + (c+1) + ".0");
            xjMetaDataSetService.updateById(xjMetaDataSet);
            /**
             * 1.获取旧的中间表模型
             * 2.将旧的中间表模型保存到中间表版本表中
             */
            List<XjMeteSetMiddleEntity> aList = xjMeteSetMiddleService.selectList(new EntityWrapper<XjMeteSetMiddleEntity>().eq("mete_set_id",xjMetaDataSet.getMeteSetId()));
            List<XjMeteSetMiddleVersionEntity> avList=new ArrayList<>();
            for(XjMeteSetMiddleEntity a : aList){
                XjMeteSetMiddleVersionEntity av = new XjMeteSetMiddleVersionEntity();
                av.setVersionId(a.getVersionId());
                av.setMeteId(a.getMeteId());
                av.setMeteSetId(a.getMeteSetId());
                av.setMeteCname(a.getMeteCname()==null?"":a.getMeteCname());
                av.setMeteEname(a.getMeteEname()==null?"":a.getMeteEname());
                av.setMeteEuShortName(a.getMeteEuShortName()==null?"":a.getMeteEuShortName());
                av.setMeteNumber(a.getMeteNumber()==null?"":a.getMeteNumber());
                av.setMeteDataType(a.getMeteDataType());
                av.setMeteDataLength(a.getMeteDataLength());
                av.setMeteRange(a.getMeteRange()==null?"":a.getMeteRange());
                av.setMeteRangeDescription(a.getMeteRangeDescription()==null?"":a.getMeteRangeDescription());
                av.setMeteDefinition(a.getMeteDefinition()==null?"":a.getMeteDefinition());
                av.setMeteSetCname(a.getMeteSetCname()==null?"":a.getMeteSetCname());
                av.setMeteSetEname(a.getMeteSetEname()==null?"":a.getMeteSetEname());
                av.setMeteSetEuShortName(a.getMeteSetEuShortName()==null?"":a.getMeteSetEuShortName());
                av.setMeteSetNumber(a.getMeteSetNumber()==null?"":a.getMeteSetNumber());
                av.setVersionNumber(a.getVersionNumber()==null?"":a.getVersionNumber());
                av.setCreateUserId(a.getCreateUserId());
                av.setCreateDate(a.getCreateDate());
                av.setUpdateTime(a.getUpdateTime());
                avList.add(av);
            }
            xjMeteSetMiddleVersionService.insertBatch(avList);
            /**保存新的中间表。
             * 1.先删除之前数据
             * 2.保存新的数据，版本号"v" + c+1 + ".0"
             */
            Map map = new HashMap();
            map.put("mete_set_id",xjMetaDataSet.getMeteSetId());
            xjMeteSetMiddleService.deleteByMap(map);
            List<XjMetaDataEntity> newList=xjMetaDataSet.getMeteDataList();
            for(XjMetaDataEntity xjMetaDataEntity:newList){
                XjMeteSetMiddleEntity middleEntity=new XjMeteSetMiddleEntity();
                middleEntity.setMeteId(xjMetaDataEntity.getMeteId());
                middleEntity.setMeteNumber(xjMetaDataEntity.getMeteNumber());
                middleEntity.setMeteCname(xjMetaDataEntity.getCnName());
                middleEntity.setMeteEname(xjMetaDataEntity.getEuName());
                middleEntity.setMeteEuShortName(xjMetaDataEntity.getEuShortName());
                middleEntity.setMeteDataType(xjMetaDataEntity.getDataType());
                middleEntity.setMeteDataLength(xjMetaDataEntity.getDataLength());
                middleEntity.setMeteRange(xjMetaDataEntity.getRange());
                middleEntity.setMeteRangeDescription(xjMetaDataEntity.getRangeDescription());
                middleEntity.setMeteDefinition(xjMetaDataEntity.getDefinition());
                middleEntity.setMeteSetId(xjMetaDataEntity.getMeteSetId());
                middleEntity.setMeteSetId(xjMetaDataEntity.getMeteSetId());
                middleEntity.setMeteSetCname(xjMetaDataSet.getCnName());
                middleEntity.setMeteSetEname(xjMetaDataSet.getEuName());
                middleEntity.setMeteSetEname(xjMetaDataSet.getMeteSetNumber());
                middleEntity.setMeteSetEuShortName(xjMetaDataSet.getEuShortName());
                middleEntity.setCreateDate(new Date());
                middleEntity.setUpdateTime(new Date());
                middleEntity.setCreateUserId(getUser().getUserId());
                middleEntity.setVersionNumber("v"+(c+1)+".0");

            }


        }

        xjMetaDataSetService.updateAllColumnById(xjMetaDataSet);//全部更新

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjmetadataset:delete")
    public R delete(@RequestBody Long[] meteSetIds){
        //删除元数据集
        xjMetaDataSetService.deleteBatchIds(Arrays.asList(meteSetIds));
        //删除元数据集关联的中间表
        xjMeteSetMiddleService.deleteBatchIds(Arrays.asList(meteSetIds));
        return R.ok();
    }
    /**
     * 下载目录模板
     */
    @RequestMapping("/downTemplate")
    public void downTemplate(HttpServletResponse response, HttpServletRequest request){
        try{
            String pathName = "fieldTemplate.xlsx";
            String fileName = "元数据集字段导入模板.xlsx";
            String fn = URLEncoder.encode(fileName,"UTF-8");
            response.setHeader("Content-disposition","attachment;fileName=" + new String(fn.getBytes("UTF-8"),"iso-8859-1").replace(" ","_"));
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            String filePath = getClass().getClassLoader().getResource("tab1/" + pathName).getPath();
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
     *导入
     */
    @RequestMapping("/importField")
    public R importField(@RequestParam(value="file",required=false) MultipartFile file , HttpServletResponse response, HttpServletRequest request) throws Exception {
        //String realPath = request.getSession().getServletContext().getRealPath("/upload/excel");
        String realPath = getClass().getClassLoader().getResource("upload").getPath();
        File xlsFile = new File(realPath, file.getOriginalFilename());
        FileUtils.copyInputStreamToFile(file.getInputStream(), xlsFile);
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(xlsFile));
        } catch (Exception ex) {
            workbook = new XSSFWorkbook(new FileInputStream(xlsFile));
        }
        XSSFSheet sheet = workbook.getSheetAt(0);
        if (sheet != null) {
            int rowcount = sheet.getPhysicalNumberOfRows();
            System.out.println("有数据" + rowcount);
        } else {
            System.out.println("没有数据");
        }
        XSSFRow row = null;
        System.out.println(sheet.getLastRowNum());
        List<XjMetaDataSetEntity> list = new ArrayList<XjMetaDataSetEntity>();
        Integer dt;
        Integer dl;
        Integer jm;
        for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            row = sheet.getRow(i);
            XjMetaDataSetEntity xjMetaDataSetEntity = new XjMetaDataSetEntity();
            xjMetaDataSetEntity.setCnName(POIUtils.getCellValue(row.getCell(1)).replace(" ", ""));
            xjMetaDataSetEntity.setEuName(POIUtils.getCellValue(row.getCell(2)).replace(" ", ""));
            xjMetaDataSetEntity.setReviewState(Integer.valueOf(POIUtils.getCellValue(row.getCell(3)).replace(" ", "")));
            xjMetaDataSetEntity.setCreateDate(new Date());
            xjMetaDataSetEntity.setUpdateTime(new Date());
            xjMetaDataSetService.insert(xjMetaDataSetEntity);
        }
        xlsFile.delete();
        return R.ok();
    }


    /**
     导出
     */
    @RequestMapping("/downField/{meteSetId}")
    public void downField(@PathVariable("meteSetId") Long meteSetId, HttpSession session, HttpServletResponse response, HttpServletRequest request)throws Exception{

        List<XjMetaDataSetEntity> fieldList =xjMetaDataSetService.selectList(new EntityWrapper<XjMetaDataSetEntity>()) ;
        if (fieldList != null && fieldList.size() > 0) {
            String[] headers = {"编号","元数据集编号", "中文名称", "英文名称","英文短名","分类名称","当前版本","状态","创建人","创建日期","更新日期"};
            String fileName = "元数据集字段";
            exportExcel(headers, fieldList, fileName, response, request, session);
        }
    }

    /**
     * 导出信息方法
     */
    public void exportExcel(String[] headers, Collection<XjMetaDataSetEntity> dataset,
                            String fileName, HttpServletResponse response, org.apache.catalina.servlet4preview.http.HttpServletRequest request, HttpSession session) throws Exception {
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
        Iterator<XjMetaDataSetEntity> it = dataset.iterator();
        //设置行索引
        int index = 0;
        while (it.hasNext()) {
            index++;
            //创建数据行
            row = sheet.createRow(index);
            //获取数据
            XjMetaDataSetEntity t =  it.next();

            Cell c = row.createCell(0);
            c.setCellValue(index);
            for (short i = 1; i < headers.length + 1; i++) {
                Cell cell = row.createCell(i);
                if(i == 1){
                    cell.setCellValue(t.getMeteSetNumber());
                }
                else if(i == 2){
                    cell.setCellValue(t.getCnName());
                }else if(i == 3){
                    cell.setCellValue(t.getEuName());
                }else if(i == 4){
                    cell.setCellValue(t.getEuShortName());
                }else if(i == 5){
                    XjMeteSetCategoryEntity xjMeteSetCategoryEntity=xjMeteSetCategoryService.selectOne(new EntityWrapper<XjMeteSetCategoryEntity>().eq("mete_category_set_id",t.getMeteCategorySetId()));
                    cell.setCellValue(xjMeteSetCategoryEntity.getName());
                } else if(i == 6){
                    cell.setCellValue(t.getCurrentVersion());
                }else if(i == 7){
                    cell.setCellValue(t.getReviewState());
                }else if(i == 8){
                    cell.setCellValue(getUser().getUsername());
                }else if(i == 9){
                    LocalDateTime localDateTime=LocalDateTime.now();
                    DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String ss = dateTimeFormatter.format(localDateTime).toString();
                    cell.setCellValue(ss);
                }else if(i == 10){
                    LocalDateTime localDateTime=LocalDateTime.now();
                    DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String ss = dateTimeFormatter.format(localDateTime).toString();
                    cell.setCellValue(ss);
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
                                HttpServletResponse response, org.apache.catalina.servlet4preview.http.HttpServletRequest request, HttpSession session) throws Exception {
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
}
