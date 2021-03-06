package io.renren.modules.xj.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.xml.crypto.Data;
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
	 * 数据源
	 */
	private int ktrDsid;

	public int getKtrDsid() {
		return ktrDsid;
	}

	public void setKtrDsid(int ktrDsid) {
		this.ktrDsid = ktrDsid;
	}

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
	 * 备注
	 */
	private String ktrText;
	/**
	 * 创建时间
	 */
	private Date ktrCreatetime;
	/**
	 * 修改时间
	 */
	private Date ktrUpdatetime;
	/**
	 * 查询数量
	 */
	private Integer ktrNumber;
	/**
	 *状态
	 * 0 未执行 1 正在执行 2 执行成功 3 执行失败
	 */
	private String ktrStatus;

	private Long catalogId;

	public Integer getKtrNumber() { return ktrNumber; }

	public void setKtrNumber(Integer ktrNumber) { this.ktrNumber = ktrNumber; }

	public String getKtrStatus() {
		return ktrStatus;
	}

	public void setKtrStatus(String ktrStatus) {
		this.ktrStatus = ktrStatus;
	}


	public String getKtrText() {
		return ktrText;
	}

	public void setKtrText(String ktrText) {
		this.ktrText = ktrText;
	}

	public Date getKtrCreatetime() {
		return ktrCreatetime;
	}

	public void setKtrCreatetime(Date ktrCreatetime) {
		this.ktrCreatetime = ktrCreatetime;
	}

	public Date getKtrUpdatetime() {
		return ktrUpdatetime;
	}

	public void setKtrUpdatetime(Date ktrUpdatetime) {
		this.ktrUpdatetime = ktrUpdatetime;
	}

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


	public String getKtrDsname() {
		return ktrDsname;
	}

	public void setKtrDsname(String ktrDsname) {
		this.ktrDsname = ktrDsname;
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

	public Long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;
	}
}
