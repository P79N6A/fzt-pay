package cn.yesway.bmw.manage.entity;


public class MgtAuthority extends BaseEntity {
 
	private static final long serialVersionUID = 1L;

	/**
     * @备注:     
     * @字段:AuthorityCode VARCHAR(100)  
     */	
	private java.lang.String authorityCode;

	/**
     * @备注:     
     * @字段:Group VARCHAR(100)  
     */	
	private java.lang.String group;

	/**
     * @备注:     
     * @字段:AuthorityName VARCHAR(50)  
     */	
	private java.lang.String authorityName;

	/**
     * @备注:     
     * @字段:Remark VARCHAR(128)  
     */	
	private java.lang.String remark;

	/**
     * @备注:     
     * @字段:Status INT(10)  
     */	
	private java.lang.Integer status;
	public MgtAuthority(){
	}

	public MgtAuthority(
		java.lang.String authorityCode
	){
		this.authorityCode = authorityCode;
	}

	public void setAuthorityCode(java.lang.String authorityCode) {
		this.authorityCode = authorityCode;
	}
	 
	public java.lang.String getAuthorityCode() {
		return this.authorityCode;
	}
	public void setGroup(java.lang.String group) {
		this.group = group;
	}
	 
	public java.lang.String getGroup() {
		return this.group;
	}
	public void setAuthorityName(java.lang.String authorityName) {
		this.authorityName = authorityName;
	}
	 
	public java.lang.String getAuthorityName() {
		return this.authorityName;
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
