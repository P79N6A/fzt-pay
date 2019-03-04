package cn.yesway.pay.order.dao;

import java.util.List;

import cn.yesway.pay.order.entity.PayCenterConfiguration;


/**
 * PayCenterConfigurationDao
 */
public interface PayCenterConfigurationDao  {

	public PayCenterConfiguration findByCondition(PayCenterConfiguration obj);
	
	public List<PayCenterConfiguration> findList(PayCenterConfiguration obj);
}
