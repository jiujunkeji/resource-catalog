package io.renren.modules.resource.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author wangdehai
 * @email 594340717@qq.com
 * @date 2019-07-25 16:57:52
 */
@TableName("organisation_info")
public class OrganisationInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long organisationId;
	/**
	 * 机构名称
	 */
	private String organisationName;
	/**
	 * 机构地址
	 */
	private String organisationAddr;
	/**
	 * 联系人
	 */
	private String linkman;
	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 设置：
	 */
	public void setOrganisationId(Long organisationId) {
		this.organisationId = organisationId;
	}
	/**
	 * 获取：
	 */
	public Long getOrganisationId() {
		return organisationId;
	}
	/**
	 * 设置：机构名称
	 */
	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}
	/**
	 * 获取：机构名称
	 */
	public String getOrganisationName() {
		return organisationName;
	}
	/**
	 * 设置：机构地址
	 */
	public void setOrganisationAddr(String organisationAddr) {
		this.organisationAddr = organisationAddr;
	}
	/**
	 * 获取：机构地址
	 */
	public String getOrganisationAddr() {
		return organisationAddr;
	}
	/**
	 * 设置：联系人
	 */
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	/**
	 * 获取：联系人
	 */
	public String getLinkman() {
		return linkman;
	}
	/**
	 * 设置：联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：联系电话
	 */
	public String getPhone() {
		return phone;
	}
}
