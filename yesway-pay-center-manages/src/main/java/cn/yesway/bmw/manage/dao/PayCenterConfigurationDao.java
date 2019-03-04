package cn.yesway.bmw.manage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import cn.yesway.bmw.manage.entity.PayCenterConfiguration;
import cn.yesway.bmw.manage.entity.Role;


/**
 * PayCenterConfigurationDao
 */
@Repository
public interface PayCenterConfigurationDao  extends BaseDao<PayCenterConfiguration, java.lang.String>{

	public PayCenterConfiguration findByCondition(PayCenterConfiguration obj);
	
	public List<PayCenterConfiguration> findList(PayCenterConfiguration obj);
}
