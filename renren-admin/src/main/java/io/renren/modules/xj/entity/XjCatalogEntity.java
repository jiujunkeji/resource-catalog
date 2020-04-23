package io.renren.modules.xj.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 17:21:53
 */
@TableName("xj_catalog")
public class XjCatalogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 目录id
	 */
	@TableId
	private Long catalogId;
	/**
	 * 目录名称
	 */
	private String catalogName;
	/**
	 * 类目id
	 */
	private Long categoryId;
	/**
	 * 类目编码
	 */
	private String categoryCode;
	/**
	 * 类目名称
	 */
	private String categoryName;
	/**
	 * 上级目录id
	 */
	private Long parentId;
	/**
	 * 信息资源名称
	 */
	private String resourceTitle;
	/**
	 * 信息资源摘要
	 */
	private String resourceAbstract;
	/**
	 * 资源提供方id
	 */
	private Long organisationId;
	/**
	 * 资源提供单位
	 */
	private String organisationName;
	/**
	 * 资源提供方地址
	 */
	private String organisationAddress;
	/**
	 * 信息资源标识
	 */
	private String resourceSign;
	/**
	 * 元数据标识
	 */
	private String metedataIdentifier;
	/**
	 * 元数据集id
	 */
	private Long meteSetId;
	/**
	 * 是否使用（0：停用；1：使用）
	 */
	private int isUsed = 1;
	/**
	 * 创建人id
	 */
	private Long createUserId;
	/**
	 * 创建人
	 */
	private String createUserName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 审核状态（0：待提交；1：待审核；2：通过；3：未通过）
	 */
	private Integer reviewState = 0;
	/**
	 * 发布状态（0：未发布；1：发布）
	 */
	private Integer pushState = 0;
	/**
	 * 是否删除（0：未删除；1：删除）
	 */
	private Integer isDeleted = 0;

	@TableField(exist = false)
	private String parentName;
	@TableField(exist = false)
	private List<XjMetaDataEntity> meteDataList;
	/**
	 * 设置：目录id
	 */
	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;
	}
	/**
	 * 获取：目录id
	 */
	public Long getCatalogId() {
		return catalogId;
	}
	/**
	 * 设置：目录名称
	 */
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	/**
	 * 获取：目录名称
	 */
	public String getCatalogName() {
		return catalogName;
	}
	/**
	 * 设置：类目id
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取：类目id
	 */
	public Long getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置：类目编码
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	/**
	 * 获取：类目编码
	 */
	public String getCategoryCode() {
		return categoryCode;
	}
	/**
	 * 设置：类目名称
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * 获取：类目名称
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * 设置：上级目录id
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：上级目录id
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * 设置：信息资源名称
	 */
	public void setResourceTitle(String resourceTitle) {
		this.resourceTitle = resourceTitle;
	}
	/**
	 * 获取：信息资源名称
	 */
	public String getResourceTitle() {
		return resourceTitle;
	}
	/**
	 * 设置：信息资源摘要
	 */
	public void setResourceAbstract(String resourceAbstract) {
		this.resourceAbstract = resourceAbstract;
	}
	/**
	 * 获取：信息资源摘要
	 */
	public String getResourceAbstract() {
		return resourceAbstract;
	}
	/**
	 * 设置：资源提供方id
	 */
	public void setOrganisationId(Long organisationId) {
		this.organisationId = organisationId;
	}
	/**
	 * 获取：资源提供方id
	 */
	public Long getOrganisationId() {
		return organisationId;
	}
	/**
	 * 设置：资源提供单位
	 */
	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}
	/**
	 * 获取：资源提供单位
	 */
	public String getOrganisationName() {
		return organisationName;
	}
	/**
	 * 设置：资源提供方地址
	 */
	public void setOrganisationAddress(String organisationAddress) {
		this.organisationAddress = organisationAddress;
	}
	/**
	 * 获取：资源提供方地址
	 */
	public String getOrganisationAddress() {
		return organisationAddress;
	}
	/**
	 * 设置：信息资源标识
	 */
	public void setResourceSign(String resourceSign) {
		this.resourceSign = resourceSign;
	}
	/**
	 * 获取：信息资源标识
	 */
	public String getResourceSign() {
		return resourceSign;
	}
	/**
	 * 设置：元数据标识
	 */
	public void setMetedataIdentifier(String metedataIdentifier) {
		this.metedataIdentifier = metedataIdentifier;
	}
	/**
	 * 获取：元数据标识
	 */
	public String getMetedataIdentifier() {
		return metedataIdentifier;
	}
	/**
	 * 设置：元数据集id
	 */
	public void setMeteSetId(Long meteSetId) {
		this.meteSetId = meteSetId;
	}
	/**
	 * 获取：元数据集id
	 */
	public Long getMeteSetId() {
		return meteSetId;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：审核状态（0：待提交；1：待审核；2：通过；3：未通过）
	 */
	public void setReviewState(Integer reviewState) {
		this.reviewState = reviewState;
	}
	/**
	 * 获取：审核状态（0：待提交；1：待审核；2：通过；3：未通过）
	 */
	public Integer getReviewState() {
		return reviewState;
	}
	/**
	 * 设置：发布状态（0：未发布；1：发布）
	 */
	public void setPushState(Integer pushState) {
		this.pushState = pushState;
	}
	/**
	 * 获取：发布状态（0：未发布；1：发布）
	 */
	public Integer getPushState() {
		return pushState;
	}
	/**
	 * 设置：是否删除（0：未删除；1：删除）
	 */
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取：是否删除（0：未删除；1：删除）
	 */
	public Integer getIsDeleted() {
		return isDeleted;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public List<XjMetaDataEntity> getMeteDataList() {
		return meteDataList;
	}

	public void setMeteDataList(List<XjMetaDataEntity> meteDataList) {
		this.meteDataList = meteDataList;
	}
}
