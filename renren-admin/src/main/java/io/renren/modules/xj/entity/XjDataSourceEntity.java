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
 * @date 2020-04-22 13:31:15
 */
@TableName("xj_data_source")
public class XjDataSourceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer dsId;
	/**
	 * 数据源名称
	 */
	private String dsName;
	/**
	 * 数据源类型
	 */
	private String dsType;
	/**
	 * 部门
	 */
	private String dsDepartment;
	/**
	 * 
	 */
	private String dsLinetype;
	/**
	 * 
	 */
	private String dsIpname;
	/**
	 * 
	 */
	private String dsIp;
	/**
	 * 
	 */
	private String dsDatabasename;
	/**
	 * 
	 */
	private String dsUsername;
	/**
	 * 
	 */
	private String dsPassword;

	/**
	 * 设置：
	 */
	public void setDsId(Integer dsId) {
		this.dsId = dsId;
	}
	/**
	 * 获取：
	 */
	public Integer getDsId() {
		return dsId;
	}
	/**
	 * 设置：数据源名称
	 */
	public void setDsName(String dsName) {
		this.dsName = dsName;
	}
	/**
	 * 获取：数据源名称
	 */
	public String getDsName() {
		return dsName;
	}
	/**
	 * 设置：数据源类型
	 */
	public void setDsType(String dsType) {
		this.dsType = dsType;
	}
	/**
	 * 获取：数据源类型
	 */
	public String getDsType() {
		return dsType;
	}
	/**
	 * 设置：部门
	 */
	public void setDsDepartment(String dsDepartment) {
		this.dsDepartment = dsDepartment;
	}
	/**
	 * 获取：部门
	 */
	public String getDsDepartment() {
		return dsDepartment;
	}
	/**
	 * 设置：
	 */
	public void setDsLinetype(String dsLinetype) {
		this.dsLinetype = dsLinetype;
	}
	/**
	 * 获取：
	 */
	public String getDsLinetype() {
		return dsLinetype;
	}
	/**
	 * 设置：
	 */
	public void setDsIpname(String dsIpname) {
		this.dsIpname = dsIpname;
	}
	/**
	 * 获取：
	 */
	public String getDsIpname() {
		return dsIpname;
	}
	/**
	 * 设置：
	 */
	public void setDsIp(String dsIp) {
		this.dsIp = dsIp;
	}
	/**
	 * 获取：
	 */
	public String getDsIp() {
		return dsIp;
	}
	/**
	 * 设置：
	 */
	public void setDsDatabasename(String dsDatabasename) {
		this.dsDatabasename = dsDatabasename;
	}
	/**
	 * 获取：
	 */
	public String getDsDatabasename() {
		return dsDatabasename;
	}
	/**
	 * 设置：
	 */
	public void setDsUsername(String dsUsername) {
		this.dsUsername = dsUsername;
	}
	/**
	 * 获取：
	 */
	public String getDsUsername() {
		return dsUsername;
	}
	/**
	 * 设置：
	 */
	public void setDsPassword(String dsPassword) {
		this.dsPassword = dsPassword;
	}
	/**
	 * 获取：
	 */
	public String getDsPassword() {
		return dsPassword;
	}
}
