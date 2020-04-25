package io.renren.modules.xj.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.resource.utils.POIUtils;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysDictEntity;
import io.renren.modules.sys.service.SysDictService;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.xj.entity.XjMetaDataSetEntity;
import io.renren.modules.xj.entity.XjMeteCategoryEntity;
import io.renren.modules.xj.entity.XjMeteSetVersionEntity;
import io.renren.modules.xj.service.XjMetaDataSetService;
import io.renren.modules.xj.service.XjMeteCategoryService;
import io.renren.modules.xj.service.XjMeteSetVersionService;
import io.renren.modules.xj.service.impl.XjMeteCategoryServiceImpl;
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

import io.renren.modules.xj.entity.XjMetaDataEntity;
import io.renren.modules.xj.service.XjMetaDataService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 元数据表
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
@RestController
@RequestMapping("xj/xjmetadata")
public class XjMetaDataController extends AbstractController {
    @Autowired
    private XjMetaDataService xjMetaDataService;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private XjMeteCategoryService xjMeteCategoryService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("xj:xjmetadata:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xjMetaDataService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 条件查询（根据元数据分类编号或者分类名称）
     */
    @RequestMapping("/queryList")
    //@RequiresPermissions("resource:metecategory:list")
    public R queryList(@RequestParam Map<String,Object> params){
        PageUtils page=xjMetaDataService.searchFindByMeteDataNumberOrName(params);
        return R.ok().put("page",page);
    }




    /**
     * 元数据一键启用
     */
    @RequestMapping("/updateEnabledState")
    public R updateEnabledState(@RequestBody Long[] meteIds){
        List<XjMetaDataEntity> xjMeteDataEntityList=xjMetaDataService.updateEnabledState(meteIds);
        if(xjMeteDataEntityList!=null && xjMeteDataEntityList.size()>0){
            for(XjMetaDataEntity xjMeteDataEntity:xjMeteDataEntityList){
                xjMeteDataEntity.setIsDisabled(0);
                xjMetaDataService.updateById(xjMeteDataEntity);
            }
        }
        return R.ok();
    }

    /**
     * 元数据一键禁用
     */
    @RequestMapping("/updateDisabledState")
    public R updateDisabledState(@RequestBody Long[] meteIds){
        List<XjMetaDataEntity> xjMeteDataEntityList=xjMetaDataService.updateEnabledState(meteIds);
        if(xjMeteDataEntityList!=null && xjMeteDataEntityList.size()>0){
            for(XjMetaDataEntity xjMeteDataEntity:xjMeteDataEntityList){
                xjMeteDataEntity.setIsDisabled(1);
                xjMetaDataService.updateById(xjMeteDataEntity);
            }
        }
        return R.ok();
    }





    /**
     * 信息
     */
    @RequestMapping("/info/{meteId}")
    //@RequiresPermissions("xj:xjmetadata:info")
    public R info(@PathVariable("meteId") Long meteId){
        XjMetaDataEntity xjMetaData = xjMetaDataService.selectById(meteId);
        return R.ok().put("xjMetaData", xjMetaData);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("xj:xjmetadata:save")
    public R save(@RequestBody XjMetaDataEntity xjMetaData) {
        xjMetaData.setCreateDate(new Date());
        xjMetaData.setUpdateTime(new Date());
        xjMetaData.setCreateUserId(getUser().getUserId());
        xjMetaDataService.insert(xjMetaData);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("xj:xjmetadata:update")
    public R update(@RequestBody XjMetaDataEntity xjMetaData){

        XjMetaDataEntity xjMetaDataEntity=xjMetaDataService.selectOne(new EntityWrapper<XjMetaDataEntity>().eq("mete_id",xjMetaData.getMeteId()));
        ValidatorUtils.validateEntity(xjMetaDataEntity);
        /**
         * 获取分类id
         */
        if(xjMetaData.getMeteCategoryId()!=null){
            xjMetaDataEntity.setMeteCategoryId(xjMetaData.getMeteCategoryId());
        }
        xjMetaDataEntity.setUpdateTime(new Date());
        xjMetaDataEntity.setCnName(xjMetaData.getCnName());
        xjMetaDataEntity.setEuName(xjMetaData.getEuName());
        xjMetaDataEntity.setDataType(xjMetaData.getDataType());
        xjMetaDataEntity.setControlType(xjMetaData.getControlType());
        xjMetaDataEntity.setEuShortName(xjMetaData.getEuShortName());
        xjMetaDataEntity.setMeteNumber(xjMetaData.getMeteNumber());
        xjMetaDataEntity.setDefinition(xjMetaData.getDefinition());
        xjMetaDataEntity.setRange(xjMetaData.getRange());
        xjMetaDataEntity.setRangeDescription(xjMetaData.getRangeDescription());
        xjMetaDataEntity.setIsDisabled(xjMetaData.getIsDisabled());
        xjMetaDataEntity.setCreateUserId(xjMetaData.getCreateUserId());
        xjMetaDataEntity.setDataLength(xjMetaData.getDataLength());
        xjMetaDataEntity.setCheckType(xjMetaData.getCheckType());
        xjMetaDataEntity.setJudgeMandatory(xjMetaData.getJudgeMandatory());
        xjMetaDataEntity.setCreateUserId(getUser().getUserId());
        xjMetaDataService.updateById(xjMetaDataEntity);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("xj:xjmetadata:delete")
    public R delete(@RequestBody Long[] meteIds){
        xjMetaDataService.deleteBatchIds(Arrays.asList(meteIds));

        return R.ok();
    }

    /**
     * 下载目录模板
     */
    @RequestMapping("/downTemplate")
    public void downTemplate(HttpServletResponse response, HttpServletRequest request){
        try{
            String pathName = "fieldTemplate.xlsx";
            String fileName = "元数据字段导入模板.xlsx";
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
        List<XjMetaDataEntity> list = new ArrayList<XjMetaDataEntity>();
        Integer dt;
        Integer dl;
        Integer jm;
        for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            row = sheet.getRow(i);
            XjMetaDataEntity xjMetaDataEntity = new XjMetaDataEntity();
            xjMetaDataEntity.setCnName(POIUtils.getCellValue(row.getCell(1)).replace(" ", ""));
            xjMetaDataEntity.setEuName(POIUtils.getCellValue(row.getCell(2)).replace(" ", ""));
            if (POIUtils.getCellValue(row.getCell(3)) != null) {
                String dtf = (POIUtils.getCellValue(row.getCell(3)));
                if (dtf.equals("整型")){
                    xjMetaDataEntity.setDataType(2);
                }else if (dtf.equals("实型")){
                    xjMetaDataEntity.setDataType(5);
                }else if (dtf.equals("布尔型")){
                    xjMetaDataEntity.setDataType(3);
                }else if (dtf.equals("字符型")){
                    xjMetaDataEntity.setDataType(1);
                }else if (dtf.equals("日期")){
                    xjMetaDataEntity.setDataType(4);
                }
            }
            if (POIUtils.getCellValue(row.getCell(4)) != null) {
                dl = Integer.valueOf(POIUtils.getCellValue(row.getCell(4)).replace(".0",""));
                xjMetaDataEntity.setDataLength(dl);
            }
            if (POIUtils.getCellValue(row.getCell(5)) != null) {
                String jmf =(POIUtils.getCellValue(row.getCell(5)));
                if (jmf.equals("必选")){
                    xjMetaDataEntity.setJudgeMandatory(0);
                }else if (jmf.equals("非必选")){
                    xjMetaDataEntity.setJudgeMandatory(1);
                }
            }
            xjMetaDataEntity.setCreateDate(new Date());
            xjMetaDataEntity.setUpdateTime(new Date());
            xjMetaDataService.insert(xjMetaDataEntity);
        }
        xlsFile.delete();
        return R.ok();
    }


    /**
     导出
     */
    @RequestMapping("/downField/{meteId}")
    public void downField(@PathVariable("meteId") Long meteId, HttpSession session, HttpServletResponse response, HttpServletRequest request)throws Exception{

        List<XjMetaDataEntity> fieldList =xjMetaDataService.selectList(new EntityWrapper<XjMetaDataEntity>()) ;
        if (fieldList != null && fieldList.size() > 0) {
            String[] headers = {"编号", "中文名称", "英文名称", "数据类型", "数据长度", "是否必选"};
            String fileName = "元数据字段";
            exportExcel(headers, fieldList, fileName, response, request, session);
        }
    }

    /**
     * 导出信息方法
     */
    public void exportExcel(String[] headers, Collection<XjMetaDataEntity> dataset,
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
        Iterator<XjMetaDataEntity> it = dataset.iterator();
        //设置行索引
        int index = 0;
        while (it.hasNext()) {
            index++;
            //创建数据行
            row = sheet.createRow(index);
            //获取数据
            XjMetaDataEntity t =  it.next();

            Cell c = row.createCell(0);
            c.setCellValue(index);
            for (short i = 1; i < headers.length + 1; i++) {
                Cell cell = row.createCell(i);
                if(i == 1){
                    cell.setCellValue(t.getCnName());
                }else if(i == 2){
                    cell.setCellValue(t.getEuName());
                }else if(i == 3){
                    cell.setCellValue(t.getEuShortName());
                }else if(i == 4){
                    cell.setCellValue(t.getMeteNumber());
                }else if(i == 5){
                    if (t.getDataType() != null){
                        if (t.getDataType().equals("整型")){
                            cell.setCellValue("整型");
                        }else if (t.getDataType().equals("实型")){
                            cell.setCellValue("实型");
                        }else if (t.getDataType().equals("布尔型")){
                            cell.setCellValue("布尔型");
                        }else if (t.getDataType().equals("字符型")){
                            cell.setCellValue("字符型");
                        }else if (t.getDataType().equals("日期")){
                            cell.setCellValue("日期");
                        }
                    }
                }else if (i == 6){
                    cell.setCellValue(t.getDataLength());
                }else if(i == 7){
                    if (t.getJudgeMandatory()== 0){
                        cell.setCellValue("必选");
                    }else if (t.getJudgeMandatory() == 1){
                        cell.setCellValue("非必选");
                    }
                }
                else if(i == 8){
                    cell.setCellValue(t.getDefinition());
                }else if(i == 9){
                    cell.setCellValue(t.getRange());
                }else if(i == 10){
                    cell.setCellValue(t.getMeteCategoryId());
                }else if(i == 11){
                    cell.setCellValue(t.getCheckType());
                }else if(i == 12){
                    cell.setCellValue(t.getIsDisabled());
                }else if(i == 13){
                    cell.setCellValue(t.getCreateUserId());
                }else if(i == 14){
                    cell.setCellValue(t.getCreateUserId());
                }else if(i == 15){
                    cell.setCellValue(t.getCreateDate());
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
