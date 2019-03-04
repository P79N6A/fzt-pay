package cn.yesway.pay.order.entity;

import java.io.Serializable;

/**
 *   角色表
 *  2017年2月16日上午9:40:48
 *  role
 */
public class Role implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7840119748361816772L;

	

	/** 角色id. */
	private String roleid;

	/** 角色名称. */
	private String rolename;

	/** 角色编号. */
	private String rolecode;

	/** 使用状态. */
	private Integer status;

	/** 备注. */
	private String remark;

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid == null ? null : roleid.trim();
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename == null ? null : rolename.trim();
	}

	public String getRolecode() {
		return rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode == null ? null : rolecode.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}