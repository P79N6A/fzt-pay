package cn.yesway.pay.order.dao;

import cn.yesway.pay.order.entity.Permission;

public interface PermissionDao {
    int deleteByPrimaryKey(String permissionid);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(String permissionid);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}