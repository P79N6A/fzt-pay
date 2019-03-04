package cn.yesway.pay.order.dao;

import cn.yesway.pay.order.entity.Menu;

public interface MenuDao {
    int deleteByPrimaryKey(Integer menucode);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer menucode);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
}