package cn.yesway.pay.order.dao;

import org.apache.ibatis.annotations.Param;

import cn.yesway.pay.order.entity.Configuration;

public interface ConfigurationDao {
	public Configuration get(@Param("name")String name,@Param("key")String key);
}
