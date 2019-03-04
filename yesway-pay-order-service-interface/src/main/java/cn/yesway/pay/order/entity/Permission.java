package cn.yesway.pay.order.entity;

import java.io.Serializable;

/**
 * @author 权限表
 *  2017年2月16日上午9:41:45
 *  permission
 */
public class Permission implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1996401253163933042L;

	private String permissionid;

	/** 权限编码. */
	private String permissioncode;

	/** 权限名称. */
	private String permissionname;

	/** 菜单编码. */
	private Integer menucode;

	public String getPermissionid() {
		return permissionid;
	}

	public void setPermissionid(String permissionid) {
		this.permissionid = permissionid == null ? null : permissionid.trim();
	}

	public String getPermissioncode() {
		return permissioncode;
	}

	public void setPermissioncode(String permissioncode) {
		this.permissioncode = permissioncode == null ? null : permissioncode
				.trim();
	}

	public String getPermissionname() {
		return permissionname;
	}

	public void setPermissionname(String permissionname) {
		this.permissionname = permissionname == null ? null : permissionname
				.trim();
	}

	public Integer getMenucode() {
		return menucode;
	}

	public void setMenucode(Integer menucode) {
		this.menucode = menucode;
	}
}