package cn.yesway.bmw.manage.shiro;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class AuthenticationFilter extends FormAuthenticationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		// ajax访问，可修改为返回自定义json数据
		String s = httpServletRequest.getHeader("X-Requested-With");
		if (s != null && s.equalsIgnoreCase("XMLHttpRequest")) {
			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.setContentType("application/json");
			PrintWriter out = httpServletResponse.getWriter();
			out.println("{\"errmsg\":\"用户未登录\"}");
			out.flush();
			out.close();
			return false;
		} else {
			return super.onAccessDenied(request, response);
		}
	}
}
