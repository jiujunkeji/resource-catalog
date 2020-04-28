package io.renren.modules.xj.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2020-04-28 15:21:11
 */
@TableName("xj_schedule_job")
public class XjScheduleJobEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer triggerId;
	/**
	 * 定时任务名
	 */
	private String triggerName;
	/**
	 * kettle任务id
	 */
	private Integer ktrId;
	/**
	 * 定时任务id
	 */
	private long scheduleId;
	/**
	 * cron表达式
	 */
	private String triggerCron;
	/**
	 * 状态（0 未执行 1 正在执行 2 暂停 3）
	 */
	private Integer status;
	/**
	 * 删除
	 */
	private Integer isDelete;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 备注
	 */
	private String text;

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(long scheduleId) {
		this.scheduleId = scheduleId;
	}

	/**
	 * 设置：
	 */
	public void setTriggerId(Integer triggerId) {
		this.triggerId = triggerId;
	}
	/**
	 * 获取：
	 */
	public Integer getTriggerId() {
		return triggerId;
	}
	/**
	 * 设置：定时任务名
	 */
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	/**
	 * 获取：定时任务名
	 */
	public String getTriggerName() {
		return triggerName;
	}
	/**
	 * 设置：kettle任务id
	 */
	public void setKtrId(Integer ktrId) {
		this.ktrId = ktrId;
	}
	/**
	 * 获取：kettle任务id
	 */
	public Integer getKtrId() {
		return ktrId;
	}
	/**
	 * 设置：cron表达式
	 */
	public void setTriggerCron(String triggerCron) {
		this.triggerCron = triggerCron;
	}
	/**
	 * 获取：cron表达式
	 */
	public String getTriggerCron() {
		return triggerCron;
	}
	/**
	 * 设置：状态（0 未执行 1 正在执行 2 暂停 3）
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态（0 未执行 1 正在执行 2 暂停 3）
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：备注
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * 获取：备注
	 */
	public String getText() {
		return text;
	}
}
