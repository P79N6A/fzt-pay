package cn.yesway.pay.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yesway.pay.order.dao.ConfigurationDao;
import cn.yesway.pay.order.entity.Configuration;

@Service
public class ConfigurationServiceImpl implements ConfigurationService{
	@Autowired
	private ConfigurationDao configurationDao;

	@Override
	public Configuration get(String name, String key) {
		return configurationDao.get(name, key);
	}
	
}
