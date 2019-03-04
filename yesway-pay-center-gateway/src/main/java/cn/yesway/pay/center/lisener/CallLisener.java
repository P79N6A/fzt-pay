package cn.yesway.pay.center.lisener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.yesway.pay.center.util.AppConfig;
import cn.yesway.pay.center.util.PropUtils;

public class CallLisener implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(CallLisener.class);

	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("[yesway-pay-center-gateway  关闭]");
	}

	// 加载配置文件
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("[yesway-pay-center-gateway 启动]");
		
		AppConfig.configure(arg0.getServletContext().getRealPath("/") + "/WEB-INF/appconfig.xml");
		
//		logger.info(">> 初始化配置...");
//		PropUtils.init(arg0.getServletContext().getRealPath("/")+"/environmentType.properties");
//		String sysType = PropUtils.get("sys_type");
//		logger.info(">> 系统环境：" + sysType);
//		PropUtils.init("/" + sysType + "/config.global.properties");
	}

}
