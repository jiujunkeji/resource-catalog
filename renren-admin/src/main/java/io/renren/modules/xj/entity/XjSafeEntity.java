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
@TableName("xj_safe")
public class XjSafeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long safeId;
	/**
	 * 目录id
	 */
	private Long catalogId;
	/**
	 * 目录名称
	 */
	private String catalogName;
	/**
	 * 安全类型code
	 */
	private Integer safeTypeCode;
	/**
	 * 安全类型
	 */
	private String safeType;
	/**
	 * 安全等级code
	 */
	private Integer safeCode;
	/**
	 * 安全等级
	 */
	private String safe;
	/**
	 * 加密code
	 */
	private Integer encryptCode;
	/**
	 * 加密方式
	 */
	private String encrypt;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 设置：id
	 */
	public void setSafeId(Long safeId) {
		this.safeId = safeId;
	}
	/**
	 * 获取：id
	 */
	public Long getSafeId() {
		return safeId;
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
	 * 设置：目录名称
	 */
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	/**
	 * 获取：目录名称
	 */
	public String getCatalogName() {
		return catalogName;
	}
	/**
	 * 设置：安全等级code
	 */
	public void setSafeCode(Integer safeCode) {
		this.safeCode = safeCode;
	}
	/**
	 * 获取：安全等级code
	 */
	public Integer getSafeCode() {
		return safeCode;
	}
	/**
	 * 设置：安全等级
	 */
	public void setSafe(String safe) {
		this.safe = safe;
	}
	/**
	 * 获取：安全等级
	 */
	public String getSafe() {
		return safe;
	}
	/**
	 * 设置：加密code
	 */
	public void setEncryptCode(Integer encryptCode) {
		this.encryptCode = encryptCode;
	}
	/**
	 * 获取：加密code
	 */
	public Integer getEncryptCode() {
		return encryptCode;
	}
	/**
	 * 设置：加密方式
	 */
	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}
	/**
	 * 获取：加密方式
	 */
	public String getEncrypt() {
		return encrypt;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}

	public Integer getSafeTypeCode() {
		return safeTypeCode;
	}

	public void setSafeTypeCode(Integer safeTypeCode) {
		this.safeTypeCode = safeTypeCode;
	}

	public String getSafeType() {
		return safeType;
	}

	public void setSafeType(String safeType) {
		this.safeType = safeType;
	}
}
