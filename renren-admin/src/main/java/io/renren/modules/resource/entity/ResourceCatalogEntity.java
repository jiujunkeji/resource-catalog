package io.renren.modules.resource.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-07-25 16:57:52
 */
@TableName("resource_catalog")
public class ResourceCatalogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
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
	 * 目录类型
	 */
	private Integer type;
	/**
	 * 描述
	 */
	private String remark;
	/**
	 * 创建人id
	 */
	private Long createUserId;
	/**
	 * 创建人
	 */
	private String createUserName;
	/**
	 * 创建部门id
	 */
	private Long createDeptId;
	/**
	 * 创建部门
	 */
	private String createDeptName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 是否使用（0：停用；1：使用）
	 */
	private Integer isUsed;
	/**
	 * 是否删除（0：未删除；1：已删除）
	 */
	private Integer isDeleted;

	@TableField(exist = false)
	private String oneName;
	@TableField(exist = false)
	private String twoName;
	@TableField(exist = false)
	private String threeName;
	/**
	 * 设置：
	 */
	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;
	}
	/**
	 * 获取：
	 */
	public Long getCatalogId() {
		return catalogId;
	}
	/**
	 * 设置：目录名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：目录名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：上级id
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：上级id
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * 设置：目录类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：目录类型
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：描述
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：描述
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：创建人id
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	/**
	 * 获取：创建人id
	 */
	public Long getCreateUserId() {
		return createUserId;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	/**
	 * 获取：创建人
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	/**
	 * 设置：创建部门id
	 */
	public void setCreateDeptId(Long createDeptId) {
		this.createDeptId = createDeptId;
	}
	/**
	 * 获取：创建部门id
	 */
	public Long getCreateDeptId() {
		return createDeptId;
	}
	/**
	 * 设置：创建部门
	 */
	public void setCreateDeptName(String createDeptName) {
		this.createDeptName = createDeptName;
	}
	/**
	 * 获取：创建部门
	 */
	public String getCreateDeptName() {
		return createDeptName;
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
	 * 设置：是否使用（0：停用；1：使用）
	 */
	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}
	/**
	 * 获取：是否使用（0：停用；1：使用）
	 */
	public Integer getIsUsed() {
		return isUsed;
	}
	/**
	 * 设置：是否删除（0：未删除；1：已删除）
	 */
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取：是否删除（0：未删除；1：已删除）
	 */
	public Integer getIsDeleted() {
		return isDeleted;
	}

	public String getOneName() {
		return oneName;
	}

	public void setOneName(String oneName) {
		this.oneName = oneName;
	}

	public String getTwoName() {
		return twoName;
	}

	public void setTwoName(String twoName) {
		this.twoName = twoName;
	}

	public String getThreeName() {
		return threeName;
	}

	public void setThreeName(String threeName) {
		this.threeName = threeName;
	}
}
