package cn.yesway.service;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
public class ClientCustomSSL2Test {
	private static String DIR = "E:/workspace/Y-Pay-Center/yesway-pay-center-rabbitmq/target/classes/";
			
    private static String CLIENT_KEY_STORE = DIR + "client.p12";
    private static String CLIENT_TRUST_KEY_STORE = DIR + "server.p12";
    private static String CLIENT_KEY_STORE_PASSWORD = "yesway";
    private static String CLIENT_TRUST_KEY_STORE_PASSWORD = "yesway";
    private static String CLIENT_KEY_PASS = "yesway";

    public final static void main(String[] args) throws Exception {

        KeyStore trustStore = KeyStore.getInstance("PKCS12");//KeyStore.getDefaultType()
        FileInputStream instream = new FileInputStream(new File(CLIENT_TRUST_KEY_STORE));
        try {
            trustStore.load(instream, CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
        }catch(Exception e){
        	e.printStackTrace();
        }
        finally {
            instream.close();
        }

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream keyStoreInput = new FileInputStream(new File(CLIENT_KEY_STORE));
        try {
            keyStore.load(keyStoreInput, CLIENT_KEY_STORE_PASSWORD.toCharArray());
        }catch(Exception e){
        	e.printStackTrace();
        } finally {
            keyStoreInput.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                .loadKeyMaterial(keyStore, CLIENT_KEY_PASS.toCharArray())
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
        try {

            HttpPost httpPost = new HttpPost("https://192.168.104.85:9001/bmw-manage-gateway/ordersynchronization/updateStatus");

            System.out.println("executing request" + httpPost.getRequestLine());

            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}