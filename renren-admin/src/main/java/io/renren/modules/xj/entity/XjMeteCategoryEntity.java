package io.renren.modules.xj.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 元数据分类表
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
@TableName("xj_mete_category")
public class XjMeteCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 元数据分类id
	 */
	@TableId
	private Long meteCategoryId;
	/**
	 * 分类编号
	 */
	private String metaCategoryNumber;
	/**
	 * 
	 */
	private String categoryType;
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
	private Integer sortCode;
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
     * 上级名称
     */
    @TableField(exist = false)
    private String parentName;


	/**
	 * ztree属性
	 */
	@TableField(exist = false)
	private  Boolean open;

	@TableField(exist = false)
	private List<?> list;

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	/**
	 * 设置：元数据分类id
	 */
	public void setMeteCategoryId(Long meteCategoryId) {
		this.meteCategoryId = meteCategoryId;
	}
	/**
	 * 获取：元数据分类id
	 */
	public Long getMeteCategoryId() {
		return meteCategoryId;
	}
	/**
	 * 设置：分类编号
	 */
	public void setMetaCategoryNumber(String metaCategoryNumber) {
		this.metaCategoryNumber = metaCategoryNumber;
	}
	/**
	 * 获取：分类编号
	 */
	public String getMetaCategoryNumber() {
		return metaCategoryNumber;
	}
	/**
	 * 设置：
	 */
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	/**
	 * 获取：
	 */
	public String getCategoryType() {
		return categoryType;
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
	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}
	/**
	 * 获取：排序码
	 */
	public Integer getSortCode() {
		return sortCode;
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
