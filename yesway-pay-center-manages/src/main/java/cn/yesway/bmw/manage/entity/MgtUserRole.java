package cn.yesway.bmw.manage.entity;


public class MgtUserRole extends BaseEntity {
 
	private static final long serialVersionUID = 1L;

	/**
     * @备注:     
     * @字段:RoleId VARCHAR(36)  
     */	
	private java.lang.String roleId;

	/**
     * @备注:     
     * @字段:UserId VARCHAR(36)  
     */	
	private java.lang.String userId;
	public MgtUserRole(){
	}

	public MgtUserRole(
		java.lang.String roleId,
		java.lang.String userId
	){
		this.roleId = roleId;
		this.userId = userId;
	}

	public void setRoleId(java.lang.String roleId) {
		this.roleId = roleId;
	}
	 
	public java.lang.String getRoleId() {
		return this.roleId;
	}
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	 
	public java.lang.String getUserId() {
		return this.userId;
	}
}
