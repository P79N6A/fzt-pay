
package cn.yesway.bmw.manage.utils;

import java.io.BufferedReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;


import org.apache.log4j.Logger;
//import org.apache.log4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

//import org.slf4j.Logger;
public class HttpUtil {

	
		private static final Logger LoggerFactory = null;
		private static final Logger logger =  LoggerFactory.getLogger(HttpUtil.class);

		/**
		 * 发送HTTP GET请求
		 */
		public static String doGet(String url, String param, Map<String, String> headers, String charset) {
			StringBuffer response = new StringBuffer();

			BufferedReader in = null;
			try {
				String _url = url;
				if (param != null) {
					_url += url.contains("?") ? "&" + param : "?" + param;
				}
				logger.debug("request url:" + _url);
				// 打开和URL之间的连接
				URLConnection conn = (new URL(_url)).openConnection();
				// 设置通用的请求属性
				if (headers != null) {
					for (Map.Entry<String, String> header : headers.entrySet()) {
						conn.setRequestProperty(header.getKey(), header.getValue());
					}
				}
				conn.connect();
				// 遍历所有的响应头字段
				logger.debug("response headers:");
				Map<String, List<String>> map = conn.getHeaderFields();
				if (map != null) {
					for (String key : map.keySet()) {
						logger.debug("\t" + key + ": " + map.get(key));
					}
				}
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
				String line;
				while ((line = in.readLine()) != null) {
					response.append(line);
				}
				logger.debug("response body:" + response);
			} catch (Exception e) {
				logger.error("error:", e);
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (IOException ex) {
				}
			}
			return response.toString();
		}

		/**
		 * 发起Http-POST请求
		 * 
		 * @param url
		 *            目标地址
		 * @param content
		 *            发送的请求内容，允许填null或空串
		 * @param headers
		 *            头信息
		 * @param charset
		 *            发送请求使用的编码，UTF-8、GBK等
		 * @return 回复信息转换为字符串
		 */
		public static String doPost(String url, String content, Map<String, String> headers, String charset) {
			StringBuffer response = new StringBuffer();
			OutputStream out = null;
			BufferedReader in = null;
			try {
				logger.debug("request url:" + url);
				logger.debug("request content:" + content);
				// 打开和URL之间的连接
				URLConnection conn = (new URL(url)).openConnection();
				// 设置通用的请求属性
				if (headers != null) {
					for (Map.Entry<String, String> header : headers.entrySet()) {
						conn.setRequestProperty(header.getKey(), header.getValue());
					}
				}
				// 发送POST请求必须设置如下两行
				conn.setDoOutput(true);
				conn.setDoInput(true);
				// 获取URLConnection对象对应的输出流
				out = conn.getOutputStream();
				if (content != null) {
					out.write(content.getBytes(charset));
				}
				out.flush();

				// 遍历所有的响应头字段
				logger.debug("response headers:");
				Map<String, List<String>> map = conn.getHeaderFields();
				if (map != null) {
					for (String key : map.keySet()) {
						logger.debug("\t" + key + ": " + map.get(key));
					}
				}

				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
				String line;
				while ((line = in.readLine()) != null) {
					response.append(line);
				}
				logger.debug("response body:" + response);
			} catch (Exception e) {
				logger.error("error:", e);
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (IOException ex) {
				}
				try {
					if (in != null) {
						in.close();
					}
				} catch (IOException ex) {
				}
			}
			return response.toString();
		}

		public static void main(String[] args) {
			HttpUtil.doGet("http://www.baidu.com?a=1", null, null, "utf-8");
		}
	//}

}
