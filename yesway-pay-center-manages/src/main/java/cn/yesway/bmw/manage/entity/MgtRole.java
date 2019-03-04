package cn.yesway.bmw.manage.entity;


public class MgtRole extends BaseEntity {
 
	private static final long serialVersionUID = 1L;

	/**
     * @备注:     
     * @字段:RoleId VARCHAR(36)  
     */	
	private java.lang.String roleId;

	/**
     * @备注:     
     * @字段:Group VARCHAR(100)  
     */	
	private java.lang.String group;

	/**
     * @备注:     
     * @字段:RoleName VARCHAR(36)  
     */	
	private java.lang.String roleName;

	/**
     * @备注:     
     * @字段:Remark VARCHAR(128)  
     */	
	private java.lang.String remark;

	/**
     * @备注:0可用，1禁用，2删除     
     * @字段:Status INT(10)  
     */	
	private java.lang.Integer status;
	public MgtRole(){
	}

	public MgtRole(
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
	public void setGroup(java.lang.String group) {
		this.group = group;
	}
	 
	public java.lang.String getGroup() {
		return this.group;
	}
	public void setRoleName(java.lang.String roleName) {
		this.roleName = roleName;
	}
	 
	public java.lang.String getRoleName() {
		return this.roleName;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	 
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	 
	public java.lang.Integer getStatus() {
		return this.status;
	}
}
