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
 * @date 2020-04-27 11:20:21
 */
@TableName("xj_ftp")
public class XjFtpEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer ftpId;
	/**
	 * ftp任务名
	 */
	private String ftpName;
	/**
	 * 文件路径
	 */
	private String ftpFilepath;
	/**
	 * ftp路径
	 */
	private String ftpFtppath;
	/**
	 * 
	 */
	private String ftpIp;
	/**
	 * 
	 */
	private int ftpPort;
	/**
	 * 
	 */
	private String ftpUsername;
	/**
	 * 
	 */
	private String ftpPassword;
	/**
	 * 
	 */
	private Date ftpCreatetime;
	/**
	 * 删除（0 未删除，1已删除）
	 */
	private Integer ftpDelete;

	/**
	 * 设置：
	 */
	public void setFtpId(Integer ftpId) {
		this.ftpId = ftpId;
	}
	/**
	 * 获取：
	 */
	public Integer getFtpId() {
		return ftpId;
	}
	/**
	 * 设置：ftp任务名
	 */
	public void setFtpName(String ftpName) {
		this.ftpName = ftpName;
	}
	/**
	 * 获取：ftp任务名
	 */
	public String getFtpName() {
		return ftpName;
	}
	/**
	 * 设置：文件路径
	 */
	public void setFtpFilepath(String ftpFilepath) {
		this.ftpFilepath = ftpFilepath;
	}
	/**
	 * 获取：文件路径
	 */
	public String getFtpFilepath() {
		return ftpFilepath;
	}
	/**
	 * 设置：ftp路径
	 */
	public void setFtpFtppath(String ftpFtppath) {
		this.ftpFtppath = ftpFtppath;
	}
	/**
	 * 获取：ftp路径
	 */
	public String getFtpFtppath() {
		return ftpFtppath;
	}
	/**
	 * 设置：
	 */
	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}
	/**
	 * 获取：
	 */
	public String getFtpIp() {
		return ftpIp;
	}
	/**
	 * 设置：
	 */
	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}
	/**
	 * 获取：
	 */
	public int getFtpPort() {
		return ftpPort;
	}
	/**
	 * 设置：
	 */
	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}
	/**
	 * 获取：
	 */
	public String getFtpUsername() {
		return ftpUsername;
	}
	/**
	 * 设置：
	 */
	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	/**
	 * 获取：
	 */
	public String getFtpPassword() {
		return ftpPassword;
	}
	/**
	 * 设置：
	 */
	public void setFtpCreatetime(Date ftpCreatetime) {
		this.ftpCreatetime = ftpCreatetime;
	}
	/**
	 * 获取：
	 */
	public Date getFtpCreatetime() {
		return ftpCreatetime;
	}
	/**
	 * 设置：删除（0 未删除，1已删除）
	 */
	public void setFtpDelete(Integer ftpDelete) {
		this.ftpDelete = ftpDelete;
	}
	/**
	 * 获取：删除（0 未删除，1已删除）
	 */
	public Integer getFtpDelete() {
		return ftpDelete;
	}
}
