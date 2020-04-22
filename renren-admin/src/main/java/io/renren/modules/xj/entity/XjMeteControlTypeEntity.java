package io.renren.modules.xj.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 元数据控件类型表
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-22 17:25:12
 */
@TableName("xj_mete_control_type")
public class XjMeteControlTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 控件类型id(主键)
	 */
	@TableId
	private Long controlTypeId;
	/**
	 * 元数据集版本号
	 */
	private String controlName;
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
	 * 设置：控件类型id(主键)
	 */
	public void setControlTypeId(Long controlTypeId) {
		this.controlTypeId = controlTypeId;
	}
	/**
	 * 获取：控件类型id(主键)
	 */
	public Long getControlTypeId() {
		return controlTypeId;
	}
	/**
	 * 设置：元数据集版本号
	 */
	public void setControlName(String controlName) {
		this.controlName = controlName;
	}
	/**
	 * 获取：元数据集版本号
	 */
	public String getControlName() {
		return controlName;
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
