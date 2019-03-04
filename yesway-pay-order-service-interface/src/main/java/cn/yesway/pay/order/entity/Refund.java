package cn.yesway.pay.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *  退款表
 *  2017年2月16日上午9:41:15
 *  refund
 */
public class Refund implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4095917309591458966L;

	private String refundid;

	/** 商户订单号. */
	private String outTradeNo;

	/** 订单号. */
	private String orderid;

	/** 退款金额. */
	private BigDecimal refundamount;

	/** 申请时间. */
	private Date addtime;

	/** 结束时间. */
	private Date endtime;

	/** 退款原因. */
	private String refundreason;
	
	/**
	 * 流水号
	 */
	private String payOrderNo;
	
	
	

	public String getRefundid() {
		return refundid;
	}

	public void setRefundid(String refundid) {
		this.refundid = refundid == null ? null : refundid.trim();
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid == null ? null : orderid.trim();
	}

	public BigDecimal getRefundamount() {
		return refundamount;
	}

	public void setRefundamount(BigDecimal refundamount) {
		this.refundamount = refundamount;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getRefundreason() {
		return refundreason;
	}

	public void setRefundreason(String refundreason) {
		this.refundreason = refundreason == null ? null : refundreason.trim();
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}
	
}