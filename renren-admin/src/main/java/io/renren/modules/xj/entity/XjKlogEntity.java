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
	 * 输入
	 */
	private String xjIn;
	/**
	 * 输出
	 */
	private String xjOut;
	/**
	 * 读
	 */
	private String xjRead;
	/**
	 * 写
	 */
	private String xjWrite;
	/**
	 * 更新
	 */
	private String xjUpdate;
	/**
	 * 错误
	 */
	private String xjError;
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
	public void setxjIn(String xjIn) {
		this.xjIn = xjIn;
	}
	/**
	 * 获取：
	 */
	public String getxjIn() {
		return xjIn;
	}
	/**
	 * 设置：
	 */
	public void setxjOut(String xjOut) {
		this.xjOut = xjOut;
	}
	/**
	 * 获取：
	 */
	public String getxjOut() {
		return xjOut;
	}
	/**
	 * 设置：
	 */
	public void setxjRead(String xjRead) {
		this.xjRead = xjRead;
	}
	/**
	 * 获取：
	 */
	public String getxjRead() {
		return xjRead;
	}
	/**
	 * 设置：
	 */
	public void setxjWrite(String xjWrite) {
		this.xjWrite = xjWrite;
	}
	/**
	 * 获取：
	 */
	public String getxjWrite() {
		return xjWrite;
	}
	/**
	 * 设置：
	 */
	public void setxjUpdate(String xjUpdate) {
		this.xjUpdate = xjUpdate;
	}
	/**
	 * 获取：
	 */
	public String getxjUpdate() {
		return xjUpdate;
	}
	/**
	 * 设置：
	 */
	public void setxjError(String xjError) {
		this.xjError = xjError;
	}
	/**
	 * 获取：
	 */
	public String getxjError() {
		return xjError;
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
