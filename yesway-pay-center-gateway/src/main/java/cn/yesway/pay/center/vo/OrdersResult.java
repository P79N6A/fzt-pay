package cn.yesway.pay.center.vo;

import cn.yesway.pay.order.entity.Orders;





public class OrdersResult {
	/**
	 * 消息头
	 */
	private MessageHeader messageheader;
	private Boolean result;
	private Orders order;
	public MessageHeader getMessageheader() {
		return messageheader;
	}
	public void setMessageheader(MessageHeader messageheader) {
		this.messageheader = messageheader;
	}
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	
}
