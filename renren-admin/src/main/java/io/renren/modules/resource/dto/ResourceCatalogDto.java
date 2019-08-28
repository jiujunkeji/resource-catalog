package io.renren.modules.resource.dto;

import com.baomidou.mybatisplus.annotations.TableId;

/**
 * @Auther: wdh
 * @Date: 2019-08-28 19:10
 * @Description:
 */
public class ResourceCatalogDto {
    /**
     *
     */
    private Long catalogId;
    /**
     * 目录名称
     */
    private String name;
    /**
     * 上级id
     */
    private Long parentId;
    /**
     * 上级目录名称
     */
    private String parentName;
    /**
     * 目录类型
     */
    private String type;
    /**
     * 描述
     */
    private String remark;

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
