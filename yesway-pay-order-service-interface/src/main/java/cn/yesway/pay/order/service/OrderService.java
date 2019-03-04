
package cn.yesway.pay.order.service;
import java.util.List;

import cn.yesway.pay.order.entity.OrderPayStatus;
import cn.yesway.pay.order.entity.Orders;
import cn.yesway.pay.order.entity.Refund;

/**
 * @author ghk
 *  2017年2月9日下午4:08:51
 *  OrderService订单接口
 */
public interface OrderService {

	
	/**
	 * 订单查询
	 */
	public List<Orders> orderquery(String outTradeNo);
	
	/**
	 * 退款
	 */
	public int refund(Refund refund);
	
	/**
	 * 查询退款信息
	 * @param outTradeNo
	 * @return
	 */
	public  List<Refund>  selectRefundInfo(String outTradeNo); 
	
	/**查询
	 * @param outTradeNo
	 * @return
	 */
	public Refund selectqueryRefundInfo(String outTradeNo);
	
	public int insert(OrderPayStatus record);
	
	/**
	 * 根据消息队列里的消息来更新订单状态
	 * @param message
	 * @return
	 */
	public Integer updateOrderStatusByMQMessage(String message);
}
