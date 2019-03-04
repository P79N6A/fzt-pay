package cn.yesway.bmw.manage.entity;


public class MgtRoleAuthority extends BaseEntity {
 
	private static final long serialVersionUID = 1L;

	/**
     * @备注:     
     * @字段:AuthorityCode VARCHAR(100)  
     */	
	private java.lang.String authorityCode;

	/**
     * @备注:     
     * @字段:RoleId VARCHAR(36)  
     */	
	private java.lang.String roleId;
	public MgtRoleAuthority(){
	}

	public MgtRoleAuthority(
		java.lang.String authorityCode,
		java.lang.String roleId
	){
		this.authorityCode = authorityCode;
		this.roleId = roleId;
	}

	public void setAuthorityCode(java.lang.String authorityCode) {
		this.authorityCode = authorityCode;
	}
	 
	public java.lang.String getAuthorityCode() {
		return this.authorityCode;
	}
	public void setRoleId(java.lang.String roleId) {
		this.roleId = roleId;
	}
	 
	public java.lang.String getRoleId() {
		return this.roleId;
	}
}
