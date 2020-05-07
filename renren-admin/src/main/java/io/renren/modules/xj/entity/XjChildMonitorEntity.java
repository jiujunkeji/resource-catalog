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
 * @date 2020-05-07 15:53:30
 */
@TableName("xj_child_monitor")
public class XjChildMonitorEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer childJobId;
	/**
	 * 
	 */
	private Integer monitorId;
	/**
	 * 
	 */
	private String childJobName;
//	/**
//	 *
//	 */
//	private Date startTime;
//	/**
//	 *
//	 */
//	private Date endTime;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 
	 */
	private Date createTime;

	/**
	 * 设置：
	 */
	public void setChildJobId(Integer childJobId) {
		this.childJobId = childJobId;
	}
	/**
	 * 获取：
	 */
	public Integer getChildJobId() {
		return childJobId;
	}
	/**
	 * 设置：
	 */
	public void setMonitorId(Integer monitorId) {
		this.monitorId = monitorId;
	}
	/**
	 * 获取：
	 */
	public Integer getMonitorId() {
		return monitorId;
	}
	/**
	 * 设置：
	 */
	public void setChildJobName(String childJobName) {
		this.childJobName = childJobName;
	}
	/**
	 * 获取：
	 */
	public String getChildJobName() {
		return childJobName;
	}
//	/**
//	 * 设置：
//	 */
//	public void setStartTime(Date startTime) {
//		this.startTime = startTime;
//	}
//	/**
//	 * 获取：
//	 */
//	public Date getStartTime() {
//		return startTime;
//	}
//	/**
//	 * 设置：
//	 */
//	public void setEndTime(Date endTime) {
//		this.endTime = endTime;
//	}
//	/**
//	 * 获取：
//	 */
//	public Date getEndTime() {
//		return endTime;
//	}
	/**
	 * 设置：
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
