package io.renren.modules.xj.dto;

import java.io.Serializable;

/**
 * @Auther: wdh
 * @Date: 2020-05-09 14:56
 * @Description:
 */
public class MeteCategoryDto implements Serializable {
    //分类名称
    private String catagoryName;
    //元数据数量
    private int count;

    public MeteCategoryDto(){}

    public String getCatagoryName() {
        return catagoryName;
    }

    public void setCatagoryName(String catagoryName) {
        this.catagoryName = catagoryName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
