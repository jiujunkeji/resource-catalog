package io.renren.modules.xj.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author fanwei
 * @email 3275869736@qq.com
 * @date 2020-04-22 13:31:15
 */
@TableName("xj_meta_data")
public class XjMetaDataEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long fieldId;
	/**
	 * 编号
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
	 * 英文短名
	 */
	private String euShortName;
	/**
	 * 数据类型
	 */
	private Integer dataType;
	/**
	 * 控件类型
	 */
	private String controlType;
	/**
	 * 校验类型（0-不验证，1-验证）
	 */
	private Integer checkType;
	/**
	 * 数据长度
	 */
	private Integer dataLength;
	/**
	 * 值域
	 */
	private String range;
	/**
	 * 值域说明
	 */
	private String rangeDescription;
	/**
	 * 是否必选还是非必选
	 */
	private Integer judgeMandatory;
	/**
	 * 定义
	 */
	private String definition;
	/**
	 * 是否禁用（0-不禁用；1-禁用）
	 */
	private Integer isDisabled;
	/**
	 * 创建用户id
	 */
	private Integer createUserId;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 更新日期
	 */
	private Date updateTime;

	/**
	 * 设置：主键
	 */
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	/**
	 * 获取：主键
	 */
	public Long getFieldId() {
		return fieldId;
	}
	/**
	 * 设置：编号
	 */
	public void setMeteId(Long meteId) {
		this.meteId = meteId;
	}
	/**
	 * 获取：编号
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
	 * 设置：英文短名
	 */
	public void setEuShortName(String euShortName) {
		this.euShortName = euShortName;
	}
	/**
	 * 获取：英文短名
	 */
	public String getEuShortName() {
		return euShortName;
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
	 * 设置：控件类型
	 */
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
	/**
	 * 获取：控件类型
	 */
	public String getControlType() {
		return controlType;
	}
	/**
	 * 设置：校验类型（0-不验证，1-验证）
	 */
	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}
	/**
	 * 获取：校验类型（0-不验证，1-验证）
	 */
	public Integer getCheckType() {
		return checkType;
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
	 * 设置：值域
	 */
	public void setRange(String range) {
		this.range = range;
	}
	/**
	 * 获取：值域
	 */
	public String getRange() {
		return range;
	}
	/**
	 * 设置：值域说明
	 */
	public void setRangeDescription(String rangeDescription) {
		this.rangeDescription = rangeDescription;
	}
	/**
	 * 获取：值域说明
	 */
	public String getRangeDescription() {
		return rangeDescription;
	}
	/**
	 * 设置：是否必选还是非必选
	 */
	public void setJudgeMandatory(Integer judgeMandatory) {
		this.judgeMandatory = judgeMandatory;
	}
	/**
	 * 获取：是否必选还是非必选
	 */
	public Integer getJudgeMandatory() {
		return judgeMandatory;
	}
	/**
	 * 设置：定义
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	/**
	 * 获取：定义
	 */
	public String getDefinition() {
		return definition;
	}
	/**
	 * 设置：是否禁用（0-不禁用；1-禁用）
	 */
	public void setIsDisabled(Integer isDisabled) {
		this.isDisabled = isDisabled;
	}
	/**
	 * 获取：是否禁用（0-不禁用；1-禁用）
	 */
	public Integer getIsDisabled() {
		return isDisabled;
	}
	/**
	 * 设置：创建用户id
	 */
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	/**
	 * 获取：创建用户id
	 */
	public Integer getCreateUserId() {
		return createUserId;
	}
	/**
	 * 设置：创建日期
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建日期
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：更新日期
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新日期
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
