package cn.yesway.pay.center.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class HttpUtil {
    private static  Logger logger = Logger.getLogger(HttpUtil.class);

    /**
     * 发送报文并接收到报文
     * @param requestUrl
     * @param params
     * @return
     * @author LiangMeng
     */
    public static String post(String requestUrl ,Map<String,String> params){
        URL url;
        HttpURLConnection conn;
        String result = null;
        try {
            /*1.初始化连接*/
            url = new URL(requestUrl);
            logger.info("请求地址：[ " + requestUrl+"]");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(50000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            /*2.构造发送参数*/
            StringBuffer sb = new StringBuffer();
            if(params!=null){
                for (Entry<String, String> e : params.entrySet()) {
                    sb.append(e.getKey());
                    sb.append("=");
                    sb.append(e.getValue());
                    sb.append("&");
                }
                sb = new StringBuffer(sb.substring(0, sb.length() - 1));
            }
            /*3.发送报文*/
            OutputStreamWriter writer;
            writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            logger.info("发送报文：[" + sb+"]");
            writer.write(sb.toString());
            writer.flush();
            writer.close();
            String line;
            StringBuilder builder = new StringBuilder();
            /*4.接收返回报文*/
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            reader.close();
         // logger.info("接收报文: [" + URLDecoder.decode(builder.toString()+"]","UTF-8"));
         // return URLDecoder.decode(builder.toString(),"UTF-8");
            logger.info("接收报文: [" + builder.toString()+"]");
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("返回数据有误:" + e.getMessage(),e);
        }
        return result;
    }
    
    /**
     * 发送并接收xml格式的报文
     * @param requestUrl
     * @param params
     * @return
     */
    public static String postByXml(String requestUrl ,String xmlInfo){
    	URL url;
        HttpURLConnection conn;
        String result = null;
        try {
            /*1.初始化连接*/
            url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Pragma:", "no-cache");  
            conn.setRequestProperty("Cache-Control", "no-cache");        
            conn.setRequestProperty("Content-Type", "text/xml");  
            conn.setConnectTimeout(30000);
  
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());      
            logger.info("\npostByXml发送数据:\n" + xmlInfo);  
            out.write(new String(xmlInfo.getBytes("utf-8")));  
            out.flush();  
            out.close();  
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));  
            StringBuffer xmlParam = new StringBuffer();  
            String line = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {  
            	xmlParam.append(line);
            } 
            logger.info("\npostByXml返回数据:\n" + xmlParam.toString());
            return xmlParam.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("返回数据有误:" + e.getMessage(),e);
            return "";
        }    	
    }
    
    /**
     * 发送xml格式的报文,并将接收到的信息存放在文件中
     * @param requestUrl
     * @param params
     * @return
     * @throws IOException 
     */
    public static List<String> postByXmlAndSave(String requestUrl ,String xmlInfo,String filePath) throws IOException{
    	URL url;
        HttpURLConnection conn;
        //缓冲FileWriter  
        BufferedWriter bufWriter = new BufferedWriter(new FileWriter(filePath));
        List<String> list = new ArrayList<String>();
        try {
            /*1.初始化连接*/
            url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Pragma:", "no-cache");  
            conn.setRequestProperty("Cache-Control", "no-cache");        
            conn.setRequestProperty("Content-Type", "text/xml");  
            conn.setConnectTimeout(30000);
  
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());      
            logger.info("postByXml发送数据=" + xmlInfo);  
            out.write(new String(xmlInfo.getBytes("utf-8")));  
            out.flush();  
            out.close();  
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));  
            StringBuffer xmlParam = new StringBuffer();         
            String line = "";
            
            int i = 0;
            for (line = br.readLine(); line != null; line = br.readLine()) {
            	xmlParam.append(line);
            	bufWriter.write(line);  
            	//newLine()方法写入与操作系统相依的换行字符，依执行环境当时的OS来决定该输出那种换行字符  
            	bufWriter.newLine();  
            	
            	if (i > 0 && line.startsWith("`")){//去掉2行中文描述
            		list.add(line);
            	}
            	i++;
            } 
            if (list.size() > 0){
            	//去掉最后一行汇总信息
            	list.remove(list.size()-1);         	
            }else{
            	logger.error("获取对账报文失败！");
            }
            logger.info("postByXml返回数据=" + xmlParam.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("返回数据有误:" + e.getMessage(),e);
        }finally{
        	bufWriter.flush();
        	bufWriter.close();
        }
        return list;
    }
    
}
