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
 * @date 2019-08-12 14:54:08
 */
@TableName("resource_field")
public class ResourceFieldEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long fieldId;
	/**
	 * 
	 */
	private Long meteId;
	/**
	 * 中文名称
	 */
	private String cnName;
	/**
	 * 英文名称
	 */
	private String euName;
	/**
	 * 数据类型
	 */
	private Integer dataType;
	/**
	 * 数据长度
	 */
	private Integer dataLength;
	/**
	 * 判断必选
	 */
	private Integer judgeMandatory;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 设置：
	 */
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	/**
	 * 获取：
	 */
	public Long getFieldId() {
		return fieldId;
	}
	/**
	 * 设置：
	 */
	public void setMeteId(Long meteId) {
		this.meteId = meteId;
	}
	/**
	 * 获取：
	 */
	public Long getMeteId() {
		return meteId;
	}
	/**
	 * 设置：中文名称
	 */
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	/**
	 * 获取：中文名称
	 */
	public String getCnName() {
		return cnName;
	}
	/**
	 * 设置：英文名称
	 */
	public void setEuName(String euName) {
		this.euName = euName;
	}
	/**
	 * 获取：英文名称
	 */
	public String getEuName() {
		return euName;
	}
	/**
	 * 设置：数据类型
	 */
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	/**
	 * 获取：数据类型
	 */
	public Integer getDataType() {
		return dataType;
	}
	/**
	 * 设置：数据长度
	 */
	public void setDataLength(Integer dataLength) {
		this.dataLength = dataLength;
	}
	/**
	 * 获取：数据长度
	 */
	public Integer getDataLength() {
		return dataLength;
	}
	/**
	 * 设置：判断必选
	 */
	public void setJudgeMandatory(Integer judgeMandatory) {
		this.judgeMandatory = judgeMandatory;
	}
	/**
	 * 获取：判断必选
	 */
	public Integer getJudgeMandatory() {
		return judgeMandatory;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateDate() {
		return createDate;
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
}
