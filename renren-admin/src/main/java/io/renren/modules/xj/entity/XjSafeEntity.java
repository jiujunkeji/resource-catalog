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
	 * createUserId
	 */
	private Long createUserId;
	/**
	 * createUser
	 */
	private String createUser;
	/**
	 * createTime
	 */
	private Date createTime;
	/**
	 * updateUserId
	 */
	private Long updateUserId;
	/**
	 * updateUser
	 */
	private String updateUser;
	/**
	 * updateTime
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 元数据列表
	 */
	@TableField(exist = false)
	private List<XjMeteSetMiddleEntity> meteDataList;
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

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<XjMeteSetMiddleEntity> getMeteDataList() {
		return meteDataList;
	}

	public void setMeteDataList(List<XjMeteSetMiddleEntity> meteDataList) {
		this.meteDataList = meteDataList;
	}
}
