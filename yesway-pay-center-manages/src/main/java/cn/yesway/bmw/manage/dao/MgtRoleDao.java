package cn.yesway.bmw.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.yesway.bmw.manage.entity.MgtRole;


/**
 * MgtRoleDao
 */
@Repository
public interface MgtRoleDao extends BaseDao<MgtRole, java.lang.String> {

	List<MgtRole> getRolesByUserId(@Param("userId") String userId);

	MgtRole getByName(String roleName);

}
