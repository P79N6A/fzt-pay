package cn.yesway.pay.center.vo;

import java.util.Date;

/**
 * @author 订单表
 *  2017年2月16日上午9:44:06
 *  orders
 */
public class OrdersVo {
	/** 订单id. */
	private String orderid;

	/** OEM Id. */
	private String oemid;

	/** 商户id. */
	private String mchid;

	/** 商户订单号. */
	private String outTradeNo;

	/** 预支付交易会话标识. */
	private String prepayId;

	/** 订单总金额. */
	private Integer totalAmount;

	/** 第三方支付工具类型，使用的是微信支付还是支付宝支付. */
	private Integer paytooltype;

	/** 商品标题. */
	private String subject;

	/** 商品描述. */
	private String body;

	private String goodsDetail;

	/** 附加数据. */
	private String attach;

	/** 终端ip. */
	private String spbillCreateIp;

	/** 交易起始时间. */
	private Date timeStart;

	/** 交易结束时间. */
	private Date timeExpire;

	/** 交易类型. */
	private String tradeType;

	/** 商店门店编号. */
	private String deviceInfo;

	/** 订单状态. */
	private Integer orderstatus;

	/** 支付状态. */
	private Integer paystatus;

	/** 交易凭证. */
	private String tradeNo;

	/** 创建时间. */
	private Date createtime;

	/** 更新时间. */
	private Date updatetime;

	/** 创建人. */
	private String creater;

	/** 修改人. */
	private String updater;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid == null ? null : orderid.trim();
	}

	public String getOemid() {
		return oemid;
	}

	public void setOemid(String oemid) {
		this.oemid = oemid == null ? null : oemid.trim();
	}

	public String getMchid() {
		return mchid;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid == null ? null : mchid.trim();
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId == null ? null : prepayId.trim();
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getPaytooltype() {
		return paytooltype;
	}

	public void setPaytooltype(Integer paytooltype) {
		this.paytooltype = paytooltype;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject == null ? null : subject.trim();
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body == null ? null : body.trim();
	}

	public String getGoodsDetail() {
		return goodsDetail;
	}

	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail == null ? null : goodsDetail.trim();
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach == null ? null : attach.trim();
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp == null ? null : spbillCreateIp
				.trim();
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeExpire() {
		return timeExpire;
	}

	public void setTimeExpire(Date timeExpire) {
		this.timeExpire = timeExpire;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType == null ? null : tradeType.trim();
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo == null ? null : deviceInfo.trim();
	}

	public Integer getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(Integer orderstatus) {
		this.orderstatus = orderstatus;
	}

	public Integer getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(Integer paystatus) {
		this.paystatus = paystatus;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo == null ? null : tradeNo.trim();
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater == null ? null : updater.trim();
	}
}