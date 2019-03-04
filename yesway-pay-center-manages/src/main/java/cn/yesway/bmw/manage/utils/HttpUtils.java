package cn.yesway.bmw.manage.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

	// 发送http get请求获取返回的string内容
	public static String httpGet(String urlStr, String charsetName,
			String contentType) throws Exception {
		log.debug("httpGet Start:{urlStr:" + urlStr + ",charsetName:"
				+ charsetName + ",contentType:" + contentType + "}");
		String result = "";
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.connect();
		BufferedReader breader = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), charsetName));
		String s = null;
		while ((s = breader.readLine()) != null) {
			result += s;
		}
		breader.close();
		conn.disconnect();
		log.debug("httpGet End:{result:" + result + "}");
		return result;
	}

	// 发送http post请求获取返回的string内容
	public static String httpPost(String urlStr, String param, String charsetName,
			String contentType) throws Exception {
		log.debug("httpPost Start:{urlStr:" + urlStr + ",param:" + param
				+ ",charsetName:" + charsetName + ",contentType:" + contentType
				+ "}");
		String result = "";
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
		conn.setRequestProperty("Content-Type", contentType);
		conn.setRequestProperty("Authorization", " Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");

		OutputStreamWriter writer = new OutputStreamWriter(
				conn.getOutputStream(), charsetName);
		writer.write(param);
		writer.flush();
		writer.close();
		InputStreamReader reader = new InputStreamReader(conn.getInputStream(),
				charsetName);
		BufferedReader breader = new BufferedReader(reader);
		String content = "";
		while ((content = breader.readLine()) != null) {
			result += content;
		}
		reader.close();
		conn.disconnect();
		log.debug("httpPost End:{result:" + result + "}");
		return result;
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
			log.debug("request url:" + url);
			log.debug("request content:" + content);
			// 打开和URL之间的连接
			URLConnection conn = (new URL(url)).openConnection();
			// 设置通用的请求属性
			if (headers != null) {
				for (Map.Entry<String, String> header : headers.entrySet()) {
					conn.setRequestProperty(header.getKey(), header.getValue());
				}
			}
			// 发送POST请求必须设置如下两行
			conn.setRequestProperty("Content-Type", "application/json"); 
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = conn.getOutputStream();
			if (content != null) {
				out.write(content.getBytes("UTF-8"));
			}
			out.flush();

			// 遍历所有的响应头字段
			log.debug("response headers:");
			Map<String, List<String>> map = conn.getHeaderFields();
			if (map != null) {
				for (String key : map.keySet()) {
					log.debug("\t" + key + ": " + map.get(key));
				}
			}

			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			String line;
			while ((line = in.readLine()) != null) {
				response.append(line);
			}
			log.debug("response body:" + response);
		} catch (Exception e) {
			log.error("error:", e);
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
}
