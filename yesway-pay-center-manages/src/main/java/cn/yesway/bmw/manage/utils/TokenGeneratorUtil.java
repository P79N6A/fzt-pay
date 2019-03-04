package cn.yesway.bmw.manage.utils;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * 生成token的工具类
 * @author cc
 *
 */
public class TokenGeneratorUtil {
	
	/**
	 * 生成Token， A(hashcode>0)|B + |name的Hash值| +_+size的值
	 * @param fileName
	 * @param fileSize
	 * @return
	 * @throws IOException 
	 */
	public static String generateToken(String fileName,String fileSize,String parentPath) throws IOException{
		if(StringUtils.isEmpty(fileName) || StringUtils.isEmpty(fileSize) || StringUtils.isEmpty(parentPath)) return "";
		int hashCode = fileName.hashCode();
		String token = (hashCode>0?"A":"B")+Math.abs(hashCode)+"_"+fileSize.trim();
		if(StringUtils.isEmpty(token))return "";
		//按照日期生成目录
		Calendar cal =Calendar.getInstance();
		int yearDir = cal.get(Calendar.YEAR);
		int monthDir =cal.get(Calendar.MONTH)+1;
		int dayDir = cal.get(Calendar.DATE);
		String rootDir=PropUtils.get("stream_file_repository");
		String path = rootDir+"/"+parentPath+"/"+yearDir+
					  "/"+monthDir+"/"+dayDir+"/"+token;
		File tranFile = new File(path);
		if(!tranFile.getParentFile().exists())tranFile.getParentFile().mkdirs();
		if(!tranFile.exists())tranFile.createNewFile();
		return path;
	}
	
	public static void main(String[] args) {
		String rootDir=PropUtils.get("stream_file_repository");
		System.out.println(rootDir);
		try {
			System.out.println(generateToken("eclipse-jee-kepler-SR1-win32.zip", "259096828 ", "img"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
