package io.renren.modules.xj.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 17:21:53
 */
@TableName("xj_catalog_audit")
public class XjCatalogAuditEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long catalogAuditId;
	/**
	 * 目录id
	 */
	private Long catalogId;
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
	 * 设置：
	 */
	public void setCatalogAuditId(Long catalogAuditId) {
		this.catalogAuditId = catalogAuditId;
	}
	/**
	 * 获取：
	 */
	public Long getCatalogAuditId() {
		return catalogAuditId;
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
