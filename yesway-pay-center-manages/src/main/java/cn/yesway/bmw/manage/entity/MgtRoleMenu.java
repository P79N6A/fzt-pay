package cn.yesway.bmw.manage.entity;


public class MgtRoleMenu extends BaseEntity {
 
	private static final long serialVersionUID = 1L;

	/**
     * @备注:角色ID，主键     
     * @字段:roleId VARCHAR(50)  
     */	
	private java.lang.String roleId;

	/**
     * @备注:菜单id     
     * @字段:MenuId VARCHAR(36)  
     */	
	private java.lang.String menuId;

	/**
     * @备注:     
     * @字段:remark VARCHAR(100)  
     */	
	private java.lang.String remark;
	public MgtRoleMenu(){
	}

	public MgtRoleMenu(
		java.lang.String roleId
	){
		this.roleId = roleId;
	}

	public void setRoleId(java.lang.String roleId) {
		this.roleId = roleId;
	}
	 
	public java.lang.String getRoleId() {
		return this.roleId;
	}
	public void setMenuId(java.lang.String menuId) {
		this.menuId = menuId;
	}
	 
	public java.lang.String getMenuId() {
		return this.menuId;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	 
	public java.lang.String getRemark() {
		return this.remark;
	}
}
