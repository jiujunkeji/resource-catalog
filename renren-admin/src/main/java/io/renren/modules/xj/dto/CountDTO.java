package io.renren.modules.xj.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Auther: wdh
 * @Date: 2020-05-09 14:42
 * @Description:
 */
public class CountDTO implements Serializable {
    //元数据数量
    private int meteDataCount;
    //元数据分类数量
    private int meteCategoryCount;
    //目录数量
    private int catalogCount;
    //目录日变化量
    private int catalogDayChange;
    //目录月变化量
    private int catalogMonChange;
    //交换任务量
    private int monitorCount;
    //本日交换任务量
    private int monitorDayCount;
    //交换记录数
    private BigDecimal monitorDataCount;
    //本日交换记录数
    private BigDecimal monitorDataDayCount;

    public CountDTO(){}

    public int getMeteDataCount() {
        return meteDataCount;
    }

    public void setMeteDataCount(int meteDataCount) {
        this.meteDataCount = meteDataCount;
    }

    public int getMeteCategoryCount() {
        return meteCategoryCount;
    }

    public void setMeteCategoryCount(int meteCategoryCount) {
        this.meteCategoryCount = meteCategoryCount;
    }

    public int getCatalogCount() {
        return catalogCount;
    }

    public void setCatalogCount(int catalogCount) {
        this.catalogCount = catalogCount;
    }

    public int getCatalogDayChange() {
        return catalogDayChange;
    }

    public void setCatalogDayChange(int catalogDayChange) {
        this.catalogDayChange = catalogDayChange;
    }

    public int getCatalogMonChange() {
        return catalogMonChange;
    }

    public void setCatalogMonChange(int catalogMonChange) {
        this.catalogMonChange = catalogMonChange;
    }

    public int getMonitorCount() {
        return monitorCount;
    }

    public void setMonitorCount(int monitorCount) {
        this.monitorCount = monitorCount;
    }

    public BigDecimal getMonitorDataCount() {
        return monitorDataCount;
    }

    public void setMonitorDataCount(BigDecimal monitorDataCount) {
        this.monitorDataCount = monitorDataCount;
    }

    public BigDecimal getMonitorDataDayCount() {
        return monitorDataDayCount;
    }

    public void setMonitorDataDayCount(BigDecimal monitorDataDayCount) {
        this.monitorDataDayCount = monitorDataDayCount;
    }

    public int getMonitorDayCount() {
        return monitorDayCount;
    }

    public void setMonitorDayCount(int monitorDayCount) {
        this.monitorDayCount = monitorDayCount;
    }
}
