package cn.yesway.bmw.manage.entity;

public class MgtUserAuthority extends BaseEntity {
 
	private static final long serialVersionUID = 1L;

	/**
     * @备注:     
     * @字段:AuthorityCode VARCHAR(100)  
     */	
	private java.lang.String authorityCode;

	/**
     * @备注:     
     * @字段:UserId VARCHAR(36)  
     */	
	private java.lang.String userId;
	public MgtUserAuthority(){
	}

	public MgtUserAuthority(
		java.lang.String authorityCode,
		java.lang.String userId
	){
		this.authorityCode = authorityCode;
		this.userId = userId;
	}

	public void setAuthorityCode(java.lang.String authorityCode) {
		this.authorityCode = authorityCode;
	}
	 
	public java.lang.String getAuthorityCode() {
		return this.authorityCode;
	}
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	 
	public java.lang.String getUserId() {
		return this.userId;
	}
}
