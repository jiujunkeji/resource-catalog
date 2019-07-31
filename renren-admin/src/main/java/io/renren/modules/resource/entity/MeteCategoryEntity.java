package io.renren.modules.resource.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-07-25 16:57:53
 */
@TableName("mete_category")
public class MeteCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 资源分类id
	 */
	@TableId
	private Long meteCategoryId;
	/**
	 * 分类类型
	 */
	private String categoryType;
	/**
	 * 分类名称
	 */
	private String name;
	/**
	 * 分类代码
	 */
	private String code;
	/**
	 * 上级id
	 */
	private Long parentId;
	/**
	 * 上级名称
	 */
	@TableField(exist = false)
	private String parentName;
	/**
	 * 描述
	 */
	private String remark;

	/**
	 * ztree属性
	 */
	@TableField(exist = false)
	private  Boolean open;

	@TableField(exist = false)
	private List<?> list;
	/**
	 * 设置：资源分类id
	 */
	public void setMeteCategoryId(Long meteCategoryId) {
		this.meteCategoryId = meteCategoryId;
	}
	/**
	 * 获取：资源分类id
	 */
	public Long getMeteCategoryId() {
		return meteCategoryId;
	}
	/**
	 * 设置：分类类型
	 */
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	/**
	 * 获取：分类类型
	 */
	public String getCategoryType() {
		return categoryType;
	}
	/**
	 * 设置：分类名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：分类名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：分类代码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：分类代码
	 */
	public String getCode() {
		return code;
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
}
