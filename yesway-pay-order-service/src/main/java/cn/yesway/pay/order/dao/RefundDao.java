package cn.yesway.pay.order.dao;

import java.util.List;

import cn.yesway.pay.order.entity.Refund;

public interface RefundDao {
    int deleteByPrimaryKey(String refundid);

    int insert(Refund record);

    int insertSelective(Refund record);

    Refund selectByPrimaryKey(String outTradeNo);
    
    List<Refund> selectRefundInfo(String outTradeNo); 

    int updateByPrimaryKeySelective(Refund record);

    int updateByPrimaryKey(Refund record);
}