package cn.yesway.pay.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yesway.pay.order.dao.PayCenterConfigurationDao;
import cn.yesway.pay.order.entity.PayCenterConfiguration;

/**
 * 基本CURD操作在MybatisBaseServiceImpl中实现 否则自行声明接口，实现方法
 */
@Service
public class PayCenterConfigurationServiceImpl  implements PayCenterConfigurationService {

	@Autowired
	private PayCenterConfigurationDao payCenterConfigurationDao;
	
	@Override
	public PayCenterConfiguration findByCondition(PayCenterConfiguration obj) {
		return payCenterConfigurationDao.findByCondition(obj);
	}

	@Override
	public List<PayCenterConfiguration> findList(PayCenterConfiguration obj) {
		return payCenterConfigurationDao.findList(obj);
	}

	

}
