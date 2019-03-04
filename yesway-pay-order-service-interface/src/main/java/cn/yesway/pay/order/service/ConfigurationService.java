package cn.yesway.pay.order.service;

import cn.yesway.pay.order.entity.Configuration;

public interface ConfigurationService {
	public Configuration get(String name,String key);
}
