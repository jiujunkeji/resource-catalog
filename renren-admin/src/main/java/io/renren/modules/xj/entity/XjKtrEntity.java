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
 * @date 2020-04-23 14:31:38
 */
@TableName("xj_ktr")
public class XjKtrEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private Long ktrId;
	/**
	 * 转换名
	 */
	private String ktrName;
	/**
	 * 数据库名
	 */
	private String ktrDsname;
	/**
	 * 表名
	 */
	private String ktrTablename;
	/**
	 * sql
	 */
	private String ktrSql;


	/**
	 * 设置：
	 */
	public void setKtrId(Long ktrId) {
		this.ktrId = ktrId;
	}
	/**
	 * 获取：
	 */
	public Long getKtrId() {
		return ktrId;
	}
	/**
	 * 设置：
	 */
	public void setKtrName(String ktrName) {
		this.ktrName = ktrName;
	}
	/**
	 * 获取：
	 */
	public String getKtrName() {
		return ktrName;
	}
	/**
	 * 设置：
	 */
	public void setKtrDsname(String ktrDsname) {
		this.ktrDsname = ktrDsname;
	}
	/**
	 * 获取：
	 */
	public String getKtrDsname() {
		return ktrDsname;
	}
	/**
	 * 设置：
	 */
	public void setKtrTablename(String ktrTablename) {
		this.ktrTablename = ktrTablename;
	}
	/**
	 * 获取：
	 */
	public String getKtrTablename() {
		return ktrTablename;
	}
	/**
	 * 设置：
	 */
	public void setKtrSql(String ktrSql) {
		this.ktrSql = ktrSql;
	}
	/**
	 * 获取：
	 */
	public String getKtrSql() {
		return ktrSql;
	}
}
