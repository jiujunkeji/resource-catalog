package io.renren.modules.resource.entity;

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
 * @date 2019-07-25 16:57:52
 */
@TableName("resource_mete_data")
public class ResourceMeteDataEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long meteId;
	/**
	 * 元数据类型（资源类型、服务类型）
	 */
	private Integer meteType;
	/**
	 * 信息资源类型（主题分类）
	 */
	private Integer resourceCategory;
	/**
	 * 资源分类id
	 */
	private Long categoryId;
	/**
	 * 资源分类编码
	 */
	private String catagoryCode;
	/**
	 * 资源分类
	 */
	private String categoryName;
	/**
	 * 目录id
	 */
	private Long catalogId;
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
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 审核状态（0：待提交；1：待审核；2：通过；3：未通过）
	 */
	private Integer reviewState;
	/**
	 * 审核人id
	 */
	private Long submitUserId;
	/**
	 * 审核人
	 */
	private String submitUserName;
	/**
	 * 审核部门id
	 */
	private Long submitDeptId;
	/**
	 * 审核部门
	 */
	private String submitDeptName;
	/**
	 * 审核时间
	 */
	private Date submitTime;
	/**
	 * 审核人id
	 */
	private Long reviewUserId;
	/**
	 * 审核人
	 */
	private String reviewUserName;
	/**
	 * 审核部门id
	 */
	private Long reviewDeptId;
	/**
	 * 审核部门
	 */
	private String reviewDeptName;
	/**
	 * 审核时间
	 */
	private Date reviewTime;
	/**
	 * 发布状态（0：未发布；1：发布）
	 */
	private Integer pushState;
	/**
	 * 发布人id
	 */
	private Long pushUserId;
	/**
	 * 发布人
	 */
	private String pushUserName;
	/**
	 * 发布部门id
	 */
	private Long pushDeptId;
	/**
	 * 发布部门
	 */
	private String pushDeptName;
	/**
	 * 发布时间
	 */
	private Date pushTime;
	/**
	 * 是否删除
	 */
	private Integer isDeleted = 0;

	@TableField(exist = false)
	private List<ResourceFieldEntity> fieldList;

	/**
	 * 设置：id
	 */
	public void setMeteId(Long meteId) {
		this.meteId = meteId;
	}
	/**
	 * 获取：id
	 */
	public Long getMeteId() {
		return meteId;
	}
	/**
	 * 设置：元数据类型（资源类型、服务类型）
	 */
	public void setMeteType(Integer meteType) {
		this.meteType = meteType;
	}
	/**
	 * 获取：元数据类型（资源类型、服务类型）
	 */
	public Integer getMeteType() {
		return meteType;
	}
	/**
	 * 设置：信息资源类型（主题分类）
	 */
	public void setResourceCategory(Integer resourceCategory) {
		this.resourceCategory = resourceCategory;
	}
	/**
	 * 获取：信息资源类型（主题分类）
	 */
	public Integer getResourceCategory() {
		return resourceCategory;
	}
	/**
	 * 设置：资源分类id
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取：资源分类id
	 */
	public Long getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置：资源分类编码
	 */
	public void setCatagoryCode(String catagoryCode) {
		this.catagoryCode = catagoryCode;
	}
	/**
	 * 获取：资源分类编码
	 */
	public String getCatagoryCode() {
		return catagoryCode;
	}
	/**
	 * 设置：资源分类
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * 获取：资源分类
	 */
	public String getCategoryName() {
		return categoryName;
	}
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
	 * 设置：关键字
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/**
	 * 获取：关键字
	 */
	public String getKeyword() {
		return keyword;
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
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：审核状态（0：待审核；1：通过；2：未通过）
	 */
	public void setReviewState(Integer reviewState) {
		this.reviewState = reviewState;
	}
	/**
	 * 获取：审核状态（0：待审核；1：通过；2：未通过）
	 */
	public Integer getReviewState() {
		return reviewState;
	}
	/**
	 * 设置：审核人id
	 */
	public void setReviewUserId(Long reviewUserId) {
		this.reviewUserId = reviewUserId;
	}
	/**
	 * 获取：审核人id
	 */
	public Long getReviewUserId() {
		return reviewUserId;
	}
	/**
	 * 设置：审核人
	 */
	public void setReviewUserName(String reviewUserName) {
		this.reviewUserName = reviewUserName;
	}
	/**
	 * 获取：审核人
	 */
	public String getReviewUserName() {
		return reviewUserName;
	}
	/**
	 * 设置：审核部门id
	 */
	public void setReviewDeptId(Long reviewDeptId) {
		this.reviewDeptId = reviewDeptId;
	}
	/**
	 * 获取：审核部门id
	 */
	public Long getReviewDeptId() {
		return reviewDeptId;
	}
	/**
	 * 设置：审核部门
	 */
	public void setReviewDeptName(String reviewDeptName) {
		this.reviewDeptName = reviewDeptName;
	}
	/**
	 * 获取：审核部门
	 */
	public String getReviewDeptName() {
		return reviewDeptName;
	}
	/**
	 * 设置：审核时间
	 */
	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}
	/**
	 * 获取：审核时间
	 */
	public Date getReviewTime() {
		return reviewTime;
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
	 * 设置：发布人id
	 */
	public void setPushUserId(Long pushUserId) {
		this.pushUserId = pushUserId;
	}
	/**
	 * 获取：发布人id
	 */
	public Long getPushUserId() {
		return pushUserId;
	}
	/**
	 * 设置：发布人
	 */
	public void setPushUserName(String pushUserName) {
		this.pushUserName = pushUserName;
	}
	/**
	 * 获取：发布人
	 */
	public String getPushUserName() {
		return pushUserName;
	}
	/**
	 * 设置：发布部门id
	 */
	public void setPushDeptId(Long pushDeptId) {
		this.pushDeptId = pushDeptId;
	}
	/**
	 * 获取：发布部门id
	 */
	public Long getPushDeptId() {
		return pushDeptId;
	}
	/**
	 * 设置：发布部门
	 */
	public void setPushDeptName(String pushDeptName) {
		this.pushDeptName = pushDeptName;
	}
	/**
	 * 获取：发布部门
	 */
	public String getPushDeptName() {
		return pushDeptName;
	}
	/**
	 * 设置：发布时间
	 */
	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}
	/**
	 * 获取：发布时间
	 */
	public Date getPushTime() {
		return pushTime;
	}
	/**
	 * 设置：是否删除
	 */
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取：是否删除
	 */
	public Integer getIsDeleted() {
		return isDeleted;
	}
	/**
	 * 设置：
	 */
	public String getCatalogName() {
		return catalogName;
	}
	/**
	 * 获取：是否删除
	 */
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public Long getSubmitUserId() {
		return submitUserId;
	}

	public void setSubmitUserId(Long submitUserId) {
		this.submitUserId = submitUserId;
	}

	public String getSubmitUserName() {
		return submitUserName;
	}

	public void setSubmitUserName(String submitUserName) {
		this.submitUserName = submitUserName;
	}

	public Long getSubmitDeptId() {
		return submitDeptId;
	}

	public void setSubmitDeptId(Long submitDeptId) {
		this.submitDeptId = submitDeptId;
	}

	public String getSubmitDeptName() {
		return submitDeptName;
	}

	public void setSubmitDeptName(String submitDeptName) {
		this.submitDeptName = submitDeptName;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public List<ResourceFieldEntity> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<ResourceFieldEntity> fieldList) {
		this.fieldList = fieldList;
	}
}
