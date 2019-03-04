package cn.yesway.pay.order.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 账户表
 *  2017年2月16日上午9:42:41
 *  account
 */
public class Account implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -557190211304169757L;

	/** 账户表主键. */
	private String accountid;

	/** 登录名称. */
	private String userid;

	/** 姓名. */
	private String username;

	/** 密码. */
	private String password;

	/** 性别. */
	private String sex;

	/** 联系方式. */
	private String phone;

	/** 头衔. */
	private String jobtitle;

	/** 邮箱. */
	private String email;

	/** 应用id. */
	private String appid;

	/** 账户类型. */
	private Integer accounttype;

	/** 创建时间. */
	private Date createtime;

	/** 更新时间. */
	private Date updatetime;

	/** 创建人. */
	private String creater;

	/** 更新人. */
	private String upater;

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid == null ? null : accountid.trim();
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid == null ? null : userid.trim();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex == null ? null : sex.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getJobtitle() {
		return jobtitle;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle == null ? null : jobtitle.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid == null ? null : appid.trim();
	}

	public Integer getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(Integer accounttype) {
		this.accounttype = accounttype;
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

	public String getUpater() {
		return upater;
	}

	public void setUpater(String upater) {
		this.upater = upater == null ? null : upater.trim();
	}
}