package io.renren.modules.resource.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.resource.entity.ResourceCatalogEntity;
import io.renren.modules.resource.utils.POIUtils;
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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.resource.entity.ResourceFieldEntity;
import io.renren.modules.resource.service.ResourceFieldService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 
 *
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-08-12 14:54:08
 */
@RestController
@RequestMapping("resource/resourcefield")
public class ResourceFieldController {
    @Autowired
    private ResourceFieldService resourceFieldService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("resource:resourcefield:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = resourceFieldService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{fieldId}")
    @RequiresPermissions("resource:resourcefield:info")
    public R info(@PathVariable("fieldId") Long fieldId){
        ResourceFieldEntity resourceField = resourceFieldService.selectById(fieldId);

        return R.ok().put("resourceField", resourceField);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("resource:resourcefield:save")
    public R save(@RequestBody ResourceFieldEntity resourceField){
        resourceFieldService.insert(resourceField);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("resource:resourcefield:update")
    public R update(@RequestBody ResourceFieldEntity resourceField){
        ValidatorUtils.validateEntity(resourceField);
        resourceFieldService.updateAllColumnById(resourceField);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("resource:resourcefield:delete")
    public R delete(@RequestBody Long[] fieldIds){
        resourceFieldService.deleteBatchIds(Arrays.asList(fieldIds));

        return R.ok();
    }

    /**
     *导入
     */
    @RequestMapping("/importField")
    public R importField(@RequestParam(value="file",required=false)MultipartFile file , HttpServletResponse response, HttpServletRequest request) throws Exception {
        String realPath = request.getSession().getServletContext().getRealPath("/upload/excel");
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
        List<ResourceFieldEntity> list = new ArrayList<ResourceFieldEntity>();
        Integer dt;
        Integer dl;
        Integer jm;
        for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            row = sheet.getRow(i);
            ResourceFieldEntity fieldEntity = new ResourceFieldEntity();
            fieldEntity.setCnName(POIUtils.getCellValue(row.getCell(1)).replace(" ", ""));
            fieldEntity.setEuName(POIUtils.getCellValue(row.getCell(2)).replace(" ", ""));
            if (POIUtils.getCellValue(row.getCell(3)) != null) {
                dt = Integer.valueOf(POIUtils.getCellValue(row.getCell(3)));
                fieldEntity.setDataType(dt);
            }
            if (POIUtils.getCellValue(row.getCell(4)) != null) {
                dl = Integer.valueOf(POIUtils.getCellValue(row.getCell(4)));
                fieldEntity.setDataType(dl);
            }
            if (POIUtils.getCellValue(row.getCell(3)) != null) {
                jm = Integer.valueOf(POIUtils.getCellValue(row.getCell(3)));
                fieldEntity.setDataType(jm);
            }
            fieldEntity.setCreateDate(new Date());
            fieldEntity.setUpdateTime(new Date());
        }
        return R.ok();
    }


    /**
    导出
     */
    @RequestMapping("/downField")
    public void downField(HttpSession session, HttpServletResponse response, HttpServletRequest request)throws Exception{

        List<ResourceFieldEntity> fieldList =resourceFieldService.selectList(new EntityWrapper<ResourceFieldEntity>()) ;
        if (fieldList != null && fieldList.size() > 0) {
            String[] headers = {"编号", "中文名称", "英文名称", "数据类型", "数据长度", "是否必选"};
            String fileName = "元数据字段";
            exportExcel(headers, fieldList, fileName, response, request, session);
        }
    }

    /**
     * 导出信息方法
     */
    public void exportExcel(String[] headers, Collection<ResourceFieldEntity> dataset,
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
        Iterator<ResourceFieldEntity> it = dataset.iterator();
        //设置行索引
        int index = 0;
        while (it.hasNext()) {
            index++;
            //创建数据行
            row = sheet.createRow(index);
            //获取数据
            ResourceFieldEntity t =  it.next();

            Cell c = row.createCell(0);
            c.setCellValue(index);
            for (short i = 1; i < headers.length + 1; i++) {
                Cell cell = row.createCell(i);
                if(i == 1){
                    cell.setCellValue(t.getCnName());
                }else if(i == 2){
                    cell.setCellValue(t.getEuName());
                }else if(i == 3){
                    if (t.getDataType() != null){
                        if (t.getDataType() == 0){
                            cell.setCellValue("整形");
                        }else if (t.getDataType() == 1){
                            cell.setCellValue("实型");
                        }else if (t.getDataType() == 2){
                            cell.setCellValue("布尔型");
                        }else if (t.getDataType() == 3){
                            cell.setCellValue("字符串");
                        }else if (t.getDataType() == 4){
                            cell.setCellValue("日期");
                        }
                    }
                }else if (i == 4){
                    cell.setCellValue(t.getDataLength());
                }else if(i == 5){
                    cell.setCellValue(t.getJudgeMandatory());
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
