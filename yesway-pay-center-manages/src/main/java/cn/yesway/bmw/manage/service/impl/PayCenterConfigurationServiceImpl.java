package cn.yesway.bmw.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yesway.bmw.manage.dao.PayCenterConfigurationDao;
import cn.yesway.bmw.manage.entity.Pager;
import cn.yesway.bmw.manage.entity.PayCenterConfiguration;
import cn.yesway.bmw.manage.service.PayCenterConfigurationService;

/**
 * 基本CURD操作在MybatisBaseServiceImpl中实现 否则自行声明接口，实现方法
 */
@Service
public class PayCenterConfigurationServiceImpl extends MybatisBaseServiceImpl<PayCenterConfiguration, java.lang.String> implements PayCenterConfigurationService {

	@Autowired
	private PayCenterConfigurationDao payCenterConfigurationDao;
	
	public PayCenterConfiguration findByCondition(PayCenterConfiguration obj) {
		return payCenterConfigurationDao.findByCondition(obj);
	}

	public List<PayCenterConfiguration> findList(PayCenterConfiguration obj) {
		return payCenterConfigurationDao.findList(obj);
	}

	

}
