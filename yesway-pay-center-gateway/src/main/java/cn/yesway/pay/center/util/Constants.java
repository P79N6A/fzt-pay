package cn.yesway.pay.center.util;

/**
 * @author 常量类 2017年2月22日上午9:22:42 Constants
 */
public class Constants {

	/**
	 * @author 状态 2017年2月22日上午9:25:35 STATUS
	 */
	public class STATUS {
		public static final String SUCCESS = "00";
		public static final String FAILD = "01";
		public static final String RETURN_SUCCESS = "10000";//Alipay退款接口返回成功状态
		public static final String RETURN_MISSAGE_METHOD = "40001";//Alipay退款接口返回缺少必选参数
		public static final String RETURN_INVALID_PARAMETE = "40002";//Alipay退款接口返回非法的参数
		public static final String BUSINESS_PROCESS_FAILURE = "40004";//Alipay退款接口返回业务处理失败
		public static final String PAY_APPLY_NOT_EXISTS = "02";// 不存在
		public static final int APPLY = 0;// 未支付；
		public static final int PAYED_SUCCESS = 1;// 支付交易成功
		public static final int PAYED_ING = 2;// 支付中
		public static final String TRADE_FINISHED = "TRADE_FINISHED";// 交易完成
		public static final String TRADE_SUCCESS = "TRADE_SUCCESS";// 交易支付成功
	}
	
}
