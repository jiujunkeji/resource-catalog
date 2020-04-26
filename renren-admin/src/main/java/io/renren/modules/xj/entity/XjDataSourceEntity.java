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
	private Long dsId;
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
	 * 连接类型
	 */
	private String dsLinetype;
	/**
	 * 主机名
	 */
	private String dsIpname;
	/**
	 * 主机ip
	 */
	private String dsIp;
	/**
	 * 端口号
	 */
	private Integer dsPort;

	public Integer getDsPort() {
		return dsPort;
	}

	public void setDsPort(Integer port) {
		this.dsPort = port;
	}

	/**
	 * 数据库名
	 */
	private String dsDatabasename;
	/**
	 * 用户名
	 */
	private String dsUsername;
	/**
	 * 密码
	 */
	private String dsPassword;
	/**
	 * 创建时间
	 */
	private Date dsCreatetime;
	/**
	 * 修改时间
	 */
	private Date dsUpdatetime;

	public Date getDsCreatetime() {
		return dsCreatetime;
	}

	public void setDsCreatetime(Date dsCreatetime) {
		this.dsCreatetime = dsCreatetime;
	}

	public Date getDsUpdatetime() {
		return dsUpdatetime;
	}

	public void setDsUpdatetime(Date dsUpdatetime) {
		this.dsUpdatetime = dsUpdatetime;
	}

	/**
	 * 设置：
	 */
	public void setDsId(Long dsId) {
		this.dsId = dsId;
	}
	/**
	 * 获取：
	 */
	public Long getDsId() {
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
