package cn.yesway.pay.order.service;

import java.util.List;

import cn.yesway.pay.order.entity.Orders;



public interface GetwayService {
	
	/**统一下单
	 * @param order 
	 * 			订单实体
	 * @return OrderResult
	 */
	public int unifiedOrder(Orders order);
	
	
	/**订单查询
	 * @param outTradeNo
	 * @return OrderResult
	 */
	public Orders orderQuery(String outTradeNo);  
	
	/**关闭订单
	 * @param outTradeNo
	 * @return OrderResult
	 */
	public int closeOrder(String outTradeNo);
	
	/**
	 * 查询同一个订单号（outTradeNo）是否已经存在
	 * @param outTradeNo
	 * @return
	 */
	public int queryOrdersCount(String outTradeNo);
	
}
