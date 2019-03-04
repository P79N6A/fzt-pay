package cn.yesway.bmw.manage.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yesway.bmw.manage.dao.MgtRoleDao;
import cn.yesway.bmw.manage.entity.MgtRole;
import cn.yesway.bmw.manage.entity.MgtUser;
import cn.yesway.bmw.manage.service.MgtUserService;


/**
 * 基本CURD操作在MybatisBaseServiceImpl中实否则自行声明接口，实现
 */
@Service
public class MgtUserServiceImpl extends MybatisBaseServiceImpl<MgtUser, java.lang.String> implements MgtUserService {
	
	@Autowired
	private MgtRoleDao roleDao;

	public MgtUser getUserByLoginName(String loginName) {
		if(StringUtils.isBlank(loginName)){
			return null;
		}
		MgtUser user = new MgtUser();
		user.setLoginName(loginName);
		List<MgtUser> list = super.findList(user);
		if(list==null||list.size()<1){
			return null;
		}
		return list.get(0);
	}
	public List<MgtRole> getRolesByUserId(String userId) {
		// TODO Auto-generated method stub
		List<MgtRole> roleList = roleDao.getRolesByUserId(userId);
		return roleList;
	}
	
	
}
