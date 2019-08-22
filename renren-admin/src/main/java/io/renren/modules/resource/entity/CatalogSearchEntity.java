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
 * @date 2019-08-22 11:40:18
 */
@TableName("catalog_search")
public class CatalogSearchEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 搜索时间
	 */
	private Date searchDate;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：搜索时间
	 */
	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}
	/**
	 * 获取：搜索时间
	 */
	public Date getSearchDate() {
		return searchDate;
	}
}
