package cn.yesway.bmw.manage.dao;

import org.springframework.stereotype.Repository;

import cn.yesway.bmw.manage.entity.MgtUserRole;


/**
 * MgtUserRoleDao
 */
@Repository
public interface MgtUserRoleDao extends BaseDao<MgtUserRole, java.lang.String> {

	void deleteByUserId(String userId);

}
