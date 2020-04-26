package io.renren.modules.xj.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 元数据集的审核表
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-25 15:42:51
 */
@TableName("xj_mete_set_audit")
public class XjMeteSetAuditEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 元数据集的审核id
	 */
	@TableId
	private Long meteSetAuditId;
	/**
	 * 元数据集的id
	 */
	private Long meteSetId;
	/**
	 * 操作人id
	 */
	private Long operatUserId;
	/**
	 * 操作人
	 */
	private String operatUserName;
	/**
	 * 操作时间
	 */
	private Date operatTime;
	/**
	 * 操作类型
	 */
	private String operatType;
	/**
	 * 审核意见
	 */
	private String auditOpinion;

	/**
	 * 设置：元数据集的审核id
	 */
	public void setMeteSetAuditId(Long meteSetAuditId) {
		this.meteSetAuditId = meteSetAuditId;
	}
	/**
	 * 获取：元数据集的审核id
	 */
	public Long getMeteSetAuditId() {
		return meteSetAuditId;
	}
	/**
	 * 设置：元数据集的id
	 */
	public void setMeteSetId(Long meteSetId) {
		this.meteSetId = meteSetId;
	}
	/**
	 * 获取：元数据集的id
	 */
	public Long getMeteSetId() {
		return meteSetId;
	}
	/**
	 * 设置：操作人id
	 */
	public void setOperatUserId(Long operatUserId) {
		this.operatUserId = operatUserId;
	}
	/**
	 * 获取：操作人id
	 */
	public Long getOperatUserId() {
		return operatUserId;
	}
	/**
	 * 设置：操作人
	 */
	public void setOperatUserName(String operatUserName) {
		this.operatUserName = operatUserName;
	}
	/**
	 * 获取：操作人
	 */
	public String getOperatUserName() {
		return operatUserName;
	}
	/**
	 * 设置：操作时间
	 */
	public void setOperatTime(Date operatTime) {
		this.operatTime = operatTime;
	}
	/**
	 * 获取：操作时间
	 */
	public Date getOperatTime() {
		return operatTime;
	}
	/**
	 * 设置：操作类型
	 */
	public void setOperatType(String operatType) {
		this.operatType = operatType;
	}
	/**
	 * 获取：操作类型
	 */
	public String getOperatType() {
		return operatType;
	}
	/**
	 * 设置：审核意见
	 */
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	/**
	 * 获取：审核意见
	 */
	public String getAuditOpinion() {
		return auditOpinion;
	}
}
