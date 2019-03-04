package cn.yesway.bmw.manage.entity;


public class MerchantsAudit extends BaseEntity {
 
	private static final long serialVersionUID = 1L;

	/**
     * @备注:主键（外部商户id）     
     * @字段:external_id VARCHAR(50)  
     */	
	private String externalId;

	/**
     * @备注:支付宝应用Id     
     * @字段:app_id VARCHAR(50)  
     */	
	private String appId;

	/**
     * @备注:申请单id     
     * @字段:order_id VARCHAR(50)  
     */	
	private String orderId;

	/**
     * @备注:二级商户id     
     * @字段:sm_id VARCHAR(50)  
     */	
	private String smId;

	/**
     * @备注:卡编号     
     * @字段:card_alias_no VARCHAR(50)  
     */	
	private String cardAliasNo;

	/**
     * @备注:审核备注信息     
     * @字段:memo VARCHAR(500)  
     */	
	private String memo;

	/**
     * @备注:拒绝原因     
     * @字段:reason VARCHAR(500)  
     */	
	private String reason;

	/**
     * @备注:商户简称     
     * @字段:merchants VARCHAR(50)  
     */	
	private String merchants;

	/**
     * @备注:联系电话     
     * @字段:phone VARCHAR(20)  
     */	
	private String phone;

	/**
     * @备注:创建时间     
     * @字段:add_time DATE(10)  
     */	
	private java.util.Date addTime;

	/**
     * @备注:修改时间     
     * @字段:update_time DATE(10)  
     */	
	private java.util.Date updateTime;

	/**
     * @备注:审核状态:1.已保存，未提交审核2.审核中3.审核失败4.审核通过     
     * @字段:audit_status INT(10)  
     */	
	private Integer auditStatus;

	/**
     * @备注:业务状态:1.正常2.停用     
     * @字段:business_status INT(10)  
     */	
	private Integer businessStatus;

	/**
     * @备注:是否删除
:0.未删除1.已删除     
     * @字段:is_delete INT(10)  
     */	
	private Integer isDelete;

	/**
     * @备注:操作员id     
     * @字段:user_id VARCHAR(36)  
     */	
	private String userId;

	/**
     * @备注:审核提交内容     
     * @字段:audit_context TEXT(65535)  
     */	
	private String auditContext;
	public MerchantsAudit(){
	}

	public MerchantsAudit(
		String externalId
	){
		this.externalId = externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	 
	public String getExternalId() {
		return this.externalId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	 
	public String getAppId() {
		return this.appId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	 
	public String getOrderId() {
		return this.orderId;
	}
	public void setSmId(String smId) {
		this.smId = smId;
	}
	 
	public String getSmId() {
		return this.smId;
	}
	public void setCardAliasNo(String cardAliasNo) {
		this.cardAliasNo = cardAliasNo;
	}
	 
	public String getCardAliasNo() {
		return this.cardAliasNo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	 
	public String getMemo() {
		return this.memo;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	 
	public String getReason() {
		return this.reason;
	}
	public void setMerchants(String merchants) {
		this.merchants = merchants;
	}
	 
	public String getMerchants() {
		return this.merchants;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	 
	public String getPhone() {
		return this.phone;
	}
	public void setAddTime(java.util.Date addTime) {
		this.addTime = addTime;
	}
	 
	public java.util.Date getAddTime() {
		return this.addTime;
	}
	 
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	 
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	 
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	 
	public Integer getAuditStatus() {
		return this.auditStatus;
	}
	public void setBusinessStatus(Integer businessStatus) {
		this.businessStatus = businessStatus;
	}
	 
	public Integer getBusinessStatus() {
		return this.businessStatus;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	 
	public Integer getIsDelete() {
		return this.isDelete;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	 
	public String getUserId() {
		return this.userId;
	}
	public void setAuditContext(String auditContext) {
		this.auditContext = auditContext;
	}
	 
	public String getAuditContext() {
		return this.auditContext;
	}
}
