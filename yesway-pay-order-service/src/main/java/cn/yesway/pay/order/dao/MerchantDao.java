package cn.yesway.pay.order.dao;

import cn.yesway.pay.order.entity.Merchant;

public interface MerchantDao {
    int deleteByPrimaryKey(String mchid);

    int insert(Merchant record);

    int insertSelective(Merchant record);

    Merchant selectByPrimaryKey(String mchid);

    int updateByPrimaryKeySelective(Merchant record);

    int updateByPrimaryKey(Merchant record);
}