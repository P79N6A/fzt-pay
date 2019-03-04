package cn.yesway.pay.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yesway.pay.order.dao.OrdersDao;
import cn.yesway.pay.order.entity.Orders;
@Service
public class GetwayServiceImpl implements GetwayService {
	@Autowired
	private OrdersDao ordersDao;
	@Override
	public int unifiedOrder(Orders order) {		
		return ordersDao.insert(order);
	}

	@Override
	public Orders orderQuery(String outTradeNo) {
		return ordersDao.orderQuery(outTradeNo);
	}

	@Override
	public int closeOrder(String outTradeNo) {
		// TODO Auto-generated method stub
		return ordersDao.closeOrder(outTradeNo);
	}

	@Override
	public int queryOrdersCount(String outTradeNo) {
		return ordersDao.queryOrdersCount(outTradeNo);
	}

}
