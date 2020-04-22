package io.renren.modules.xj.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 元数据集分类表
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
@TableName("xj_mete_set_category")
public class XjMeteSetCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long meteCategorySetId;
	/**
	 * 分类编号
	 */
	private String metaCategorySetNumber;
	/**
	 * 
	 */
	private String categorySetType;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String code;
	/**
	 * 排序码
	 */
	private Integer sortSetCode;
	/**
	 * 是否禁用(0-未禁用；1-禁用)
	 */
	private Integer isDisabled;
	/**
	 * 
	 */
	private Long parentId;
	/**
	 * 
	 */
	private String remark;

	/**
	 * 设置：
	 */
	public void setMeteCategorySetId(Long meteCategorySetId) {
		this.meteCategorySetId = meteCategorySetId;
	}
	/**
	 * 获取：
	 */
	public Long getMeteCategorySetId() {
		return meteCategorySetId;
	}
	/**
	 * 设置：分类编号
	 */
	public void setMetaCategorySetNumber(String metaCategorySetNumber) {
		this.metaCategorySetNumber = metaCategorySetNumber;
	}
	/**
	 * 获取：分类编号
	 */
	public String getMetaCategorySetNumber() {
		return metaCategorySetNumber;
	}
	/**
	 * 设置：
	 */
	public void setCategorySetType(String categorySetType) {
		this.categorySetType = categorySetType;
	}
	/**
	 * 获取：
	 */
	public String getCategorySetType() {
		return categorySetType;
	}
	/**
	 * 设置：
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：排序码
	 */
	public void setSortSetCode(Integer sortSetCode) {
		this.sortSetCode = sortSetCode;
	}
	/**
	 * 获取：排序码
	 */
	public Integer getSortSetCode() {
		return sortSetCode;
	}
	/**
	 * 设置：是否禁用(0-未禁用；1-禁用)
	 */
	public void setIsDisabled(Integer isDisabled) {
		this.isDisabled = isDisabled;
	}
	/**
	 * 获取：是否禁用(0-未禁用；1-禁用)
	 */
	public Integer getIsDisabled() {
		return isDisabled;
	}
	/**
	 * 设置：
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * 设置：
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：
	 */
	public String getRemark() {
		return remark;
	}
}
