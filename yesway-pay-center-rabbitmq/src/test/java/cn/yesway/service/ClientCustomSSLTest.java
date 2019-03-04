package cn.yesway.service;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
  
//import codeGenerate.util.ConfProperties;  
/**
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
public class ClientCustomSSLTest { 
	private static Logger logger = LoggerFactory.getLogger(ClientCustomSSLTest.class);
//	private static String PASSWORD = "yesway";
//	private static String p12_file = "client.p12";
    
	public static String doSyncBySSL(String url,String data,String certP12File,String certPassword) throws Exception {  
        /** 
         * 注意PKCS12证书 是从宝马网关获得
         */  
         //获取文件的位置  
        String filePath=ClientCustomSSLTest.class.getClassLoader().getResource(certP12File).getFile();  
        logger.info("p12证书位置>>>"+filePath);
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");  
        FileInputStream instream = new FileInputStream(new File(filePath));//P12文件目录  
        try {  
            keyStore.load(instream, certPassword.toCharArray());//这里写密码..默认是你的MCHID  
        } finally {  
            instream.close();  
        }  
  
        // Trust own CA and all self-signed certs  
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, certPassword.toCharArray()).build();  
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(  
                sslcontext,  
                new String[] { "TLSv1" },  
                null,  
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);  
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();  
        try {  
            HttpPost httpost = new HttpPost(url); // 设置响应头信息  
            httpost.addHeader("Connection", "keep-alive");  
            httpost.addHeader("Accept", "*/*");  
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");  
            httpost.addHeader("Host", "api.mch.weixin.qq.com");  
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");  
            httpost.addHeader("Cache-Control", "max-age=0");  
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");  
            httpost.setEntity(new StringEntity(data, "UTF-8"));  
            CloseableHttpResponse response = httpclient.execute(httpost);  
            try {  
                HttpEntity entity = response.getEntity(); 
                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");  
                EntityUtils.consume(entity);  
                return jsonStr;  
            } finally {  
                response.close();  
            }  
        } finally {  
            httpclient.close();  
        }  
    }  
  
}  
