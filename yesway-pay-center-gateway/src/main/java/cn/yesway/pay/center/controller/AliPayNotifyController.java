package cn.yesway.pay.center.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.yesway.pay.center.service.alipay.AlipayNotifyMOBILEPAY;
import cn.yesway.pay.center.util.ConfigUtil;
import cn.yesway.pay.center.util.Constants;
import cn.yesway.pay.order.entity.OrderPayStatus;
import cn.yesway.pay.order.entity.Orders;
import cn.yesway.pay.order.service.OrderService;
import cn.yesway.pay.order.service.QueueService;


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
    		/*if(!AlipayNotifyMOBILEPAY.verify(mapParam)){//验证失败
    			desc += "签名验证失败";
    			logger.error(desc);
    			return;
    		}else{
    			logger.info(desc + "签名验证成功！");
    		}*/
    		
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
                 if (TRADE_FINISHED.equals(transStatus)|| TRADE_SUCCESS.equals(transStatus)) {
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
                 }else{
                 	logger.info(desc + "订单还未成功支付，订单号="+ outTradeNo + ",trade_status=" + transStatus);
                 	return;
                 }
                OrderPayStatus orderPay = new OrderPayStatus();
 				orderPay.setId(UUID.randomUUID().toString());
 				orderPay.setOutTradeNo(outTradeNo);
 				orderPay.setPaystatus(Integer.parseInt(transStatus));
 				orderPay.setUpdatetime(new Timestamp(System.currentTimeMillis()));
 				orderPay.setStatusdesc(mapParam.toString());
 			
 				int result = orderService.insert(orderPay);
 				if (result > 0) {
 					logger.info("\n 插入支付中心订单状态表成功");
 				} else {
 					logger.error("\n 插入支付中心订单状态表失败");
 				}
                //4、把数据写入队列
               	Map<String, String> params = new Hashtable<String, String>();
             	params.put("out_trade_no", outTradeNo);
           	    params.put("payStatus", transStatus);
           	    params.put("trade_no", transaction_id);
           	    params.put("orderid", order.getOrderid());               	    
           	    params.put("oemid", order.getOemid());               	    
           	    params.put("mchid", order.getMchid());               	    
           	    JSONObject json = JSONObject.fromObject(params);
       	        logger.info("插入队列的数据params："+json.toString());
                boolean flag = queueService.insert(json.toString());
            	if(flag == true){
            		logger.info("支付宝回调信息写入队列成功");
            	}else{
            		logger.info("支付宝回调信息写入队列失败");
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
