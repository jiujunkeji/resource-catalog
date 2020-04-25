package io.renren.modules.xj.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 元数据与元数据集的中间表的版本表
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-24 10:54:31
 */
@TableName("xj_mete_set_middle_version")
public class XjMeteSetMiddleVersionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 版本id（主键）
	 */
	@TableId
	private Long versionId;
	/**
	 * 元数据集id
	 */
	private Long meteSetId;
	/**
	 * 变更版本号
	 */
	private String versionNumber;
	/**
	 * 元数据的中文名称
	 */
	private String meteCname;
	/**
	 * 元数据的英文短名
	 */
	private String meteEuame;
	/**
	 * 元数据的数据类型
	 */
	private Integer meteDataType;
	/**
	 * 元数据的数据长度
	 */
	private Integer meteDataLength;
	/**
	 * 元数据的值域
	 */
	private String meteRange;
	/**
	 * 元数据的值域描述
	 */
	private String meteRangeDescription;
	/**
	 * 元数据的定义
	 */
	private String meteDefinition;
	/**
	 * 元数据编号
	 */
	private String meteNumber;
	/**
	 * 元数据集的中文名称
	 */
	private String meteEuShortName;
	/**
	 * 
	 */
	private String meteSetCname;
	/**
	 * 元数据集的英文名称
	 */
	private String meteSetEuame;
	/**
	 * 元数据集的编号
	 */
	private String meteSetNumber;
	/**
	 * 元数据集的英文短名
	 */
	private String meteSetEuShortName;
	/**
	 * 
	 */
	private Long meteId;
	/**
	 * 创建用户id
	 */
	private Long createUserId;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 更新日期
	 */
	private Date updateTime;

	/**
	 * 设置：版本id（主键）
	 */
	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}
	/**
	 * 获取：版本id（主键）
	 */
	public Long getVersionId() {
		return versionId;
	}
	/**
	 * 设置：元数据集id
	 */
	public void setMeteSetId(Long meteSetId) {
		this.meteSetId = meteSetId;
	}
	/**
	 * 获取：元数据集id
	 */
	public Long getMeteSetId() {
		return meteSetId;
	}
	/**
	 * 设置：变更版本号
	 */
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	/**
	 * 获取：变更版本号
	 */
	public String getVersionNumber() {
		return versionNumber;
	}
	/**
	 * 设置：元数据的中文名称
	 */
	public void setMeteCname(String meteCname) {
		this.meteCname = meteCname;
	}
	/**
	 * 获取：元数据的中文名称
	 */
	public String getMeteCname() {
		return meteCname;
	}
	/**
	 * 设置：元数据的英文短名
	 */
	public void setMeteEuame(String meteEuame) {
		this.meteEuame = meteEuame;
	}
	/**
	 * 获取：元数据的英文短名
	 */
	public String getMeteEuame() {
		return meteEuame;
	}

	public Integer getMeteDataType() {
		return meteDataType;
	}

	public void setMeteDataType(Integer meteDataType) {
		this.meteDataType = meteDataType;
	}

	/**
	 * 设置：元数据的数据长度
	 */
	public void setMeteDataLength(Integer meteDataLength) {
		this.meteDataLength = meteDataLength;
	}
	/**
	 * 获取：元数据的数据长度
	 */
	public Integer getMeteDataLength() {
		return meteDataLength;
	}
	/**
	 * 设置：元数据的值域
	 */
	public void setMeteRange(String meteRange) {
		this.meteRange = meteRange;
	}
	/**
	 * 获取：元数据的值域
	 */
	public String getMeteRange() {
		return meteRange;
	}
	/**
	 * 设置：元数据的值域描述
	 */
	public void setMeteRangeDescription(String meteRangeDescription) {
		this.meteRangeDescription = meteRangeDescription;
	}
	/**
	 * 获取：元数据的值域描述
	 */
	public String getMeteRangeDescription() {
		return meteRangeDescription;
	}
	/**
	 * 设置：元数据的定义
	 */
	public void setMeteDefinition(String meteDefinition) {
		this.meteDefinition = meteDefinition;
	}
	/**
	 * 获取：元数据的定义
	 */
	public String getMeteDefinition() {
		return meteDefinition;
	}
	/**
	 * 设置：元数据编号
	 */
	public void setMeteNumber(String meteNumber) {
		this.meteNumber = meteNumber;
	}
	/**
	 * 获取：元数据编号
	 */
	public String getMeteNumber() {
		return meteNumber;
	}
	/**
	 * 设置：元数据集的中文名称
	 */
	public void setMeteEuShortName(String meteEuShortName) {
		this.meteEuShortName = meteEuShortName;
	}
	/**
	 * 获取：元数据集的中文名称
	 */
	public String getMeteEuShortName() {
		return meteEuShortName;
	}
	/**
	 * 设置：
	 */
	public void setMeteSetCname(String meteSetCname) {
		this.meteSetCname = meteSetCname;
	}
	/**
	 * 获取：
	 */
	public String getMeteSetCname() {
		return meteSetCname;
	}
	/**
	 * 设置：元数据集的英文名称
	 */
	public void setMeteSetEuame(String meteSetEuame) {
		this.meteSetEuame = meteSetEuame;
	}
	/**
	 * 获取：元数据集的英文名称
	 */
	public String getMeteSetEuame() {
		return meteSetEuame;
	}
	/**
	 * 设置：元数据集的编号
	 */
	public void setMeteSetNumber(String meteSetNumber) {
		this.meteSetNumber = meteSetNumber;
	}
	/**
	 * 获取：元数据集的编号
	 */
	public String getMeteSetNumber() {
		return meteSetNumber;
	}
	/**
	 * 设置：元数据集的英文短名
	 */
	public void setMeteSetEuShortName(String meteSetEuShortName) {
		this.meteSetEuShortName = meteSetEuShortName;
	}
	/**
	 * 获取：元数据集的英文短名
	 */
	public String getMeteSetEuShortName() {
		return meteSetEuShortName;
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
	 * 设置：创建用户id
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	/**
	 * 获取：创建用户id
	 */
	public Long getCreateUserId() {
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
