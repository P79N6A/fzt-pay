package cn.yesway.pay.order.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 订单支付状态记录表
 *  2017年2月16日上午9:43:39
 *  orderPayStatus
 */
public class OrderPayStatus implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -258367585410803243L;

	

	private String id;

	/** 商户订单号. */
	private String outTradeNo;

	/** 支付状态. */
	private Integer paystatus;

	private Date createtime;
	/** 更新时间. */
	private Date updatetime;

	/** 状态描述. */
	private String statusdesc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
	}

	public Integer getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(Integer paystatus) {
		this.paystatus = paystatus;
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

	public String getStatusdesc() {
		return statusdesc;
	}

	public void setStatusdesc(String statusdesc) {
		this.statusdesc = statusdesc == null ? null : statusdesc.trim();
	}
}