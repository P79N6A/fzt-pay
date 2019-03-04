package cn.yesway.bmw.manage.shiro;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.yesway.bmw.manage.entity.MgtRole;
import cn.yesway.bmw.manage.entity.MgtUser;
import cn.yesway.bmw.manage.service.MgtUserService;

public class AuthenticationRealm extends AuthorizingRealm {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private MgtUserService userService;
	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String userName = token.getUsername();
		MgtUser user = userService.getUserByLoginName(userName);
		if (user != null && userName != null && !"".equals(userName)) {
			// 从数据库或 接口通过userName获取用户信息
//			user.setLoginName(userName);
			//密码为admin（SHA1加密后）
//			user.setPassword(password);
			
			String password = new String(token.getPassword());
			if (!user.getPassword().equals(password)) {
				throw new IncorrectCredentialsException();
			}
			return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
		} else {
			return null;
		}
	
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	*/
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		MgtUser user = (MgtUser) principals.getPrimaryPrincipal();
		if (user != null) {
			//List<String> authCodeList = userService.getAuthorityByUserId(user.getUserId());
			List<MgtRole> roleList = userService.getRolesByUserId(user.getUserId());
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			for(MgtRole role : roleList){
				info.addRole(role.getRoleId());
			}
			//info.addStringPermissions(authCodeList);
			return info;
		}
		return null;
	}
	
}
