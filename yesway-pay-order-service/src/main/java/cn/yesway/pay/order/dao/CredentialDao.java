package cn.yesway.pay.order.dao;

import org.apache.ibatis.annotations.Param;

import cn.yesway.pay.order.entity.Credential;


public interface CredentialDao {
    int deleteByPrimaryKey(String credid);

    int insert(Credential record);

    int insertSelective(Credential record);

    Credential selectByPrimaryKey(String credid);

    int updateByPrimaryKeySelective(Credential record);

    int updateByPrimaryKey(Credential record);

    Credential selectCredential(@Param("from") String from,@Param("to") String to, @Param("signType") String signType);
}