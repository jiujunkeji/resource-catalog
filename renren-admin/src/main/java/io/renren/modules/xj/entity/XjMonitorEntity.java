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
 * @date 2020-05-07 15:53:30
 */
@TableName("xj_monitor")
public class XjMonitorEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer monitorId;
	/**
	 * 任务名称
	 */
	private String jobName;
	/**
	 * 执行开始时间
	 */
	private Date startTime;
	/**
	 * 执行结束时间
	 */
	private Date endTime;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 
	 */
	private Date createTime;

	@TableField(exist = false)
	private List<XjChildMonitorEntity> childMonitorList;

	@TableField(exist = false)
	private List<XjKlogEntity> logList;

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
	 * 设置：任务名称
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * 获取：任务名称
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * 设置：执行开始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：执行开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：执行结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：执行结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}
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

	public List<XjChildMonitorEntity> getChildMonitorList() {
		return childMonitorList;
	}

	public void setChildMonitorList(List<XjChildMonitorEntity> childMonitorList) {
		this.childMonitorList = childMonitorList;
	}

	public List<XjKlogEntity> getLogList() {
		return logList;
	}

	public void setLogList(List<XjKlogEntity> logList) {
		this.logList = logList;
	}
}
