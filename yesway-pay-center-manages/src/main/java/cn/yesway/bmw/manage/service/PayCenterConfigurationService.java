package cn.yesway.bmw.manage.service;

import java.util.List;

import cn.yesway.bmw.manage.entity.MgtRole;
import cn.yesway.bmw.manage.entity.PayCenterConfiguration;


/**
 * PayCenterConfigurationService接口
 */
public interface PayCenterConfigurationService extends BaseService<PayCenterConfiguration, java.lang.String> {

	public  PayCenterConfiguration findByCondition(PayCenterConfiguration obj);
	
	public List<PayCenterConfiguration> findList(PayCenterConfiguration obj);
	
	
}
