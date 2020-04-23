package io.renren.modules.xj.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 元数据集表
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
@TableName("xj_meta_data_set")
public class XjMetaDataSetEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long meteSetId;
	/**
	 * 元数据集编号
	 */
	private String meteSetNumber;
	/**
	 * 元数据集合中文名称
	 */
	private String cnName;
	/**
	 * 元数据集英文名称
	 */
	private String euName;
	/**
	 * 元数据集英文短名
	 */
	private String euShortName;
	/**
	 * 审核状态（0：待提交；1：待审核；2：通过；3：未通过）
	 */
	private Integer reviewState;
	/**
	 * 当前版本
	 */
	private String currentVersion;

	/**
	 * 
	 */
	private Long meteCategorySetId;
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
	public void setMeteSetId(Long meteSetId) {
		this.meteSetId = meteSetId;
	}
	/**
	 * 获取：主键
	 */
	public Long getMeteSetId() {
		return meteSetId;
	}
	/**
	 * 设置：元数据集编号
	 */
	public void setMeteSetNumber(String meteSetNumber) {
		this.meteSetNumber = meteSetNumber;
	}
	/**
	 * 获取：元数据集编号
	 */
	public String getMeteSetNumber() {
		return meteSetNumber;
	}
	/**
	 * 设置：元数据集合中文名称
	 */
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	/**
	 * 获取：元数据集合中文名称
	 */
	public String getCnName() {
		return cnName;
	}
	/**
	 * 设置：元数据集英文名称
	 */
	public void setEuName(String euName) {
		this.euName = euName;
	}
	/**
	 * 获取：元数据集英文名称
	 */
	public String getEuName() {
		return euName;
	}
	/**
	 * 设置：元数据集英文短名
	 */
	public void setEuShortName(String euShortName) {
		this.euShortName = euShortName;
	}
	/**
	 * 获取：元数据集英文短名
	 */
	public String getEuShortName() {
		return euShortName;
	}
	/**
	 * 设置：审核状态（0：待提交；1：待审核；2：通过；3：未通过）
	 */
	public void setReviewState(Integer reviewState) {
		this.reviewState = reviewState;
	}
	/**
	 * 获取：审核状态（0：待提交；1：待审核；2：通过；3：未通过）
	 */
	public Integer getReviewState() {
		return reviewState;
	}
	/**
	 * 设置：当前版本
	 */
	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}
	/**
	 * 获取：当前版本
	 */
	public String getCurrentVersion() {
		return currentVersion;
	}

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
