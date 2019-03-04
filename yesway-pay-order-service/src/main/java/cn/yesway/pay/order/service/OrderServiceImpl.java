
package cn.yesway.pay.order.service;

import java.sql.Timestamp;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yesway.pay.order.dao.OrderPayStatusDao;
import cn.yesway.pay.order.dao.OrdersDao;
import cn.yesway.pay.order.dao.RefundDao;
import cn.yesway.pay.order.entity.OrderPayStatus;
import cn.yesway.pay.order.entity.Orders;
import cn.yesway.pay.order.entity.Refund;

/**
 * @author ghk
 *  2017年2月13日下午5:53:51
 *  OrderServiceImpl
 */
@Service
public class OrderServiceImpl implements OrderService{
	private Logger logger = Logger.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrdersDao orderDao;
	@Autowired
	private OrderPayStatusDao orderPayStatusDao;
	@Autowired
	private RefundDao refundDao;
	
	
	//订单查询
	@Override
	public List<Orders> orderquery(String outTradeNo) {
		return orderDao.selectByPrimaryKey(outTradeNo);
	}
	//申请退款
	@Override
	public int refund(Refund refund) {
       int record = refundDao.insert(refund); 
       if(record>0){
    	   logger.info("插入refund表数据成功");
       }else{
    	  logger.error("插入refund表数据失败"); 
       }
		return record;
	}
	

	private static String getSeverity(String[] strings){
	    if (strings.length < 1)
	            return "info";
	    return strings[0];
	  }
	private static String getMessage(String[] strings) {
		if (strings.length < 1)
			return "info: Hello World!";
		return joinStrings(strings, " ");
	}

	private static String joinStrings(String[] strings, String delimiter) {
		int length = strings.length;
		if (length == 0)
			return "";
		StringBuilder words = new StringBuilder(strings[0]);
		for (int i = 1; i < length; i++) {
			words.append(delimiter).append(strings[i]);
		}
		return words.toString();
	}


	@Override
	public  List<Refund> selectRefundInfo(String outTradeNo) {
		return refundDao.selectRefundInfo(outTradeNo);
	}
	
	@Override
	public Refund selectqueryRefundInfo(String outTradeNo) {
		return refundDao.selectByPrimaryKey(outTradeNo);
	}
	@Override
	public int insert(OrderPayStatus record) {
		return orderPayStatusDao.insert(record);
	}
	@Override
	public Integer updateOrderStatusByMQMessage(String message) {
		int record = 0;
		if(StringUtils.isEmpty(message)){
			return 0;
		}
		JSONObject object = JSONObject.fromObject(message);
		JSONObject sdata = (JSONObject)object.get("sdata");	
		if(sdata ==null){
			return 0;
		}
		String payStatus = (String) sdata.get("payStatus");
		if(StringUtils.isNotEmpty(payStatus) && payStatus.equals("1")){//只有支付状态为1：成功
			Orders order = new Orders();
			order.setOutTradeNo((String)sdata.get("out_trade_no"));
			order.setOrderstatus(1);// 订单状态
			order.setPaystatus(1);// 支付状态
			order.setTradeNo((String)sdata.get("trade_no"));
			order.setUpdatetime(new Timestamp(System.currentTimeMillis()));
			record = orderDao.updateStatusByCode(order);
			if (record > 0) {
				logger.info(">>>更新支付中心订单Orders表成功!");
			} else {
				logger.error(">>>更新支付中心订单Orders表失败!");
			}
		}else if(StringUtils.isNotEmpty(payStatus) && payStatus.equals("2")){//支付成功，但不允许退款
			Orders order = new Orders();
			order.setOutTradeNo((String)sdata.get("out_trade_no"));
			order.setOrderstatus(1);// 订单状态
			order.setPaystatus(2);// 支付状态
			order.setTradeNo((String)sdata.get("trade_no"));
			order.setUpdatetime(new Timestamp(System.currentTimeMillis()));
			record = orderDao.updateStatusByCode(order);
			if (record > 0) {
				logger.info(">>>更新支付中心订单Orders表成功!");
			} else {
				logger.error(">>>更新支付中心订单Orders表失败!");
			}
		}
		return record;
	}


}
