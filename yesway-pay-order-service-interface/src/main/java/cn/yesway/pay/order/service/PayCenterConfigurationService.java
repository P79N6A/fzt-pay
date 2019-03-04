package cn.yesway.pay.order.service;

import java.util.List;

import cn.yesway.pay.order.entity.PayCenterConfiguration;


/**
 * PayCenterConfigurationService接口
 */
public interface PayCenterConfigurationService  {

	public  PayCenterConfiguration findByCondition(PayCenterConfiguration obj);
	
	public List<PayCenterConfiguration> findList(PayCenterConfiguration obj);
}
