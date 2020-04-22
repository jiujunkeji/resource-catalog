package io.renren.modules.xj.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 元数据集版本变更历史记录表
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 15:46:35
 */
@TableName("xj_mete_set_version")
public class XjMeteSetVersionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long versionId;
	/**
	 * 元数据集版本号
	 */
	private String versionNumber;
	/**
	 * 元数据集版本变更详情
	 */
	private String versionChangeDetails;
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
	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}
	/**
	 * 获取：主键
	 */
	public Long getVersionId() {
		return versionId;
	}
	/**
	 * 设置：元数据集版本号
	 */
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	/**
	 * 获取：元数据集版本号
	 */
	public String getVersionNumber() {
		return versionNumber;
	}
	/**
	 * 设置：元数据集版本变更详情
	 */
	public void setVersionChangeDetails(String versionChangeDetails) {
		this.versionChangeDetails = versionChangeDetails;
	}
	/**
	 * 获取：元数据集版本变更详情
	 */
	public String getVersionChangeDetails() {
		return versionChangeDetails;
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
