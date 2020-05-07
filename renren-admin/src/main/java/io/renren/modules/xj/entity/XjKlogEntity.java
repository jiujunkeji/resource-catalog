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
@TableName("xj_klog")
public class XjKlogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer klogId;
	/**
	 * 
	 */
	private Integer monitorId;
	/**
	 * 组件名
	 */
	private String assemblyName;
	/**
	 * 
	 */
	private String in;
	/**
	 * 
	 */
	private String out;
	/**
	 * 
	 */
	private String read;
	/**
	 * 
	 */
	private String write;
	/**
	 * 
	 */
	private String update;
	/**
	 * 
	 */
	private String error;
	/**
	 * 
	 */
	private Date createTime;

	/**
	 * 设置：
	 */
	public void setKlogId(Integer klogId) {
		this.klogId = klogId;
	}
	/**
	 * 获取：
	 */
	public Integer getKlogId() {
		return klogId;
	}
	/**
	 * 设置：
	 */
	public void setmonitorId(Integer monitorId) {
		this.monitorId = monitorId;
	}
	/**
	 * 获取：
	 */
	public Integer getmonitorId() {
		return monitorId;
	}
	/**
	 * 设置：组件名
	 */
	public void setAssemblyName(String assemblyName) {
		this.assemblyName = assemblyName;
	}
	/**
	 * 获取：组件名
	 */
	public String getAssemblyName() {
		return assemblyName;
	}
	/**
	 * 设置：
	 */
	public void setIn(String in) {
		this.in = in;
	}
	/**
	 * 获取：
	 */
	public String getIn() {
		return in;
	}
	/**
	 * 设置：
	 */
	public void setOut(String out) {
		this.out = out;
	}
	/**
	 * 获取：
	 */
	public String getOut() {
		return out;
	}
	/**
	 * 设置：
	 */
	public void setRead(String read) {
		this.read = read;
	}
	/**
	 * 获取：
	 */
	public String getRead() {
		return read;
	}
	/**
	 * 设置：
	 */
	public void setWrite(String write) {
		this.write = write;
	}
	/**
	 * 获取：
	 */
	public String getWrite() {
		return write;
	}
	/**
	 * 设置：
	 */
	public void setUpdate(String update) {
		this.update = update;
	}
	/**
	 * 获取：
	 */
	public String getUpdate() {
		return update;
	}
	/**
	 * 设置：
	 */
	public void setError(String error) {
		this.error = error;
	}
	/**
	 * 获取：
	 */
	public String getError() {
		return error;
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
