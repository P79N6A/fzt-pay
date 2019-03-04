package cn.yesway.pay.center.lisener;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import cn.yesway.pay.center.vo.MessageHeader;
import cn.yesway.pay.order.entity.Orders;
import cn.yesway.pay.order.entity.enums.OrderStatusEnum;
import cn.yesway.pay.order.entity.enums.PayStatusEnum;
import cn.yesway.pay.order.service.GetwayService;

import com.alibaba.fastjson.JSONObject;

public class TempUnifiedOrderThread extends Thread {
	private final Logger logger = Logger.getLogger(this.getClass());
	private MessageHeader messageheader;
	private JSONObject sdata;
	private GetwayService getwayService;
	private String prepayId;
	public TempUnifiedOrderThread(MessageHeader messageheader, JSONObject sdata, String prepay_id, GetwayService getwayService){
		this.messageheader=messageheader;
		this.sdata=sdata;
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
		String orderId = UUID.randomUUID().toString();
		order.setOrderid(orderId);
	    order.setOemid(messageheader.getOemid());
	    order.setMchid(messageheader.getMchid());
	    order.setOutTradeNo(sdata.getString("out_trade_no"));
	    order.setPrepayId(prepayId);
	    order.setTotalAmount(Double.parseDouble(sdata.getString("total_amount")));
	    order.setPaytooltype(sdata.getInteger("pay_tool_type"));
	    order.setSubject(sdata.getString("subject"));
	    order.setBody(sdata.getString("body"));
	    order.setGoodsDetail(sdata.getString("goods_detail"));
	    order.setAttach(sdata.getString("attach"));
	    order.setSpbillCreateIp(sdata.getString("spbill_create_ip"));
	    order.setTradeType(sdata.getString("trade_type"));	    
	    order.setOrderstatus(OrderStatusEnum.Unfinished.code);
	    order.setPaystatus(PayStatusEnum.Unpaid.code);
	    order.setDeviceInfo("WEB");
	    order.setCreater(messageheader.getOemid());
	    order.setCreatetime(new Date());
	    order.setUpdater(messageheader.getOemid());
	    order.setUpdatetime(new Date());
	    
	  
    	int n = getwayService.unifiedOrder(order);		    
	    logger.info("订单保存完毕，插入订单数量："+n);
		
	    
    }
    
    
}