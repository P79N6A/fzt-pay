package cn.yesway.pay.order.dao;

import cn.yesway.pay.order.entity.OrderPayStatus;



public interface OrderPayStatusDao {
    int deleteByPrimaryKey(String id);

    int insert(OrderPayStatus record);

    int insertSelective(OrderPayStatus record);

    OrderPayStatus selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderPayStatus record);

    int updateByPrimaryKey(OrderPayStatus record);
}