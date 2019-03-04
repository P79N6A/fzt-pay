package cn.yesway.bmw.manage.entity;


public class MgtUser extends BaseEntity {
 
	private static final long serialVersionUID = 1L;

	/**
     * @备注:     
     * @字段:UserId VARCHAR(36)  
     */	
	private java.lang.String userId;

	/**
     * @备注:     
     * @字段:Group VARCHAR(100)  
     */	
	private java.lang.String group;

	/**
     * @备注:     
     * @字段:LoginName VARCHAR(50)  
     */	
	private java.lang.String loginName;

	/**
     * @备注:     
     * @字段:Password VARCHAR(100)  
     */	
	private java.lang.String password;

	/**
     * @备注:     
     * @字段:FirstName VARCHAR(50)  
     */	
	private java.lang.String firstName;

	/**
     * @备注:     
     * @字段:LastName VARCHAR(50)  
     */	
	private java.lang.String lastName;

	/**
     * @备注:     
     * @字段:Title VARCHAR(50)  
     */	
	private java.lang.String title;

	/**
     * @备注:     
     * @字段:Gender INT(10)  
     */	
	private java.lang.Integer gender;

	/**
     * @备注:     
     * @字段:Email VARCHAR(100)  
     */	
	private java.lang.String email;

	/**
     * @备注:     
     * @字段:PhoneNumber VARCHAR(50)  
     */	
	private java.lang.String phoneNumber;

	/**
     * @备注:     
     * @字段:AddTime DATETIME(19)  
     */	
	private java.util.Date addTime;

	/**
	 * @备注:支付宝应用id
	 * @字段:app_id VARCHAR(50)
	 */
	private java.lang.String appId;
	public MgtUser(){
	}

	public MgtUser(
		java.lang.String userId
	){
		this.userId = userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	 
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setGroup(java.lang.String group) {
		this.group = group;
	}
	 
	public java.lang.String getGroup() {
		return this.group;
	}
	public void setLoginName(java.lang.String loginName) {
		this.loginName = loginName;
	}
	 
	public java.lang.String getLoginName() {
		return this.loginName;
	}
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	 
	public java.lang.String getPassword() {
		return this.password;
	}
	public void setFirstName(java.lang.String firstName) {
		this.firstName = firstName;
	}
	 
	public java.lang.String getFirstName() {
		return this.firstName;
	}
	public void setLastName(java.lang.String lastName) {
		this.lastName = lastName;
	}
	 
	public java.lang.String getLastName() {
		return this.lastName;
	}
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	 
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setGender(java.lang.Integer gender) {
		this.gender = gender;
	}
	 
	public java.lang.Integer getGender() {
		return this.gender;
	}
	public void setEmail(java.lang.String email) {
		this.email = email;
	}
	 
	public java.lang.String getEmail() {
		return this.email;
	}
	public void setPhoneNumber(java.lang.String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	 
	public java.lang.String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setAddTime(java.util.Date addTime) {
		this.addTime = addTime;
	}
	 
	public java.util.Date getAddTime() {
		return this.addTime;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
}
