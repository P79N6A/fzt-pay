package cn.yesway.bmw.manage.service;

import java.util.List;

import cn.yesway.bmw.manage.entity.MgtRole;

/**
 * MgtRoleService接口
 */
public interface MgtRoleService extends BaseService<MgtRole, java.lang.String> {

	MgtRole getByName(String roleName);

	List<MgtRole> getRoleByUserId(String userId);

}
