package io.renren.modules.xj.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 元数据集变更版本表
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-24 10:54:31
 */
@TableName("xj_mete_set_version")
public class XjMeteSetVersionEntity implements Serializable {
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
	 * 变更版本号
	 */
	private String versionNumber;
	/**
	 * 元数据集分类id
	 */
	private Long meteCategorySetId;
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
	 * 设置：元数据集分类id
	 */
	public void setMeteCategorySetId(Long meteCategorySetId) {
		this.meteCategorySetId = meteCategorySetId;
	}
	/**
	 * 获取：元数据集分类id
	 */
	public Long getMeteCategorySetId() {
		return meteCategorySetId;
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
