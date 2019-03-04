package cn.yesway.pay.order.dao;

import cn.yesway.pay.order.entity.Account;


public interface AccountDao {
    int deleteByPrimaryKey(String accountid);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(String accountid);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);
}