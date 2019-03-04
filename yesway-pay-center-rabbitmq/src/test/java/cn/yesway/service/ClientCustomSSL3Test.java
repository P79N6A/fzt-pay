package cn.yesway.service;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.*;

/**
 * ssl通信的client
 */
public class ClientCustomSSL3Test {

    private static PoolingHttpClientConnectionManager secureConnectionManager;
    private static HttpClientBuilder secureHttpBulder = null;
    private static RequestConfig requestConfig = null;
    private static int MAXCONNECTION = 10;
    private static int DEFAULTMAXCONNECTION = 5;

    private static String CLIENT_KEY_STORE = "E:/workspace/Y-Pay-Center/yesway-pay-center-rabbitmq/target/classes/client.p12";
    private static String CLIENT_TRUST_KEY_STORE = "E:/workspace/Y-Pay-Center/yesway-pay-center-rabbitmq/target/classes/client.crt";
    private static String CLIENT_KEY_STORE_PASSWORD = "yesway";
    private static String CLIENT_TRUST_KEY_STORE_PASSWORD = "123456";
    private static String CLIENT_KEY_PASS = "123456";

    /**
     * 进行安全通信的主机和端口
     */
    private static String HOST = "192.168.104.85";
    private static int PORT = 9001;

    static {
        //设置http的状态参数
        requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();

        try {
//            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            KeyStore trustStore = KeyStore.getInstance("PKCS12");
            FileInputStream trustStoreInput = new FileInputStream(new File(CLIENT_TRUST_KEY_STORE));
            trustStore.load(trustStoreInput, CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
            KeyStore clientKeyStore = KeyStore.getInstance("PKCS12");
            FileInputStream clientKeyStoreInput = new FileInputStream(new File(CLIENT_KEY_STORE));
            clientKeyStore.load(clientKeyStoreInput, CLIENT_KEY_STORE_PASSWORD.toCharArray());

            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                    .loadKeyMaterial(clientKeyStore, CLIENT_KEY_PASS.toCharArray())
                    .setSecureRandom(new SecureRandom())
                    .useSSL()
                    .build();

            ConnectionSocketFactory plainSocketFactory = new PlainConnectionSocketFactory();
            SSLConnectionSocketFactory sslSocketFactoy = new SSLConnectionSocketFactory(
                    sslContext, new String[]{"SSLv3"}, null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

            Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", plainSocketFactory)
                    .register("https", sslSocketFactoy)
                    .build();

            secureConnectionManager = new PoolingHttpClientConnectionManager(r);
            HttpHost target = new HttpHost(HOST, PORT, "https");
            secureConnectionManager.setMaxTotal(MAXCONNECTION);
            //设置每个Route的连接最大数
            secureConnectionManager.setDefaultMaxPerRoute(DEFAULTMAXCONNECTION);
            //设置指定域的连接最大数
            secureConnectionManager.setMaxPerRoute(new HttpRoute(target), 20);
            secureHttpBulder = HttpClients.custom().setConnectionManager(secureConnectionManager);
        } catch (Exception e) {
        	e.printStackTrace();
            throw new Error("Failed to initialize the server-side SSLContext", e);
        }
    }

    public static CloseableHttpClient getSecureConnection() throws Exception {
        return secureHttpBulder.build();
    }

    public static HttpUriRequest getRequestMethod(Map<String, String> map, String url, String method) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> e : entrySet) {
            String name = e.getKey();
            String value = e.getValue();
            NameValuePair pair = new BasicNameValuePair(name, value);
            params.add(pair);
        }
        HttpUriRequest reqMethod = null;
        if ("post".equals(method)) {
            reqMethod = RequestBuilder.post().setUri(url)
                    .addParameters(params.toArray(new BasicNameValuePair[params.size()]))
                    .setConfig(requestConfig).build();
        } else if ("get".equals(method)) {
            reqMethod = RequestBuilder.get().setUri(url)
                    .addParameters(params.toArray(new BasicNameValuePair[params.size()]))
                    .setConfig(requestConfig).build();
        }
        return reqMethod;
    }

    public static void main(String args[]) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("account", "sdsdsd");
        map.put("password", "98765");

        HttpClient client = getSecureConnection(); //使用ssl通信
        String url = "https://192.168.104.85:9001/bmw-manage-gateway/ordersynchronization/updateStatus";
        HttpUriRequest post = getRequestMethod(map, url, "post");
        HttpResponse response = client.execute(post);

        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String message = EntityUtils.toString(entity, "utf-8");
            System.out.println(message);
        } else {
            System.out.println("请求失败");
        }
    }
}