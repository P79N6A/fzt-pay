
package cn.yesway.pay.center.util;


import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class AppConfig {
	static Logger logger = Logger.getLogger(AppConfig.class);
	private static final AppConfig appConfig = new AppConfig();

	private static String appConfigpath;
	private static String confText = "";
	private static Map propertyCache;
	
	
/**	
	static {
	      String appConfigpath = "E:/MyEclipseWorkspace/smsweb/WEB-INF/appconfig.xml";
	      appConfigpath = appConfigpath.replaceAll("file:/", "");
	      appConfig.configure(appConfigpath);

	}
*/
	public static String getParameter(String name) {
		return appConfig.getProperty(name);
	}

	public static Map getPropertyCache() {
		return appConfig.getPropertys();
	}

	public static void reload() {
		try {
			String appConfigpath = AppConfig.class.getResource("/").toString();
			appConfigpath = appConfigpath.substring(0, appConfigpath
					.lastIndexOf("/"));
			appConfigpath = appConfigpath.substring(0, appConfigpath
					.lastIndexOf("/"));
			appConfigpath = appConfigpath.substring(0, appConfigpath
					.lastIndexOf("/"));
			appConfigpath += "/WEB-INF/appconfig.xml";
			appConfigpath = appConfigpath.replaceAll("file:", "");
			appConfig.configure(appConfigpath);

		} catch (Exception e) {
			logger.error("Error creating XML parser", e);
		}
	}

	//private Map propertyCache;

	public AppConfig() {

	}

	public static String getConfText() {
		return confText;
	}

	public static void saveConf(String confText) {
		try {
			FileWriter fw = new FileWriter(appConfigpath);
			confText = confText.replaceAll("&amp;", "&");
			confText = confText.replaceAll("&", "&amp;");
			fw.write(confText);
			fw.close();
		} catch (Exception e) {
			logger.error("Error save conf File:", e);
		}

	}

	public AppConfig(String filename) {
		appConfigpath = filename;
		propertyCache = new HashMap();
		try {
			SAXReader saxReader = new SAXReader();
			org.dom4j.Document doc = saxReader.read(new File(appConfigpath));
			confText = doc.asXML();
			Iterator elements = doc.getRootElement().elementIterator();
			while (elements.hasNext()) {
				Element subelement = (Element) elements.next();
				String subelementName = subelement.getName();
				if ("param".equals(subelementName)) {
					Attribute key = subelement.attribute("key");
					propertyCache.put(key.getValue(), subelement.getText());
				}
			}
		} catch (Exception e) {
			logger.error("Error creating XML parser", e);
		}
	}

	public static void configure(String filename) {
		appConfigpath = filename;
		if (propertyCache != null) {
			propertyCache.clear();
		} else {
			propertyCache = new HashMap();
		}
		try {
			SAXReader saxReader = new SAXReader();
			org.dom4j.Document doc = saxReader.read(new File(filename));
			confText = doc.asXML();
			Iterator elements = doc.getRootElement().elementIterator();
			while (elements.hasNext()) {
				Element subelement = (Element) elements.next();
				String subelementName = subelement.getName();
				/*if ("param".equals(subelementName)) {
					Attribute key = subelement.attribute("key");
					propertyCache.put(key.getValue(), subelement.getText());
				}*/
			}
		} catch (Exception e) {
			//logger.error(e);
			e.printStackTrace();
		}
	}

	public String getProperty(String name) {
		if (propertyCache.containsKey(name)) {
			return (String) propertyCache.get(name);
		} else {
			return null;
		}

	}
	

	public Map getPropertys() {
		return propertyCache;
	}

	public static void main(String[] args) {
		String appConfigpath = AppConfig.class.getResource("/").toString();
		System.out.println("appConfigpath1:" + appConfigpath);
		appConfigpath = appConfigpath.substring(0, appConfigpath
				.lastIndexOf("/"));
		System.out.println("appConfigpath2:" + appConfigpath);
		appConfigpath = appConfigpath.substring(0, appConfigpath
				.lastIndexOf("/"));
		System.out.println("appConfigpath3:" + appConfigpath);
		appConfigpath = appConfigpath.substring(0, appConfigpath
				.lastIndexOf("/"));
		System.out.println("appConfigpath4:" + appConfigpath);
		appConfigpath += "/WEB-INF/appconfig.xml";
		System.out.println("appConfigpath5:" + appConfigpath);
		appConfigpath = appConfigpath.replaceAll("file:", "");
		System.out.println("AppConfig:" + appConfigpath);

		AppConfig appconfig = new AppConfig(appConfigpath);
		AppConfig.configure(appConfigpath);
		System.out.println(AppConfig.getParameter("Webservice.Address"));
		String confText = AppConfig.confText;
		System.out.println(confText);
		System.out.println(appconfig.getProperty("SOSServerIP"));

	}
}

