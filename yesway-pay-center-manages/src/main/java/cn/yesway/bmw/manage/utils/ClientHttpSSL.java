package cn.yesway.bmw.manage.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import net.sf.json.JSONObject;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
public class ClientHttpSSL {		
	private Logger logger = LoggerFactory.getLogger(ClientHttpSSL.class);
	
	
    private String clientKeyStore ;
    private String clientTrustKeyStore ;
    private String clientKeyStorePassword ;
    private String clientTrustKeyStorePassword ;
    private String caKeyPassword;
    public ClientHttpSSL(String clientKeyStore,
    		String clientTrustKeyStore,
    		String clientKeyStorePassword,
    		String clientTrustKeyStorePassword,
    		String caKeyPassword){
    	this.clientKeyStore = clientKeyStore;
    	this.clientTrustKeyStore = clientTrustKeyStore;
    	this.clientKeyStorePassword = clientKeyStorePassword;
    	this.clientTrustKeyStorePassword = clientTrustKeyStorePassword;
    	this.caKeyPassword = caKeyPassword;
    }
    public String doSyncBySSL(String url,String data ) throws Exception{
    	CloseableHttpClient httpclient = getHttpClient();
    	try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(data, "UTF-8"));
            httpPost.setHeader("ocp-apim-subscription-key","4be77952d6fe4f25a5e398fd84c77965");
            httpPost.setHeader("description", "");
            logger.info("executing request" + httpPost.getRequestLine());

            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity == null) {
                	return null;
                }
                
                logger.info(response.getStatusLine().getReasonPhrase() +" "+  response.getStatusLine().getStatusCode());
                logger.info("Response content length: " + entity.getContentLength());
                String jsonStr = EntityUtils.toString(entity, "UTF-8");  
                EntityUtils.consume(entity);
                return jsonStr; 
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
   
	private CloseableHttpClient getHttpClient()
			throws KeyStoreException, FileNotFoundException, IOException,
			NoSuchAlgorithmException, KeyManagementException,
			UnrecoverableKeyException {
		/** 
         * 注意PKCS12证书 是从宝马网关获得
         */
        String clientKeyStoreFilePath=ClientHttpSSL.class.getClassLoader().getResource(clientKeyStore).getFile();  
        String clientTrustKeyStoreFilePath=ClientHttpSSL.class.getClassLoader().getResource(clientTrustKeyStore).getFile();  
        logger.info("clientKeyStore p12证书位置>>>"+clientKeyStoreFilePath);
        logger.info("clientTrustKeyStore p12证书位置>>>"+clientTrustKeyStoreFilePath);
		
		
		KeyStore trustStore = KeyStore.getInstance("PKCS12");//KeyStore.getDefaultType()
        FileInputStream instream = new FileInputStream(new File(clientTrustKeyStoreFilePath));
        try {
            trustStore.load(instream, clientTrustKeyStorePassword.toCharArray());
        }catch(Exception e){
        	e.printStackTrace();
        }
        finally {
            instream.close();
        }

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream keyStoreInput = new FileInputStream(new File(clientKeyStoreFilePath));
        try {
            keyStore.load(keyStoreInput, clientKeyStorePassword.toCharArray());
        }catch(Exception e){
        	e.printStackTrace();
        } finally {
            keyStoreInput.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                .loadKeyMaterial(keyStore, caKeyPassword.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1.2"},
                null,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
		return httpclient;
	}
	public final static void main(String[] args) throws Exception {
		String SYNC_TO_BMW_URL = "https://111.207.49.71:9001/bmw-manage-gateway/ordersynchronization/updateStatus";
		String DIR = "cerd/";
		String clientKeyStore = DIR + "client.p12";
	    String clientTrustKeyStore = DIR + "server.p12";
	    String clientKeyStorePassword = "yesway";
	    String clientTrustKeyStorePassword = "yesway";
	    String caKeyPassword = "yesway";
		try {
			String oemid = "1000";
			String mchid = "100001";
			String orderId = "10001000010020170517114202899484";
			String outTradeNo = "201705171138024";
			String tradeNo = (DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))+ RandomUtils.nextInt(10);
			
			JSONObject msgheader = new JSONObject();
			JSONObject sdata = new JSONObject();
			Map<String,Object> msg = new HashMap<String,Object>();
			
			msgheader.put("oemid", oemid);
			msgheader.put("mchid", mchid);
			msgheader.put("resultcode", 0);
			msgheader.put("transactionid", tradeNo);
			msgheader.put("version", "1.0");
			msgheader.put("createtime", DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
			sdata.put("orderid", orderId);
			sdata.put("out_trade_no", outTradeNo);
			sdata.put("trade_no", tradeNo);
			sdata.put("payStatus", "1");
			
			msg.put("messageheader", msgheader);
			msg.put("sdata", sdata);			
			
			String url="";
			ClientHttpSSL sslHttp = new ClientHttpSSL(clientKeyStore,clientTrustKeyStore,clientKeyStorePassword,clientTrustKeyStorePassword,caKeyPassword);
			String dataJson = sslHttp.doSyncBySSL(SYNC_TO_BMW_URL, JSON.toJSONString(msg)).toString();
			
			String urls="https://bmwuserportalint.chinacloudsites.cn/api/gateway/partnercenter/api/partner/SearchPartner?partnerNo=&partnerName=&partnerRole=3&pageSize=5&pageIndex=0";
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("ocp-apim-subscription-key","4be77952d6fe4f25a5e398fd84c77965");
			//String resp = HttpUtils.doGet(urls, null, headers, "UTF-8");
			System.out.println(">>>"+dataJson);
		} catch (Exception e) {
			System.err.println(">>>使用SSL访问网关出错");
			e.printStackTrace();
		}
      
  }
}