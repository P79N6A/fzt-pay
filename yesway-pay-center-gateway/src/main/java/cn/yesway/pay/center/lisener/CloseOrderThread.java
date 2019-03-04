package cn.yesway.pay.center.lisener;

import org.apache.log4j.Logger;

import cn.yesway.pay.order.service.GetwayService;

public class CloseOrderThread extends Thread {
	private final Logger logger = Logger.getLogger(this.getClass());

	private GetwayService getwayService;
	private String outTradeNo;
	public CloseOrderThread(String outTradeNo, GetwayService getwayService){		
		this.outTradeNo = outTradeNo;
		this.getwayService=getwayService;
	}
    @Override
    public void run(){
    	logger.info("执行关闭订单线程:"+Thread.currentThread().getName());
 
    	int n = getwayService.closeOrder(outTradeNo);		
    	logger.info(n>0?"成功关闭订单,"+n : "关闭订单失败");
	    
    }
    
    
}