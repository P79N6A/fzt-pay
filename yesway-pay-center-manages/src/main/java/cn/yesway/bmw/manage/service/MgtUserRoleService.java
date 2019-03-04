package cn.yesway.bmw.manage.service;

import cn.yesway.bmw.manage.entity.MgtUserRole;

/**
 * MgtUserRoleService接口
 */
public interface MgtUserRoleService extends BaseService<MgtUserRole, java.lang.String> {

	void deleteByUserId(String userId);

}
