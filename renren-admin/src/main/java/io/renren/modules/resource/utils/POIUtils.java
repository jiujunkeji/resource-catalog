package io.renren.modules.resource.utils;

import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 * @Auther: wdh
 * @Date: 2019-07-30 19:52
 * @Description:
 */
public class POIUtils {
    /**
     * 得到Excel Cell数据
     *
     * @param cell
     * @return
     */
    public static String getCellValue(XSSFCell cell) {
        if (cell != null) {
            switch (cell.getCellType()) {
                case XSSFCell.CELL_TYPE_STRING:
                    return cell.getStringCellValue();
                case XSSFCell.CELL_TYPE_NUMERIC:
                    return cell.getNumericCellValue() + "";
                case XSSFCell.CELL_TYPE_BLANK:
                    return "";
                default:
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    return cell.getStringCellValue();
            }
        } else {
            return "";
        }
    }
}
