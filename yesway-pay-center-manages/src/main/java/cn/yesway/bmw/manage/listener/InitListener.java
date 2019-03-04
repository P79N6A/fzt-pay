package cn.yesway.bmw.manage.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.yesway.bmw.manage.utils.PropUtils;



/**
 * 系统监控类 
 * @author 
 * 实现加载配置文件 
 */
public class InitListener implements ServletContextListener {
	private static final Logger log = LoggerFactory
			.getLogger(InitListener.class);

	public void contextDestroyed(ServletContextEvent arg0) { 
		log.info("【yesway-pay-center-manages  关闭】");
	}
	public void contextInitialized(ServletContextEvent arg0) {		
		log.info("【yesway-pay-center-manages  启动】");
		PropUtils.init("/environmentType.properties");
		String sysType = PropUtils.get("sys_type");
		PropUtils.init("/" + sysType + "/config.global.properties");
	}
	
}
