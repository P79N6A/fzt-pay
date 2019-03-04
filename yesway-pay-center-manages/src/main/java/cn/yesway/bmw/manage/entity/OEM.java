package cn.yesway.bmw.manage.entity;

import java.util.Date;

public class OEM extends BaseEntity  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9188317004592193549L;

	/** OEM编码的ID. */
	private String oemId;

	/** OEM名称. */
	private String oemName;

	/** 订单回调地址. */
	private String notifyUrl;

	/** signKey. */
	private String signKey;

	/** 创建时间. */
	private Date createTime;

	/** 修改时间. */
	private Date updateTime;

	/** 创建人. */
	private String creater;

	/** 修改人. */
	private String updater;

	public String getOemId() {
		return oemId;
	}

	public void setOemId(String oemId) {
		this.oemId = oemId;
	}

	public String getOemName() {
		return oemName;
	}

	public void setOemName(String oemName) {
		this.oemName = oemName;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getSignKey() {
		return signKey;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	
}