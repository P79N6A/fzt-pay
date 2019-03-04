package cn.yesway.pay.order.entity;

import java.io.Serializable;


/**
 * @author 支付记录配置表
 *  2017年12月12日下午5:06:09
 *  PayCenterConfiguration
 */
public class PayCenterConfiguration implements Serializable {
 
	private static final long serialVersionUID = 1L;

	/**
     * @备注:主键id     
     * @字段:payConfigId VARCHAR(10)  
     */	
	private java.lang.String payConfigId;

	/**
     * @备注:yesway分配商家     
     * @字段:oemId VARCHAR(50)  
     */	
	private java.lang.String oemId;

	/**
     * @备注:yesway分配给商家的唯一标识(如果没有的话，默认为all)     
     * @字段:mchId VARCHAR(50)  
     */	
	private java.lang.String mchId;

	/**
     * @备注:商家名称     
     * @字段:mchName VARCHAR(100)  
     */	
	private java.lang.String mchName;

	/**
     * @备注:各个商家的微信、支付宝唯一id值     
     * @字段:appId VARCHAR(50)  
     */	
	private java.lang.String appId;

	/**
     * @备注:商家微信开通的商家号     
     * @字段:weixinMchId VARCHAR(50)  
     */	
	private java.lang.String weixinMchId;

	/**
     * @备注:商户秘钥     
     * @字段:weixinApiSecret VARCHAR(100)  
     */	
	private java.lang.String weixinApiSecret;

	/**
     * @备注:回调地址     
     * @字段:notityUrl VARCHAR(200)  
     */	
	private java.lang.String notityUrl;

	/**
     * @备注:支付方式（1：支付宝，2：微信）     
     * @字段:payToolType INT(10)  
     */	
	private java.lang.Integer payToolType;

	/**
     * @备注:yesway的aes加密秘钥     
     * @字段:aesKey VARCHAR(2000)  
     */	
	private java.lang.String aesKey;

	/**
     * @备注:证书地址     
     * @字段:credentialAdress VARCHAR(1000)  
     */	
	private java.lang.String credentialAdress;
	
	/**
	 * 各系统与支付中心加密公钥
	 */
	private String yeswayPublicKey;
	
	/**
	 * 各系统与支付中心解密私钥
	 */
	private String yeswayPrivateKey;
	
	/**
	 * 支付宝公钥
	 */
	private String alipayPublicKey;
	
	/**
	 * 九五智驾yesway和支付宝之间的私钥
	 */
	private String yeswayAlipayPrivateKey;
	
	/**
	 * 回调客户端证书路径
	 */
	private String notifyClient;
	
	/**
	 * 回调客户端证书提取密码
	 */
	private String notifyClientPWD;
	
	/**
	 * 回调客户端服务端证书路径
	 */
	private String notifyServer;
	
	/**
	 * 回调客户端服务端提取密码
	 */
	private String notifyServerPWD;
	
	/**
	 * 支付宝微信回调地址
	 */
	private String thirdNotifyUrl;
	
	public PayCenterConfiguration(){
	}

	public PayCenterConfiguration(
		java.lang.String payConfigId
	){
		this.payConfigId = payConfigId;
	}

	public void setPayConfigId(java.lang.String payConfigId) {
		this.payConfigId = payConfigId;
	}
	 
	public java.lang.String getPayConfigId() {
		return this.payConfigId;
	}
	public void setOemId(java.lang.String oemId) {
		this.oemId = oemId;
	}
	 
	public java.lang.String getOemId() {
		return this.oemId;
	}
	public void setMchId(java.lang.String mchId) {
		this.mchId = mchId;
	}
	 
	public java.lang.String getMchId() {
		return this.mchId;
	}
	public void setMchName(java.lang.String mchName) {
		this.mchName = mchName;
	}
	 
	public java.lang.String getMchName() {
		return this.mchName;
	}
	public void setAppId(java.lang.String appId) {
		this.appId = appId;
	}
	 
	public java.lang.String getAppId() {
		return this.appId;
	}
	public void setWeixinMchId(java.lang.String weixinMchId) {
		this.weixinMchId = weixinMchId;
	}
	 
	public java.lang.String getWeixinMchId() {
		return this.weixinMchId;
	}
	public void setWeixinApiSecret(java.lang.String weixinApiSecret) {
		this.weixinApiSecret = weixinApiSecret;
	}
	 
	public java.lang.String getWeixinApiSecret() {
		return this.weixinApiSecret;
	}
	public void setNotityUrl(java.lang.String notityUrl) {
		this.notityUrl = notityUrl;
	}
	 
	public java.lang.String getNotityUrl() {
		return this.notityUrl;
	}
	public void setPayToolType(java.lang.Integer payToolType) {
		this.payToolType = payToolType;
	}
	 
	public java.lang.Integer getPayToolType() {
		return this.payToolType;
	}
	public void setAesKey(java.lang.String aesKey) {
		this.aesKey = aesKey;
	}
	 
	public java.lang.String getAesKey() {
		return this.aesKey;
	}
	public void setCredentialAdress(java.lang.String credentialAdress) {
		this.credentialAdress = credentialAdress;
	}
	 
	public String getYeswayPublicKey() {
		return yeswayPublicKey;
	}

	public void setYeswayPublicKey(String yeswayPublicKey) {
		this.yeswayPublicKey = yeswayPublicKey;
	}

	public String getYeswayPrivateKey() {
		return yeswayPrivateKey;
	}

	public void setYeswayPrivateKey(String yeswayPrivateKey) {
		this.yeswayPrivateKey = yeswayPrivateKey;
	}

	public java.lang.String getCredentialAdress() {
		return this.credentialAdress;
	}

	public String getAlipayPublicKey() {
		return alipayPublicKey;
	}

	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}

	public String getYeswayAlipayPrivateKey() {
		return yeswayAlipayPrivateKey;
	}

	public void setYeswayAlipayPrivateKey(String yeswayAlipayPrivateKey) {
		this.yeswayAlipayPrivateKey = yeswayAlipayPrivateKey;
	}

	public String getNotifyClient() {
		return notifyClient;
	}

	public void setNotifyClient(String notifyClient) {
		this.notifyClient = notifyClient;
	}

	public String getNotifyClientPWD() {
		return notifyClientPWD;
	}

	public void setNotifyClientPWD(String notifyClientPWD) {
		this.notifyClientPWD = notifyClientPWD;
	}

	public String getNotifyServer() {
		return notifyServer;
	}

	public void setNotifyServer(String notifyServer) {
		this.notifyServer = notifyServer;
	}

	public String getNotifyServerPWD() {
		return notifyServerPWD;
	}

	public void setNotifyServerPWD(String notifyServerPWD) {
		this.notifyServerPWD = notifyServerPWD;
	}

	public String getThirdNotifyUrl() {
		return thirdNotifyUrl;
	}

	public void setThirdNotifyUrl(String thirdNotifyUrl) {
		this.thirdNotifyUrl = thirdNotifyUrl;
	}
	
}
