package cn.yesway.bmw.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yesway.bmw.manage.dao.MgtRoleDao;
import cn.yesway.bmw.manage.entity.MgtRole;
import cn.yesway.bmw.manage.service.MgtRoleService;

/**
 * 基本CURD操作在MybatisBaseServiceImpl中实现 否则自行声明接口，实现方法
 */
@Service
public class MgtRoleServiceImpl extends MybatisBaseServiceImpl<MgtRole, java.lang.String> implements MgtRoleService {

	@Autowired
	MgtRoleDao mgtRoleDao;
	
	public MgtRole getByName(String roleName) {
		return mgtRoleDao.getByName(roleName);
	}

	public List<MgtRole> getRoleByUserId(String userId) {
		return mgtRoleDao.getRolesByUserId(userId);
	}

}
