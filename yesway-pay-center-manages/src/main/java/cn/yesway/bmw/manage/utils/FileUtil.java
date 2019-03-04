package cn.yesway.bmw.manage.utils;

import cn.yesway.bmw.manage.dto.Range;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileUtil {
	static final Pattern RANGE_PATTERN = Pattern.compile("bytes \\d+-\\d+/\\d+");
	/**
	 * Acquired the file.
	 * @param key
	 * @return
	 * @throws IOException 
	 */
	public static File getFile(String tokenPath) throws IOException {
		if (StringUtils.isEmpty(tokenPath))return null;
		File tokenFile = new File(tokenPath);
		if (!tokenFile.getParentFile().exists())tokenFile.getParentFile().mkdirs();
		if (!tokenFile.exists())tokenFile.createNewFile();
		return tokenFile;
	}
	
	public static String generateFileName(){
		return System.currentTimeMillis() + ""+ new Random().nextInt(1000);
	}

	/**
	 * 获取Range参数
	 * @param req
	 * @return
	 * @throws IOException
	 */
	public static Range parseRange(HttpServletRequest req) throws IOException {
		String range = req.getHeader("content-range");
		Matcher m = RANGE_PATTERN.matcher(range);
		if (m.find()) {
			range = m.group().replace("bytes ", "");
			String[] rangeSize = range.split("/");
			String[] fromTo = rangeSize[0].split("-");

			long from = Long.parseLong(fromTo[0]);
			long to = Long.parseLong(fromTo[1]);
			long size = Long.parseLong(rangeSize[1]);

			return new Range(from, to, size);
		}
		throw new IOException("获取RANG时出错");
	}
	
	/**
	 * 关流
	 * @param stream
	 */
	public static void close(Closeable stream) {
		try {
			if (stream != null)
				stream.close();
		} catch (IOException e) {
		}
	}
}
