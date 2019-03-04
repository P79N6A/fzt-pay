
package cn.yesway.bmw.manage.interceptor;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.yesway.bmw.manage.entity.MgtUser;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author 对所有操作用户的记录
 *  2017年6月14日下午2:53:28
 *  ProtocolAuthInterceptor
 */
public class ProtocolAuthInterceptor extends AbstractInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1425677613693778015L;
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//得到session
		HttpSession session=ServletActionContext.getRequest().getSession(true);
		//从session取得用户对象
		MgtUser info=(MgtUser) session.getAttribute("");
		//获取请求的controller名称
		String controllerName=invocation.getInvocationContext().getName();
		//获取请求的远程IP地址
		String remoteIp=ServletActionContext.getRequest().getRemoteAddr();
		String actionNameFirst = controllerName.split("_")[0];
		if(info!=null){
			this.log.info("用户IP:"+remoteIp+" 用户单位:"+info.getGroup()+" 用户姓名:"+info.getLoginName()+"正在做:"+controllerName);
		}
		return null;
	}

}
