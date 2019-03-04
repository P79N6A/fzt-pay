
package com.yesway.pay.center.thirdparty.callback.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yesway.pay.center.thirdparty.callback.util.Constants;
import com.yesway.pay.center.thirdparty.callback.util.XMLUtil;
import com.yesway.pay.center.thirdparty.callback.util.sign.MD5;

import cn.yesway.pay.order.entity.Configuration;
import cn.yesway.pay.order.entity.OrderPayStatus;
import cn.yesway.pay.order.entity.Orders;
import cn.yesway.pay.order.service.ConfigurationService;
import cn.yesway.pay.order.service.OrderService;
import cn.yesway.pay.order.service.QueueService;


/** 微信回调方法
 * @author ghk
 *  2017年2月7日下午4:54:24
 *  PayController
 */
@Controller
@RequestMapping("/weixinnotify")//yesway-pay-center-gateway/weixinnotify/weixinNotify_url
public class WeiXinNotifyController extends BaseController{

	private final Logger logger = Logger.getLogger(this.getClass());
	@Value("#{globalConfig.WEIXIN_MCH_API_SECRET}")
	private String WEIXIN_MCH_API_SECRET ;//= ConfigUtil.getProperty("WEIXIN_MCH_API_SECRET");
	@Value("#{globalConfig.INPUT_CHARSET}")
	private String ENCODE_TYPE ;//= ConfigUtil.getProperty("INPUT_CHARSET");
	private String CODE_SUCCESS = "SUCCESS";
	private String RETURN_MSG = "OK";
	private String CODE_FAIL = "FAIL";
	
	@Autowired
	OrderService orderService;
	@Autowired
	QueueService queueService;
	
	@Autowired
	private ConfigurationService configurationService;
	
	private final String CONFIG_KEY = "save_pay_message_to_queue";
	private final String CONFIG_VALUE = "on";
	/**
	 * 接收微信支付通知
	 * @param request
	 * @param response
	 */
	@RequestMapping(value ="/weixinNotify_url")
	 public void weixinNotify(HttpServletRequest request, HttpServletResponse response) throws Exception{      
        logger.info("开始接收微信支付通知");
        String returnCodeToWeixin = CODE_SUCCESS;
        String returnMsgToWeixin = RETURN_MSG;
        String desc = "接收微信通知: ";
        
      try{
        //1.获取参数
        Map<String, String> mapParam = new HashMap<String, String>();
        try{
        	mapParam = getRequestParam(request);
       }catch (Throwable e1) {
			returnCodeToWeixin = CODE_FAIL;
			returnMsgToWeixin = desc + e1.getMessage();
			logger.info(returnMsgToWeixin);
		}               
//        String weixin_key = WEIXIN_MCH_API_SECRET;
        //2.验证签名
        /*if(!verifiySign(mapParam,weixin_key)){
        	returnCodeToWeixin = CODE_FAIL;
			returnMsgToWeixin = desc + "签名验证失败！";
			logger.info(returnMsgToWeixin);
        }*/
        //3.判断通知返回的return_code
        String returnCode = mapParam.get("return_code");//SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
        if ("FAIL".equals(returnCode)){
        	logger.info("微信回调信息返回错误:return_code"+returnCode);
        }
        //4.查询订单信息
        String outTradeNo = mapParam.get("out_trade_no");// 商户订单号
        String transStatus = mapParam.get("result_code");// 交易状态
        String payAmount = mapParam.get("total_fee"); //总金额  
        String errCode = mapParam.get("err_code"); //错误代码
        String tradeNo = mapParam.get("transaction_id");//微信支付订单号
    	//String errCodeDes = mapParam.get("err_code_des"); //错误代码描述
    	if(CODE_SUCCESS.equals(transStatus)){
    		transStatus="1";
    		logger.info("微信回调接口成功,交易状态:result_code"+transStatus);
    	}else{
    		logger.info("微信回调接口出现错误");
    		transStatus="0";
    		returnCodeToWeixin = CODE_FAIL;
			returnMsgToWeixin = "微信回调接口失败,交易状态：result_code"+transStatus;
    	}
    	
        //4.1、调用查询订单接口
        List<Orders> queryResult= orderService.orderquery(outTradeNo);
        double payAmountLi = 0;
        if(queryResult.size()>0 ){
        	Orders order = queryResult.get(0);
        	if(order.getPaystatus()==Constants.STATUS.PAYED_SUCCESS){//支付成功
        		logger.info("该订单已经支付成功："+order.getOutTradeNo()+"支付状态:"+order.getPaystatus());
        		String Desc = "已经支付成功";
        		returnCodeToWeixin = CODE_SUCCESS;
    			returnMsgToWeixin = Desc;
    			return ;
        	}
        	 //只更新支付状态为1（待支付）的订单
            if (order.getPaystatus() != Constants.STATUS.APPLY) {
            	logger.info("订单状态不为0(未支付)，不接收通知，商户订单号：" +order.getOutTradeNo()+"订单支付状态:"+order.getPaystatus());
                return ;
            }
            payAmountLi=(double)(Math.round(Float.parseFloat(payAmount))/100.0);//这样为保持2位小数
            if (order.getTotalAmount() != payAmountLi) {
                logger.info("通知服务返回的金额不一致：支付平台：" + order.getTotalAmount() + "====微信返回" + payAmountLi);
            }
           
            OrderPayStatus orderPay = new OrderPayStatus();
			orderPay.setId(UUID.randomUUID().toString());
			orderPay.setOutTradeNo(outTradeNo);
			orderPay.setPaystatus(Integer.parseInt(transStatus));
			orderPay.setUpdatetime(new Timestamp(System.currentTimeMillis()));
			orderPay.setStatusdesc(mapParam.toString());
			int result = orderService.insert(orderPay);
			if (result > 0) {
					logger.info("插入支付中心订单状态表成功");
			} else {
					logger.error("插入支付中心订单状态表失败");
			}
            //4、把数据写入队列
			//根据配置判断是否写入队列
			Configuration config = configurationService.get(order.getOemid(), CONFIG_KEY);
			if(config==null){
				return;
			}
			String configValue = config.getValue();
			if(configValue!=null && CONFIG_VALUE.equals(configValue)){
				JSONObject msgheader = new JSONObject();
				JSONObject sdata = new JSONObject();
				Map<String,Object> msg = new HashMap<String,Object>();
				
				msgheader.put("oemid", order.getOemid());
				msgheader.put("mchid", order.getMchid());
				msgheader.put("resultcode", 0);
				msgheader.put("transactionid", DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
				msgheader.put("version", "1.0");
				msgheader.put("createtime", DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
				sdata.put("orderid", order.getOrderid());
				sdata.put("out_trade_no", outTradeNo);
				sdata.put("trade_no", tradeNo);			
				sdata.put("payToolType", 2);
				sdata.put("payStatus", transStatus);
				sdata.put("paidStartTime", DateFormatUtils.format(order.getTimeStart(), "yyyyMMddHHmmss"));
				//time_end 支付完成时间 
				sdata.put("paidEndTime", mapParam.get("time_end"));
				//微信接口没有返回支付渠道的参数（支付宝有返回）
				sdata.put("paymentChannel","");
				//订单现金支付金额 cash_fee
				//将微信的交易金额单位转由分为元
				String cashFee = mapParam.get("cash_fee");
				int cashFeeInt = Integer.parseInt(cashFee);
				double cashFeeD = cashFeeInt/100D;
				sdata.put("amount", String.valueOf(cashFeeD));
				//2.1增加回调第三方接口附加数据字段
				sdata.put("attach",!StringUtils.isEmpty(order.getAttach()) ? order.getAttach() : "");

				msg.put("messageheader", msgheader);
				msg.put("sdata", sdata);
           	    
           	    JSONObject json = JSONObject.fromObject(msg);				
				
	      	    logger.info("插入队列的数据params："+json.toString());
	            boolean flag = queueService.insert(json.toString());
	        	if(flag == true){
	        		logger.info("写入队列成功");
	        	}else{
	        		logger.info("写入队列失败");
	        	}
			}
          	
        }else if(queryResult.size()==0 ){
        	logger.info("订单不存在，不再接收通知。订单号：" + outTradeNo);
			return ;
        }else {
        	String errDesc = "失败";
            logger.info(errDesc);
            returnCodeToWeixin = CODE_FAIL;
			returnMsgToWeixin = desc + errDesc;
        }
        //6.将支付结果上发appServer
      }catch (Exception e) {
      	e.printStackTrace();
      	returnCodeToWeixin = CODE_FAIL;
		returnMsgToWeixin = desc + e.getMessage();
		logger.info(returnMsgToWeixin);   
      }finally{
      	//7.回复微信
      	responseToWeixin(response,returnCodeToWeixin,returnMsgToWeixin);
      }
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
            	
            }finally{
            	try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }
        return new byte[] {};
    }
	
	private Map<String, String> getRequestParam(HttpServletRequest request)throws Exception {
		 // 获取body内容
		BufferedReader reader = request.getReader();
		String line = "";
		String xmlString = null;
		StringBuffer inputString = new StringBuffer();

		while ((line = reader.readLine()) != null) {
			inputString.append(line);
		}
		xmlString = inputString.toString();
		reader.close();

		logger.info("xmlParam信息456:" + xmlString);

		// 将xml参数解析为map
		Map<String, String> mapParam = StringUtils.isNotBlank(xmlString)?XMLUtil.readStringXmlOut(xmlString):new HashMap<String, String>();

		return mapParam;
	}
	private boolean verifiySign(Map<String, String> mapParam,String weixin_key){
		boolean flag = true;
		String signAfter=sign(mapParam,weixin_key);
		
        if(!signAfter.equals(mapParam.get("sign"))){
        	flag = false;
        }
		return flag;
	}
	private String sign(Map<String, String> mapParam,String weixin_key){
		//key排序
		String[] keyArr = new String[mapParam.size()];
        int i = 0;
        for (Iterator iter = mapParam.keySet().iterator(); iter.hasNext();) {
        	String name = (String) iter.next();
        	keyArr[i]=name;
        	i++;
        }
        Arrays.sort(keyArr); 
        
        //拼接加密串
        StringBuffer signBefore = new StringBuffer();
        for(int j = 0;j<keyArr.length;j++){
        	if(StringUtils.isNotBlank(mapParam.get(keyArr[j])) && !"sign".equals(keyArr[j])){
        		if(j>0){
        			signBefore.append("&");
        		}        		
        		signBefore.append(keyArr[j]).append("=").append(mapParam.get(keyArr[j]));
        	}
        }
        
        //验证签名
		signBefore.append("&key=").append(weixin_key);               
        logger.info("签名前="+signBefore.toString());
        String signAfter=MD5.sign(signBefore.toString(), "", ENCODE_TYPE).toUpperCase(); 
        logger.info("签名后="+signAfter);
        return signAfter;
	}
	private void responseToWeixin(HttpServletResponse response,String returnCode,String returnMsg) throws Exception{
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter writer = response.getWriter();
        StringBuffer sb = new StringBuffer("<xml>");
        sb.append("<return_code><![CDATA[").append(returnCode).append("]]></return_code>");
        sb.append("<return_msg><![CDATA[").append(returnMsg).append("]]></return_msg>");
        sb.append("</xml>");
        writer.print(sb.toString());
		logger.error(sb.toString());
        writer.flush();
        writer.close();
	}
}
