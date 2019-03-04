package cn.yesway.bmw.manage.entity;


public class Role extends BaseEntity {
 
	private static final long serialVersionUID = 1L;

	/**
     * @备注:角色ID，主键     
     * @字段:roleId VARCHAR(50)  
     */	
	private java.lang.String roleId;

	/**
     * @备注:     
     * @字段:roleName VARCHAR(50)  
     */	
	private java.lang.String roleName;

	/**
     * @备注:     
     * @字段:roleCode VARCHAR(50)  
     */	
	private java.lang.String roleCode;

	/**
     * @备注:1启用，0停用     
     * @字段:status INT(10)  
     */	
	private java.lang.Integer status;

	/**
     * @备注:     
     * @字段:remark VARCHAR(100)  
     */	
	private java.lang.String remark;
	public Role(){
	}

	public Role(
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
	public void setRoleName(java.lang.String roleName) {
		this.roleName = roleName;
	}
	 
	public java.lang.String getRoleName() {
		return this.roleName;
	}
	public void setRoleCode(java.lang.String roleCode) {
		this.roleCode = roleCode;
	}
	 
	public java.lang.String getRoleCode() {
		return this.roleCode;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	 
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	 
	public java.lang.String getRemark() {
		return this.remark;
	}
}
