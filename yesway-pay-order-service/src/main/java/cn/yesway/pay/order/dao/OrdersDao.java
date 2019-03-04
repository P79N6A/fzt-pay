package cn.yesway.pay.order.dao;

import java.util.List;

import cn.yesway.pay.order.entity.Orders;



public interface OrdersDao {
    int deleteByPrimaryKey(String orderid);

    int insert(Orders record);

    int insertSelective(Orders record);

    List<Orders> selectByPrimaryKey(String outTradeNo);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);
    
    
    Orders orderQuery(String outTradeNo);
    
    int closeOrder(String outTradeNo);
    
    int queryOrdersCount(String outTradeNo);

	int updateStatusByCode(Orders record);
}