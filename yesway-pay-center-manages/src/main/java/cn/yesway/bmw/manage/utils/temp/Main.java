package cn.yesway.bmw.manage.utils.temp;

import cn.yesway.bmw.manage.common.Constans;
import cn.yesway.bmw.manage.dto.BizContent;
import cn.yesway.bmw.manage.utils.AlipayClientUtil;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AntMerchantExpandIndirectZftCreateRequest;
import com.alipay.api.response.AntMerchantExpandIndirectZftCreateResponse;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import java.util.Map;

public class Main {
    private final static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",Constans.APP_ID,Constans.PRIVATE_KEY,"json","utf-8",Constans.PUBLIC_KEY,"RSA2");
        AntMerchantExpandIndirectZftCreateRequest request = new AntMerchantExpandIndirectZftCreateRequest();
        BizContent bizContent = new BizContent();
        bizContent.setExternal_id("123123123");
        Map<String,Object> bizContentMap = AlipayClientUtil.objectToMap(bizContent);
        logger.info("bizContentMap="+JSON.toJSONString(bizContentMap));
        bizContentMap.put("sign_type",Constans.SIGN_TYPE);
        String linkStr = AlipayClientUtil.linkString(bizContentMap, "&");
        logger.info("linkStr="+linkStr);
        String sign = AlipaySignature.rsa256Sign(linkStr,Constans.PRIVATE_KEY , Constans.CHARSET);
        bizContentMap.put("sign",sign);
        logger.info("bizContentMap="+JSON.toJSONString(bizContentMap));
        request.setBizContent(JSON.toJSONString(bizContentMap));
        AntMerchantExpandIndirectZftCreateResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }
}
