package cn.yesway.pay.order.dao;

import cn.yesway.pay.order.entity.OEM;


public interface OEMDao {
    int deleteByPrimaryKey(String oemid);

    int insert(OEM record);

    int insertSelective(OEM record);

    OEM selectByPrimaryKey(String oemid);

    int updateByPrimaryKeySelective(OEM record);

    int updateByPrimaryKey(OEM record);
}