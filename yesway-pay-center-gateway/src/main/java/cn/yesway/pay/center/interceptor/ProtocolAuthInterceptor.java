package cn.yesway.pay.center.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.yesway.pay.center.exception.DataIntegrityException;
import cn.yesway.pay.center.exception.MerchantIdentityException;
import cn.yesway.pay.center.exception.MessageHeaderException;
import cn.yesway.pay.center.exception.OEMIdentityException;
import cn.yesway.pay.center.exception.RSAPrivateKeyException;
import cn.yesway.pay.center.exception.RequestMethodException;
import cn.yesway.pay.center.exception.RequestParamException;
import cn.yesway.pay.center.exception.RequestTerminalTypeException;
import cn.yesway.pay.center.exception.SymmetricKeyException;
import cn.yesway.pay.center.util.AESCipher;
import cn.yesway.pay.center.util.AESSecurityUtils;
import cn.yesway.pay.center.util.RSAUtils;
import cn.yesway.pay.center.util.ResponseMessageHearder;
import cn.yesway.pay.center.util.SHADigestUtils;
import cn.yesway.pay.center.util.SecurityUtil_AES;
import cn.yesway.pay.center.util.SecurityUtils;
import cn.yesway.pay.center.util.aes.AESOperator;
import cn.yesway.pay.center.vo.MessageHeader;
import cn.yesway.pay.order.entity.Credential;
import cn.yesway.pay.order.entity.Merchant;
import cn.yesway.pay.order.entity.OEM;
import cn.yesway.pay.order.entity.enums.SignTypeEnum;
import cn.yesway.pay.order.service.CredentialService;
import cn.yesway.pay.order.service.MerchantService;
import cn.yesway.pay.order.service.OemService;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;


public class ProtocolAuthInterceptor implements HandlerInterceptor {

    private final Logger logger = Logger.getLogger(this.getClass());
    
    /**
     * 出自九五支付中心或者OEM的秘钥（证书）
     */
    private final String FROM = "YESWAY";

    @Autowired
    private CredentialService credentialService;
    
    @Autowired
	private OemService oemService;
    
    @Autowired
	private MerchantService merchantService;
    
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler)
            throws IOException {  
    	
        //  GET请求打回，只允许POST方式 
        if ("GET".equals(request.getMethod())) {
        	logger.error(">>>请求方式发生错误，只允许用POST方式提交请求", new RequestMethodException());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			ResponseMessageHearder.responseMessageHearderError(response, new RequestMethodException());
            return false;
        }
       
        
        String requestUri = request.getRequestURI();
        logger.info("请求路径requestUri:"+requestUri);
        //
    	//如果是微信支付或者是支付宝支付回调通知的请求，直接跳过，分给相应的的Controller进行处理
    	//
    	if(requestUri.contains("/alipaynotify/alipayNotify_url") ||requestUri.contains("/weixinnotify/weixinNotify_url") ){
    		logger.info("requestUri.contains("+requestUri+")");
    		return true;
    	}
       
        // 获取body内容
        int size = request.getContentLength();
        /**
         * request.getInputStream()方法必须位于微信/支付宝回调请求的下面，避免request请求二次读取失效
         */
        InputStream in = request.getInputStream();
        byte[] reqBodyBytes = this.readBytes(in, size);
        String requestBody = new String(reqBodyBytes, "UTF-8");
        //  日志记录请求信息 
        logger.info("记录日志开始时间线程id="+Thread.currentThread().getId() + "开始时间" + Calendar.getInstance().getTimeInMillis());
        logRequestBody(request, requestBody);
        logger.info("记录日志结束时间线程id="+Thread.currentThread().getId() + "结束时间" + Calendar.getInstance().getTimeInMillis());
        
        if(requestBody.length() < 1){            	
        	ResponseMessageHearder.writeJsonReposne(response,"OK!");
        	logger.debug(">>>requestBody.length() < 1");
			return false;
		}
        JSONObject messageJSON = JSONObject.parseObject(requestBody);  
        
        //协议鉴权
        // post请求需携带json格式的MessageHeader 
        JSONObject messageHeaderJSON = messageJSON.getJSONObject("messageheader");
        if(messageHeaderJSON==null){
        	logger.error(">>>消息头数据为空", new MessageHeaderException());
            ResponseMessageHearder.responseMessageHearderError(response, new MessageHeaderException());
            return false;
        }       
        
        MessageHeader messageheader = (MessageHeader) JSONObject.toJavaObject(messageHeaderJSON, MessageHeader.class);
              
        try {   
			// 验证协议头
			// oemid（OEM）,mchid（商户）不能为空
			if (messageheader != null && StringUtils.isBlank(messageheader.getOemid())) {
				throw new OEMIdentityException();
			}
			/*if(messageheader != null && StringUtils.isBlank(messageheader.getMchid())){
				throw new MerchantIdentityException();
			}*/
		} catch (Exception e) {
            logger.error(">>>拦截器过滤请求时发生未知错误", e);
            ResponseMessageHearder.responseMessageHearderError(response, e);
            return false;
        }
        //鉴权OEMID和商户ID从数据库查找
        logger.info("鉴权OEMID和商户ID开始时间线程id="+Thread.currentThread().getId() + "开始时间" + Calendar.getInstance().getTimeInMillis());
		OEM oem = oemService.getById(messageheader.getOemid());
		if(oem == null){
			ResponseMessageHearder.responseMessageHearderError(response, new OEMIdentityException());
            return false;
		}
		 logger.info("鉴权OEMID和商户ID结束时间线程id="+Thread.currentThread().getId() + "开始时间" + Calendar.getInstance().getTimeInMillis());
		/*Merchant mch = merchantService.getById(messageheader.getMchid());
		if(mch == null){
			ResponseMessageHearder.responseMessageHearderError(response, new MerchantIdentityException());
            return false;
		}*/
		
		request.setAttribute("messageheader", messageheader);	
//		logger.info("request.setAttribute(\"messageheader\", "+messageheader+")");
    	
    	//如果是请求不需要加密请求，直接取出请求参数
    	//
    	if(requestUri.contains("/pay/orderquery") || requestUri.contains("/pay/closeorder") 
    			|| requestUri.contains("/pay/refundquery") || requestUri.contains("/pay/weixin/unifiedorder") || requestUri.contains("/pay/alipayrefundquery") ){
    		request.setAttribute("sdata", messageJSON.getString("sdata"));			
    		return true;
    	}
        //
    	//以下是处理有加密请求的功能
    	//
        
        String hOemId = messageheader.getOemid();
		String hMchId = messageheader.getMchid();
		
		
		//从数据库证书表读取AES算法密钥
		Credential aesCredential = credentialService.getCredential(FROM,hOemId, SignTypeEnum.AES.name());	
		//从数据库证书表读取
		Credential rsaCredential = credentialService.getCredential(FROM,hOemId, SignTypeEnum.RSA_PRIVATE.name());	
		//对支付单号处理(下单和退款)
		if(requestUri.contains("/pay/unifiedorder")){
			String payOrderNoSign = messageJSON.getString("payOrderNoSign");
			String payOrderNo = null;
			try {
				payOrderNo = RSAUtils.decryptByPrivateKey(payOrderNoSign);
				logger.info("解密数据payOrderNo是 \n"+payOrderNo);
			} catch (Exception e) {
				e.printStackTrace();
			}
    		request.setAttribute("payOrderNo", payOrderNo);			
    	}
		if(requestUri.contains("/pay/refund")){
			String payOrderNoSign = messageJSON.getString("payOrderNoSign");
			String payRefundNoSign = messageJSON.getString("payRefundNoSign");
			String payOrderNo = null;
			String payRefundNo = null;
			try {
				payOrderNo = RSAUtils.decryptByPrivateKey(payOrderNoSign);
				payRefundNo = RSAUtils.decryptByPrivateKey(payRefundNoSign);
				logger.info("解密数据payOrderNo是 \n"+payOrderNo);
			} catch (Exception e) {
				e.printStackTrace();
			}
    		request.setAttribute("payOrderNo", payOrderNo);			
    		request.setAttribute("payRefundNo", payRefundNo);	
		}
        //解密数据明文
        String sdata = messageJSON.getString("sdata");
        if(StringUtils.isEmpty(sdata)){
        	logger.error(">>>解析数据明文出错",new RequestParamException());
			ResponseMessageHearder.responseMessageHearderError(response, new RequestParamException());
            return false;
	    }
        if(aesCredential==null){
        	logger.error(">>>使用AES秘钥解析签名数据出错",new SymmetricKeyException());
			ResponseMessageHearder.responseMessageHearderError(response, new SymmetricKeyException());
            return false;
        }
        String aesSecretKey = aesCredential.getCredential();
        try {
        	//解密
        	sdata =SecurityUtil_AES.decrypt(sdata, aesSecretKey);
        	logger.info("解密数据是 \n"+sdata);
        	
		} catch (Exception e) {
			logger.error(">>>使用AES秘钥解析签名数据出错",new SymmetricKeyException());
			ResponseMessageHearder.responseMessageHearderError(response, new SymmetricKeyException());
            return false;
		}
		//解析签名数据
        if(rsaCredential==null){
        	logger.error(">>>使用RSA私钥解析签名数据出错",new RSAPrivateKeyException());
			ResponseMessageHearder.responseMessageHearderError(response, new RSAPrivateKeyException());
            return false;   	
        }
        String rsaPrivateKey = rsaCredential.getCredential();
        String sign = messageJSON.getString("sign");
       /* PrivateRSA privateRSA = PrivateRSA.getInstance();*/
        
        try {
			sign =  RSAUtils.decryptByPrivateKey(sign,rsaPrivateKey);
		} catch (Exception e) {
			logger.error(">>>使用RSA私钥解析签名数据出错",new RSAPrivateKeyException());
			ResponseMessageHearder.responseMessageHearderError(response, new RSAPrivateKeyException());
            return false;
		}
        JSONObject signJSON = JSONObject.parseObject(sign);
        String oemid = signJSON.getString("oemid");
        String mchid = signJSON.getString("mchid");
        String sdigest = signJSON.getString("sdigest");
        
		//将明文数据（sData）进行哈希算法（SHA1）得到数字摘要（RDigest）
		String rDigest=null;
		try {
			@SuppressWarnings("unchecked")
			Map<String, String> params = (Map<String, String>)JSONObject.parseObject(sdata, Map.class);
			String data = SecurityUtils.createLinkString(params);
			System.out.println();
			System.out.println(data);
			System.out.println();
			System.out.println("sdigest===="+sdigest);
			rDigest = SHADigestUtils.encryptSHA(data);
//			rDigest = EncrypSHA.eccryptSHA1(data);
			System.out.println("rDigest===="+rDigest);
		} catch (Exception e) {
			logger.error(">>>使用SHA1加密明文数据（sData）出错 ",new SymmetricKeyException());
			ResponseMessageHearder.responseMessageHearderError(response, new SymmetricKeyException());
            return false;
		}
		//对比数字摘要（SDigest）和数字摘要（RDigest），验证数据完整性
		if(!sdigest.equals(rDigest)){
			logger.error(">>>对比数字摘要（SDigest）和数字摘要（RDigest）发生错误", new DataIntegrityException());
			ResponseMessageHearder.responseMessageHearderError(response, new DataIntegrityException());
			return false;
		}
		//将OEM编号和商户编号，与控制协议中的这两个编号对比，验证发送方身份是否伪造		
		if(!(oemid.equals(hOemId) && mchid.equals(hMchId))){
			logger.error(">>>对比OEM ID（oemid）和商户ID（mchid）时发生错误", new MessageHeaderException());
			ResponseMessageHearder.responseMessageHearderError(response, new MessageHeaderException());
			return false;
		}
                
        request.setAttribute("sdata", sdata);
        request.setAttribute("oemid", oemid);
        request.setAttribute("mchid", mchid);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,HttpServletResponse response,Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response,Object handler,Exception ex)
            throws Exception {

    }

    private byte[] readBytes(InputStream in,int contentLen) {
        if (contentLen > 0) {
            int readLen = 0;
            int readLengthThisTime = 0;
            byte[] message = new byte[contentLen];

            try {
                while (readLen != contentLen) {
                    readLengthThisTime = in.read(message, readLen, contentLen - readLen);
                    if (readLengthThisTime == -1) {
                        break;
                    }
                    readLen += readLengthThisTime;
                }
                return message;
            } catch (IOException e) {
            }
        }
        return new byte[] {};
    }

    private String getHeader(HttpServletRequest request) {
        StringBuffer buf = new StringBuffer();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            buf.append("[" + key + ":" + value + "]").append(" ");
        }
        return buf.toString();
    }
    private void logRequestBody(HttpServletRequest request, String requestBody) {
		StringBuilder logMessage = new StringBuilder();
        logMessage.append(">>>请求信息:\n");
        logMessage.append("remoteAddr:[" + request.getRemoteAddr() + "]").append("\n");
        logMessage.append("method:[" + request.getMethod() + "]").append("\n");
        logMessage.append("requestURI:[" + request.getRequestURI() + "]").append("\n");
        logMessage.append("charactorEncoding:[" + request.getCharacterEncoding() + "]").append("\n");
        logMessage.append("queryString:[" + request.getQueryString() + "]").append("\n");
        logMessage.append("header:[" + getHeader(request) + "]").append("\n");
        logMessage.append("requestBody:" + requestBody).append("\n");
        logger.info(logMessage.toString());
	}
    
}
