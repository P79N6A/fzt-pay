package cn.yesway.pay.order.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author OEM表
 *  2017年2月16日上午9:43:21
 *  OEM
 */
public class OEM implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1995396574835399970L;

	/** oemid. */
	private String oemid;

	/** oem名称. */
	private String oemname;

	/** 订单回调地址. */
	private String notifyurl;

	/** signkey. */
	private String signkey;

	/** 创建时间. */
	private Date createtime;

	/** 更新时间. */
	private Date updatetime;

	/** 创建人. */
	private String creater;

	/** 修改人. */
	private String updater;

	public String getOemid() {
		return oemid;
	}

	public void setOemid(String oemid) {
		this.oemid = oemid == null ? null : oemid.trim();
	}

	public String getOemname() {
		return oemname;
	}

	public void setOemname(String oemname) {
		this.oemname = oemname == null ? null : oemname.trim();
	}

	public String getNotifyurl() {
		return notifyurl;
	}

	public void setNotifyurl(String notifyurl) {
		this.notifyurl = notifyurl == null ? null : notifyurl.trim();
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