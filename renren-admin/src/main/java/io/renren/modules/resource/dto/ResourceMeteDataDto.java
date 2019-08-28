package io.renren.modules.resource.dto;

import com.baomidou.mybatisplus.annotations.TableId;

/**
 * @Auther: wdh
 * @Date: 2019-08-28 19:10
 * @Description:
 */
public class ResourceMeteDataDto {
    /**
     * id
     */
    @TableId
    private Long meteId;
    /**
     * 元数据类型（资源类型、服务类型）
     */
    private String meteType;
    /**
     * 信息资源类型（主题分类）
     */
    private String resourceCategory;

    /**
     * 资源分类编码
     */
    private String catagoryCode;
    /**
     * 资源分类
     */
    private String categoryName;
    /**
     * 目录名称
     */
    private String catalogName;
    /**
     * 信息资源名称
     */
    private String resourceTitle;
    /**
     * 信息资源摘要
     */
    private String resourceAbstract;
    /**
     * 资源提供单位
     */
    private String organisationName;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 信息资源标识
     */
    private String resourceSign;
    /**
     * 元数据标识
     */
    private String metedataIdentifier;

    public Long getMeteId() {
        return meteId;
    }

    public void setMeteId(Long meteId) {
        this.meteId = meteId;
    }

    public String getMeteType() {
        return meteType;
    }

    public void setMeteType(String meteType) {
        this.meteType = meteType;
    }

    public String getResourceCategory() {
        return resourceCategory;
    }

    public void setResourceCategory(String resourceCategory) {
        this.resourceCategory = resourceCategory;
    }

    public String getCatagoryCode() {
        return catagoryCode;
    }

    public void setCatagoryCode(String catagoryCode) {
        this.catagoryCode = catagoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getResourceTitle() {
        return resourceTitle;
    }

    public void setResourceTitle(String resourceTitle) {
        this.resourceTitle = resourceTitle;
    }

    public String getResourceAbstract() {
        return resourceAbstract;
    }

    public void setResourceAbstract(String resourceAbstract) {
        this.resourceAbstract = resourceAbstract;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getResourceSign() {
        return resourceSign;
    }

    public void setResourceSign(String resourceSign) {
        this.resourceSign = resourceSign;
    }

    public String getMetedataIdentifier() {
        return metedataIdentifier;
    }

    public void setMetedataIdentifier(String metedataIdentifier) {
        this.metedataIdentifier = metedataIdentifier;
    }
}
