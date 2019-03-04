package cn.yesway.bmw.manage.entity;

import java.io.Serializable;


/**
 * @author 支付记录配置表
 *  2017年12月12日下午5:06:09
 *  PayCenterConfiguration
 */
public class PayCenterConfiguration extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @备注:主键id
	 * @字段:payConfigId VARCHAR(50)
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
	 * @备注:yesway公钥
	 * @字段:yeswayPublicKey VARCHAR(2000)
	 */
	private java.lang.String yeswayPublicKey;

	/**
	 * @备注:yesway私钥
	 * @字段:yeswayPrivateKey VARCHAR(2000)
	 */
	private java.lang.String yeswayPrivateKey;

	/**
	 * @备注:alipay公钥
	 * @字段:alipayPublicKey VARCHAR(2000)
	 */
	private java.lang.String alipayPublicKey;

	/**
	 * @备注:yesway和支付宝之间的商户私钥
	 * @字段:yeswayAlipayPrivateKey VARCHAR(2000)
	 */
	private java.lang.String yeswayAlipayPrivateKey;

	/**
	 * @备注:
	 * @字段:thirdNotifyUrl VARCHAR(200)
	 */
	private java.lang.String thirdNotifyUrl;

	/**
	 * @备注:
	 * @字段:notifyServerPWD VARCHAR(20)
	 */
	private java.lang.String notifyServerPWD;

	/**
	 * @备注:
	 * @字段:notifyServer VARCHAR(50)
	 */
	private java.lang.String notifyServer;

	/**
	 * @备注:
	 * @字段:notifyClientPWD VARCHAR(20)
	 */
	private java.lang.String notifyClientPWD;

	/**
	 * @备注:
	 * @字段:notifyClient VARCHAR(50)
	 */
	private java.lang.String notifyClient;
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

	public java.lang.String getCredentialAdress() {
		return this.credentialAdress;
	}
	public void setYeswayPublicKey(java.lang.String yeswayPublicKey) {
		this.yeswayPublicKey = yeswayPublicKey;
	}

	public java.lang.String getYeswayPublicKey() {
		return this.yeswayPublicKey;
	}
	public void setYeswayPrivateKey(java.lang.String yeswayPrivateKey) {
		this.yeswayPrivateKey = yeswayPrivateKey;
	}

	public java.lang.String getYeswayPrivateKey() {
		return this.yeswayPrivateKey;
	}
	public void setAlipayPublicKey(java.lang.String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}

	public java.lang.String getAlipayPublicKey() {
		return this.alipayPublicKey;
	}
	public void setYeswayAlipayPrivateKey(java.lang.String yeswayAlipayPrivateKey) {
		this.yeswayAlipayPrivateKey = yeswayAlipayPrivateKey;
	}

	public java.lang.String getYeswayAlipayPrivateKey() {
		return this.yeswayAlipayPrivateKey;
	}
	public void setThirdNotifyUrl(java.lang.String thirdNotifyUrl) {
		this.thirdNotifyUrl = thirdNotifyUrl;
	}

	public java.lang.String getThirdNotifyUrl() {
		return this.thirdNotifyUrl;
	}
	public void setnotifyServerPWD(java.lang.String notifyServerPWD) {
		this.notifyServerPWD = notifyServerPWD;
	}

	public java.lang.String getnotifyServerPWD() {
		return this.notifyServerPWD;
	}
	public void setNotifyServer(java.lang.String notifyServer) {
		this.notifyServer = notifyServer;
	}

	public java.lang.String getNotifyServer() {
		return this.notifyServer;
	}
	public void setnotifyClientPWD(java.lang.String notifyClientPWD) {
		this.notifyClientPWD = notifyClientPWD;
	}

	public java.lang.String getnotifyClientPWD() {
		return this.notifyClientPWD;
	}
	public void setNotifyClient(java.lang.String notifyClient) {
		this.notifyClient = notifyClient;
	}

	public java.lang.String getNotifyClient() {
		return this.notifyClient;
	}
}
