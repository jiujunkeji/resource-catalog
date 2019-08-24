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
 * @date 2019-08-20 16:11:33
 */
@TableName("catalog_user")
public class CatalogUserEntity implements Serializable {
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
	private Long userId;

	public CatalogUserEntity() {}

	public CatalogUserEntity(Long catalogId, Long userId) {
		this.catalogId = catalogId;
		this.userId = userId;
	}

	public CatalogUserEntity(Long id, Long catalogId, Long userId) {
		this.id = id;
		this.catalogId = catalogId;
		this.userId = userId;
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
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：
	 */
	public Long getUserId() {
		return userId;
	}

}
