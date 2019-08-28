package io.renren.modules.resource.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-08-20 16:13:07
 */
@TableName("catalog_dept")
public class CatalogDeptEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long catalogId;
	/**
	 * 
	 */
	private Long deptId;

	private Long organisationId;
	public CatalogDeptEntity(Long catalogId, Long organisationId) {
		this.catalogId = catalogId;
		this.organisationId = organisationId;
	}

	public CatalogDeptEntity(Long id, Long catalogId, Long deptId, Long organisationId) {
		this.id = id;
		this.catalogId = catalogId;
		this.deptId = deptId;
		this.organisationId = organisationId;
	}

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
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
	 * 设置：
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：
	 */
	public Long getDeptId() {
		return deptId;
	}

	public Long getOrganisationId() {
		return organisationId;
	}

	public void setOrganisationId(Long organisationId) {
		this.organisationId = organisationId;
	}
}
