package cn.yesway.bmw.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yesway.bmw.manage.dao.MgtUserRoleDao;
import cn.yesway.bmw.manage.entity.MgtUserRole;
import cn.yesway.bmw.manage.service.MgtUserRoleService;

/**
 * 基本CURD操作在MybatisBaseServiceImpl中实现 否则自行声明接口，实现方法
 */
@Service
public class MgtUserRoleServiceImpl extends MybatisBaseServiceImpl<MgtUserRole, java.lang.String> implements MgtUserRoleService {

	
	@Autowired
	MgtUserRoleDao mgtUserRoleDao;
	
	public void deleteByUserId(String userId) {
		mgtUserRoleDao.deleteByUserId(userId);
	}

}
