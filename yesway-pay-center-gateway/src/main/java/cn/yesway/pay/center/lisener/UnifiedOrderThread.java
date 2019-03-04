package cn.yesway.pay.center.lisener;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import cn.yesway.pay.center.util.SerialNumberGenerator;
import cn.yesway.pay.center.vo.MessageHeader;
import cn.yesway.pay.order.entity.Orders;
import cn.yesway.pay.order.entity.enums.OrderStatusEnum;
import cn.yesway.pay.order.entity.enums.PayStatusEnum;
import cn.yesway.pay.order.service.GetwayService;

import com.alibaba.fastjson.JSONObject;

public class UnifiedOrderThread extends Thread {
	private final Logger logger = Logger.getLogger(this.getClass());
	private MessageHeader messageheader;
	private JSONObject sdata;
	private GetwayService getwayService;
	private String payOrderNo;
	private String prepayId;
	public UnifiedOrderThread(MessageHeader messageheader, JSONObject sdata,String payOrderNo, String prepay_id, GetwayService getwayService){
		this.messageheader=messageheader;
		this.sdata=sdata;
		this.payOrderNo = payOrderNo;
		this.prepayId = prepay_id;
		this.getwayService=getwayService;
	}
    @Override
    public void run(){
    	logger.info("执行保存订单线程:"+Thread.currentThread().getName());
    	
    	//
	    //将接收到的sdata参数实体转换为数据模型（Orders）
		//
		Orders order = new Orders();
		String oemID = messageheader.getOemid();
		String mchID = messageheader.getMchid();
		String regionCode = null;
		if(StringUtils.isNotEmpty(sdata.getString("region"))){
			regionCode = sdata.getString("region");
		}
		
//		String orderId = UUID.randomUUID().toString();
		String orderId = SerialNumberGenerator.getOrderCode(oemID, mchID, regionCode);
		order.setOrderid(orderId);
	    order.setOemid(oemID);
	    order.setMchid(mchID);
	    
	   /* if(StringUtils.isNotEmpty(sdata.getString("out_trade_no"))){
	    	order.setOutTradeNo(sdata.getString("out_trade_no"));	  
	    }*/
	   
	    //20170531--ghk-add  
	    if(StringUtils.isNotEmpty(payOrderNo)){
	    	//目前取得是宝马订单orderid的值
	    	order.setOutTradeNo(payOrderNo);	 
	    }
	    if(StringUtils.isNotEmpty(prepayId)){
	    	order.setPrepayId(prepayId);	
	    }
	    if(StringUtils.isNotEmpty(sdata.getString("total_amount"))){
	    	order.setTotalAmount(Double.parseDouble(sdata.getString("total_amount")));	 
	    }
	    if(StringUtils.isNotEmpty(sdata.getString("pay_tool_type"))){
	    	order.setPaytooltype(sdata.getInteger("pay_tool_type"));	
	    }
	    if(StringUtils.isNotEmpty(sdata.getString("subject"))){
	    	order.setSubject(sdata.getString("subject"));	    	
	    }
	    if(StringUtils.isNotEmpty(sdata.getString("body"))){
	    	order.setBody(sdata.getString("body"));	    	
	    }
	    if(StringUtils.isNotEmpty(sdata.getString("goods_detail"))){
	    	order.setGoodsDetail(sdata.getString("goods_detail"));	    	
	    }
	    if(StringUtils.isNotEmpty(sdata.getString("attach"))){
	    	order.setAttach(sdata.getString("attach"));	    	    	
	    }
	    if(StringUtils.isNotEmpty(sdata.getString("spbill_create_ip"))){
	    	order.setSpbillCreateIp(sdata.getString("spbill_create_ip"));
	    }
	    if(StringUtils.isNotEmpty(sdata.getString("trade_type"))){
	    	order.setTradeType(sdata.getString("trade_type"));	    	    	
	    }
	    
	    order.setOrderstatus(OrderStatusEnum.Unfinished.code);
	    order.setPaystatus(PayStatusEnum.Unpaid.code);
	    order.setDeviceInfo("WEB");
	    SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
	    try {
	    	if(StringUtils.isNotEmpty(sdata.getString("time_start"))){
	    		order.setTimeStart(format.parse(sdata.getString("time_start")));		    	
		    }
	    	if(StringUtils.isNotEmpty(sdata.getString("time_expire"))){
	    		order.setTimeExpire(format.parse(sdata.getString("time_expire")));		    	
	    	}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    order.setCreater(messageheader.getOemid());
	    order.setCreatetime(new Timestamp(System.currentTimeMillis()));
	    order.setUpdater(messageheader.getOemid());
	    order.setUpdatetime(new Timestamp(System.currentTimeMillis()));
	    
	  
    	int n = getwayService.unifiedOrder(order);		    
	    logger.info("订单保存完毕，插入订单数量："+n);
		
	    
    }
    
    
}