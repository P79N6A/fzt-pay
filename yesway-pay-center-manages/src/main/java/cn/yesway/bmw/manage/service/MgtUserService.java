package cn.yesway.bmw.manage.service;

import java.util.List;

import cn.yesway.bmw.manage.entity.MgtRole;
import cn.yesway.bmw.manage.entity.MgtUser;

/**
 * MgtUserService接口
 */
public interface MgtUserService extends BaseService<MgtUser, java.lang.String> {

	MgtUser getUserByLoginName(String userName);

	List<MgtRole> getRolesByUserId(String userId);

}
