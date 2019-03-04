package cn.yesway.pay.order.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 证书表
 *  2017年2月16日上午9:42:54
 *  credential
 */
public class Credential implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3605119164042011719L;

	/** 证书id主键. */
	private String credid;

	/** 证书名称. */
	private String credname;

	/** 证书权属. */
	private String ownership;
	
	private String consumer;

	/** 证书状态. */
	private Integer credstatus;

	/** 证书. */
	private String credential;

	/** 证书类型. */
	private String signtype;

	/** 证书失效时间. */
	private Date timeexpire;

	/** 创建时间. */
	private Date createtime;

	/** 更新时间. */
	private Date updatetime;

	/** 创建人. */
	private String creater;

	/** 更新人. */
	private String updater;

	public String getCredid() {
		return credid;
	}

	public void setCredid(String credid) {
		this.credid = credid == null ? null : credid.trim();
	}

	public String getCredname() {
		return credname;
	}

	public void setCredname(String credname) {
		this.credname = credname == null ? null : credname.trim();
	}

	public String getOwnership() {
		return ownership;
	}

	public void setFrom(String ownership) {
		this.ownership = ownership == null ? null : ownership.trim();
	}

	
	public String getConsumer() {
		return consumer;
	}

	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}

	public Integer getCredstatus() {
		return credstatus;
	}

	public void setCredstatus(Integer credstatus) {
		this.credstatus = credstatus;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential == null ? null : credential.trim();
	}

	public String getSigntype() {
		return signtype;
	}

	public void setSigntype(String signtype) {
		this.signtype = signtype == null ? null : signtype.trim();
	}

	public Date getTimeexpire() {
		return timeexpire;
	}

	public void setTimeexpire(Date timeexpire) {
		this.timeexpire = timeexpire;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater == null ? null : updater.trim();
	}
}