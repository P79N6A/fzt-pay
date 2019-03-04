package cn.yesway.pay.order.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 商户表
 *  2017年2月16日上午9:44:44
 *  merchant
 */
public class Merchant implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6742017603681554493L;

	/** oem下的商户. */
	private String mchid;

	/** 商户名称. */
	private String mchname;

	/** oem的id. */
	private String oemid;

	private String signkey;

	/** 创建时间. */
	private Date createtime;

	/** 修改时间. */
	private Date updatetime;

	/** 创建人. */
	private String creater;

	/** 修改人. */
	private String updater;

	public String getMchid() {
		return mchid;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid == null ? null : mchid.trim();
	}

	public String getMchname() {
		return mchname;
	}

	public void setMchname(String mchname) {
		this.mchname = mchname == null ? null : mchname.trim();
	}

	public String getOemid() {
		return oemid;
	}

	public void setOemid(String oemid) {
		this.oemid = oemid == null ? null : oemid.trim();
	}

	public String getSignkey() {
		return signkey;
	}

	public void setSignkey(String signkey) {
		this.signkey = signkey == null ? null : signkey.trim();
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