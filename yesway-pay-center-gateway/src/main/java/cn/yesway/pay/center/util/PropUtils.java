package cn.yesway.pay.center.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 获取properties配置文件配置的工具类<br>
 * 本类提供了加载properties配置文件的一系列工具方法<br>
 * 
 * @author Mr.Mol
 *
 */
public class PropUtils {
	private static Map<String, String> cache = new HashMap<String, String>();

	static {
		init("/config.global.properties");
	}

	/**
	 * 初始化<br>
	 * <br>
	 * 在static块中调用本方法来初始化类<br>
	 * <br>
	 * 
	 * @param paths
	 *            配置文件的路径数组<br>
	 *            <br>
	 */
	public static void init(String... paths) {
		/* 清空map中的数据 */
		cache.clear();
		/* 装载配置文件中的数据 */
		for (String path : paths) {
			load(path);
		}
	}

	/**
	 * 将一个peoperties文件中的数据装入map<br>
	 * <br>
	 * 
	 * @param filePath
	 *            要读取的配置文件的路径，如"/config.properties"<br>
	 *            <br>
	 */
	public static void load(String filePath) {
		/* 装载配置文件 */
		Properties global = new Properties();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(PropUtils.class.getResourceAsStream(filePath)));
			global.load(br);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/* 将配置文件中的数据读入map */
		Set keys = global.keySet();
		Iterator it = keys.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String value = global.getProperty(key);
			cache.put(key, value);
		}
	}

	/**
	 * 获取配置信息<br>
	 * <br>
	 * 
	 * @param key
	 *            配置信息对应的key。<br>
	 *            <br>
	 * @return
	 */
	public static String get(String key) {
		return cache.get(key);
	}

	/**
	 * 获取配置信息<br>
	 * <br>
	 * 
	 * @param key
	 *            配置信息对应的key。<br>
	 *            <br>
	 * @return
	 */
	public static Integer getInteger(String key) {
		return Integer.valueOf(cache.get(key));
	}
}