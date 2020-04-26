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
 * @date 2020-04-26 15:14:46
 */
@TableName("xj_catalog_link_data")
public class XjCatalogLinkDataEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Long linkId;
	/**
	 * 目录id
	 */
	private Long catalogId;
	/**
	 * 数据源id
	 */
	private Long dataSourceId;
	/**
	 * 数据表名
	 */
	private String tableName;

	/**
	 * 元数据列表
	 */
	@TableField(exist = false)
	private List<XjMeteSetMiddleEntity> meteDataList;
	/**
	 * 设置：主键id
	 */
	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}
	/**
	 * 获取：主键id
	 */
	public Long getLinkId() {
		return linkId;
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
	 * 设置：数据源id
	 */
	public void setDataSourceId(Long dataSourceId) {
		this.dataSourceId = dataSourceId;
	}
	/**
	 * 获取：数据源id
	 */
	public Long getDataSourceId() {
		return dataSourceId;
	}
	/**
	 * 设置：数据表名
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * 获取：数据表名
	 */
	public String getTableName() {
		return tableName;
	}

	public List<XjMeteSetMiddleEntity> getMeteDataList() {
		return meteDataList;
	}

	public void setMeteDataList(List<XjMeteSetMiddleEntity> meteDataList) {
		this.meteDataList = meteDataList;
	}
}
