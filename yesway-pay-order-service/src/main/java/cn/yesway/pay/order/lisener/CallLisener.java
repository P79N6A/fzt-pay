
package cn.yesway.pay.order.lisener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import cn.yesway.pay.order.utils.AppConfig;


	public class CallLisener extends HttpServlet implements ServletContextListener {

	    /**
		 * 
		 */
		private static final long serialVersionUID = 7884058757467968298L;
		public void contextDestroyed(ServletContextEvent arg0) {

	    }
	   //加载配置文件
	    public void contextInitialized(ServletContextEvent arg0) {
	    	AppConfig.configure(arg0.getServletContext().getRealPath("/")+ "/WEB-INF/appconfig.xml");   
	    	/*AppConfig.configure(arg0.getServletContext().getRealPath("/")+ "/WEB-INF/struts-html.tld");   
	    	AppConfig.configure(arg0.getServletContext().getRealPath("/")+ "/WEB-INF/struts-logic.tld");   
	    	AppConfig.configure(arg0.getServletContext().getRealPath("/")+ "/WEB-INF/struts-bean.tld");*/   
	    }
	    
}
