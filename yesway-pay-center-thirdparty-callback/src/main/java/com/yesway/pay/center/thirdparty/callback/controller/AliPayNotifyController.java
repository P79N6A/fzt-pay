package com.yesway.pay.center.thirdparty.callback.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.dubbo.common.utils.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.yesway.pay.order.entity.Configuration;
import cn.yesway.pay.order.entity.OrderPayStatus;
import cn.yesway.pay.order.entity.Orders;
import cn.yesway.pay.order.service.ConfigurationService;
import cn.yesway.pay.order.service.OrderService;
import cn.yesway.pay.order.service.QueueService;

import com.yesway.pay.center.thirdparty.callback.util.Constants;


/**
 * 支付宝回调方法处理
 * @author ghk
 *  2017年2月17日上午11:00:10
 *  AliPayNotifyController
 */
@Controller
@RequestMapping("/alipaynotify")
public class AliPayNotifyController extends BaseController{

	@Autowired
	OrderService orderService;
	
	@Autowired
	QueueService queueService;
	
	@Autowired
	private ConfigurationService configurationService;
	
	private final String CONFIG_KEY = "save_pay_message_to_queue";
	private final String CONFIG_VALUE = "on";
	
	private final Logger logger = Logger.getLogger(this.getClass());
	public static final String TRADE_FINISHED = "TRADE_FINISHED";//交易完成
	public static final String TRADE_SUCCESS = "TRADE_SUCCESS";//支付成功
	public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";//交易创建
	//private static String INPUT_CHARSET = ConfigUtil.getProperty("INPUT_CHARSET");
	

	/**
	 * 支付宝移动app支付后台通知服务
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/alipayNotify_url")
	@ResponseBody
	public void Notify(HttpServletRequest request, HttpServletResponse response) throws IOException{
		long threadId = Thread.currentThread().getId();
        logger.info("[" + threadId + "]" + "接收支付宝APP后台通知，商户订单号：" + request.getParameter("out_trade_no"));
        PrintWriter printWriter = null;
        //String returnCodeToAlipay = "fail";
        String desc = "[" + threadId + "]" + "接收支付宝APP后台通知: ";
        
        try {
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			printWriter = response.getWriter();
			 //1.获取参数
            Map<String, String> mapParam = new HashMap<String, String>();
    		try {
    			mapParam = getRequestParam(request);
    			logger.info(desc + mapParam.toString());
    			
    		}catch (Throwable e1) {
    			desc += e1.getMessage();
    			logger.info(desc);
    			return;
    		}
    		//2.验证签名
    		//...
    		
    		//4.查询订单信息
            String outTradeNo = mapParam.get("out_trade_no");// 商户订单号       
            List<Orders> queryResult = orderService.orderquery(outTradeNo);  
            if(queryResult.size()>0){
            	 //int payAmountLi = 0;
            	 String payAmount = "";         //总金额  
            	 String transStatus = "";       // 交易状态
            	 String transaction_id = "";    //支付订单号
            	 transStatus = mapParam.get("trade_status");// 交易状态
                 payAmount = mapParam.get("total_amount"); //总金额  
                 transaction_id = mapParam.get("trade_no");//支付宝订单号
                 
                 Orders order = queryResult.get(0);
            	 if(order.getPaystatus()==Constants.STATUS.PAYED_SUCCESS){//支付成功
             		logger.info("该订单已经支付成功：商户订单号:"+order.getOutTradeNo()+"支付状态:"+order.getPaystatus());
             		desc = "success";
         			return ;
             	}
            	 //只更新支付状态为1（待支付）的订单
            	 if (order.getPaystatus() != Constants.STATUS.APPLY) {//未支付0
                 	logger.info("订单状态不为0(未支付)，不接收通知，商户订单号：" +order.getOutTradeNo()+"订单支付状态:" +order.getPaystatus());
                 	desc = "success";
                 	return;
                 }
            	
	             double price=Double.parseDouble(payAmount); 
	             if ( TRADE_SUCCESS.equals(transStatus)) {//交易完成，允许退款
	             	//payAmountLi = new BigDecimal(payAmount).multiply(new BigDecimal(1000)).intValue();
	             	 if (order.getTotalAmount() != price) {
	                     logger.info(desc + "通知服务返回的金额不一致：支付平台：" + order.getTotalAmount() + "====支付宝返回"+ price);
	                 }
	                 transStatus = "1";
	             }else if(WAIT_BUYER_PAY.equals(transStatus)){
	             	logger.info(desc + "订单还未成功支付，订单号="+ outTradeNo + ",订单状态trade_status=" + transStatus);
	             	desc = "success";
	             	transStatus = "0";
	             	//return;
	             }else if(TRADE_FINISHED.equals(transStatus)){//交易完成，不允许退款
	            	 logger.info(desc + "订单支付成功支付，不允许退款，订单号="+ outTradeNo + ",订单状态trade_status=" + transStatus);
	            	 if (order.getTotalAmount() != price) {
	                     logger.info(desc + "通知服务返回的金额不一致：支付平台：" + order.getTotalAmount() + "====支付宝返回"+ price);
	                 }
	            	 transStatus = "2";
	             }else{
	             	logger.info(desc + "订单还未成功支付，订单号="+ outTradeNo + ",trade_status=" + transStatus);
	             	return;
	             }
                OrderPayStatus orderPay = new OrderPayStatus();
 				orderPay.setId(UUID.randomUUID().toString());
 				orderPay.setOutTradeNo(outTradeNo);
 				orderPay.setPaystatus(Integer.parseInt(transStatus));
 				orderPay.setCreatetime(new Timestamp(System.currentTimeMillis()));
 				orderPay.setUpdatetime(new Timestamp(System.currentTimeMillis()));
 				orderPay.setStatusdesc(mapParam.toString());
 			
 				int result = orderService.insert(orderPay);
 				if (result > 0) {
 					logger.info("\n 插入支付中心订单状态表成功");
 				} else {
 					logger.error("\n 插入支付中心订单状态表失败");
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
 					sdata.put("payToolType", 1);
 					sdata.put("out_trade_no", outTradeNo);
 					sdata.put("trade_no", transaction_id);
 					//
 					//trade_status 支付宝交易目前所处的状态
 					//TRADE_FINISHED	交易完成,不允许退款，状态为2
 					//TRADE_SUCCESS 	支付成功，状态为1
 					//WAIT_BUYER_PAY 	交易创建，状态为0
 					//TRADE_CLOSED 		交易关闭，状态为0
 					String tradeStatus = mapParam.get("trade_status");
 					if(StringUtils.isNotEmpty(tradeStatus) ){
 						if("TRADE_SUCCESS".equals(tradeStatus)){
 	 						sdata.put("payStatus", "1");
 	 					}else if("TRADE_FINISHED".equals(tradeStatus)){
 	 						sdata.put("payStatus", "2");
 	 					}else if("WAIT_BUYER_PAY".equals(tradeStatus)||"WAIT_BUYER_PAY".equals(tradeStatus)){
 	 						sdata.put("payStatus", "0");
 	 					}
 					}
 					//gmt_payment 	交易付款时间
 					//gmt_close 	交易结束时间
 					sdata.put("paidStartTime", mapParam.get("gmt_payment"));
 					sdata.put("paidEndTime", mapParam.get("gmt_close"));
 					//fund_bill_list支付金额信息(支付成功的各个渠道金额信息，详见支付宝文档中心资金明细信息说明)
 					//fundChannel 	支付渠道
 					//amount 	支付金额
 					JSONArray arr = JSONArray.fromObject(mapParam.get("fund_bill_list"));
 					if(arr.size()>0){
 						JSONObject fundBill = (JSONObject)arr.get(0);
 						sdata.put("paymentChannel",fundBill.get("fundChannel"));
 						sdata.put("amount", fundBill.get("amount"));
 					}
 					//2.1增加回调第三方接口附加数据字段
					sdata.put("attach",StringUtils.isNotEmpty(order.getAttach()) ? order.getAttach() : "");

 					msg.put("messageheader", msgheader);
 					msg.put("sdata", sdata);
 	           	    
 	           	    JSONObject json = JSONObject.fromObject(msg);   
 	           	    logger.info("插入队列的数据params："+json.toString());
 	                boolean flag = queueService.insert(json.toString());
 	            	if(flag == true){
 	            		logger.info("支付宝回调信息写入队列成功");
 	            	}else{
 	            		logger.info("支付宝回调信息写入队列失败");
 	            	}
 				}               	
            }else {
            	logger.info(desc + "订单不存在，不再接收通知。订单号：" + outTradeNo);
            	desc = "success";
            	return;
            }
    		
         desc = "success";
		} catch (Exception e) {
			 logger.error(desc, e);
	         desc += "fail";
		}finally{
			logger.info("[" + threadId + "]" + "返回支付宝：" + desc);
        	printWriter.write(desc);// 支付宝接收不到“success” 就会在24小时内重复调用多次
            printWriter.flush();
            printWriter.close();
		}
	}
	 private Map<String,String> getRequestParam(HttpServletRequest request) throws Exception{	
	    	//获取支付宝POST过来反馈信息
	    	Map<String,String> params = new HashMap<String,String>();
	    	Map requestParams = request.getParameterMap();
	    	for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
	    		String name = (String) iter.next();
	    		String[] values = (String[]) requestParams.get(name);
	    		String valueStr = "";
	    		for (int i = 0; i < values.length; i++) {
	    			valueStr = (i == values.length - 1) ? valueStr + values[i]
	    					: valueStr + values[i] + ",";
	    		}
	    		//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
	    		//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
	    		params.put(name, valueStr);
	    	}
	    	return params;
		}
}
