package cn.yesway.pay.order.dao;

import cn.yesway.pay.order.entity.Role;

public interface RoleDao {
    int deleteByPrimaryKey(String roleid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String roleid);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}