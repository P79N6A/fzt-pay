package cn.yesway.bmw.manage.utils;

import cn.yesway.bmw.manage.common.Constans;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.FileItem;
import com.alipay.api.request.AntMerchantExpandIndirectImageUploadRequest;
import com.alipay.api.request.AntMerchantExpandIndirectZftCreateRequest;
import com.alipay.api.request.AntMerchantExpandIndirectZftModifyRequest;
import com.alipay.api.response.AntMerchantExpandIndirectImageUploadResponse;
import com.alipay.api.response.AntMerchantExpandIndirectZftCreateResponse;
import com.alipay.api.response.AntMerchantExpandIndirectZftModifyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 支付宝客户端工具
 */
public class AlipayClientUtil {

    private final static Logger logger = LoggerFactory.getLogger(AlipayClientUtil.class);

    /**
     * 初始化支付宝客户端
     * @param appId
     * @param privateKey
     * @param alipayPublicKey
     * @return
     */
    public static AlipayClient getAlipayClient(String appId, String privateKey, String alipayPublicKey) {
        return new DefaultAlipayClient(Constans.SERVER_URL,appId,privateKey,Constans.FORMAT,Constans.CHARSET,alipayPublicKey,Constans.SIGN_TYPE);
    }

    /**
     * 直付通二级商户创建
     * ant.merchant.expand.indirect.zft.create
     * @param bizContent
     * @return
     */
    public static String alizftcreate(String bizContent,String appId,String privateKey,String publicKey) throws Exception{
        AlipayClient alipayClient = AlipayClientUtil.getAlipayClient(appId,privateKey,publicKey);
        AntMerchantExpandIndirectZftCreateRequest request = new AntMerchantExpandIndirectZftCreateRequest();
        request.setBizContent(bizContent);
        AntMerchantExpandIndirectZftCreateResponse response = alipayClient.execute(request);
        logger.info("【直付通二级商户创建接口response="+JSON.toJSONString(response)+"】");
        String orderId = "";
        if(response.isSuccess()){
            orderId = response.getOrderId();
        }
        return orderId;
    }

    /**
     * 直付通二级商户修改
     * aant.merchant.expand.indirect.zft.modify
     * @param bizContent
     * @return
     */
    public static String alizftmodify(String bizContent) throws Exception{
        AlipayClient alipayClient = AlipayClientUtil.getAlipayClient(Constans.APP_ID,Constans.PRIVATE_KEY,Constans.PUBLIC_KEY);
        AntMerchantExpandIndirectZftModifyRequest request = new AntMerchantExpandIndirectZftModifyRequest();
        request.setBizContent(bizContent);
        AntMerchantExpandIndirectZftModifyResponse response = alipayClient.execute(request);
        logger.info("【直付通二级商户修改接口response="+JSON.toJSONString(response)+"】");
        String orderId = "";
        if(response.isSuccess()){
            orderId = response.getOrderId();
        }
        return orderId;
    }

    /**
     * 图片上传
     * ant.merchant.expand.indirect.image.upload
     * @param imgUrl
     * @return
     */
    public static String alizftupload(String imgUrl) throws Exception{
        AlipayClient alipayClient = AlipayClientUtil.getAlipayClient(Constans.APP_ID,Constans.PRIVATE_KEY,Constans.PUBLIC_KEY);
        AntMerchantExpandIndirectImageUploadRequest request = new AntMerchantExpandIndirectImageUploadRequest();
        request.setImageType("jpg");
        FileItem ImageContent = new FileItem(imgUrl);
        request.setImageContent(ImageContent);
        AntMerchantExpandIndirectImageUploadResponse response = alipayClient.execute(request);
        logger.info("【直付通二级商户图片上传接口response="+JSON.toJSONString(response)+"】");
        String imageId = "";
        if(response.isSuccess()){
            imageId = response.getImageId();
        }
        return imageId;
    }

    /**
     * 处理直付通回调参数
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String,String> getRequestParam(HttpServletRequest request) throws Exception{
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
            params.put(name, valueStr);
        }
        return params;
    }

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }

    /**
     * 获取将参数按字典序拼接的字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     *            注：参数值为空的参数不进行排序
     * @return 拼接后字符串
     */
    public static String linkString(Map<String, Object> params,String symbol) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = (Object)params.get(key);
            if(value !=null && !"".equals(value)){
                stringBuilder = stringBuilder.append(symbol).append(key + "=" + value);
            }
        }
        return (stringBuilder.toString()).length()>0?(stringBuilder.toString()).substring(1):stringBuilder.toString();
    }

}
