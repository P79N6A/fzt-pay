package cn.yesway.pay.center.exception;

public class BaseException extends Exception {
    private static final long serialVersionUID = -481143207423657534L;

    private int resultCode;
    private String message;

    public BaseException(int resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{resultCode:").append(resultCode).append(", ");
        if (message != null)
            builder.append("message:").append(message);
        builder.append("}");
        return builder.toString();
    }

    public BaseException(int resultCode, String message, Throwable e) {
        super(message, e);
        this.resultCode = resultCode;
        this.message = message;
    }

    @SuppressWarnings("unused")
    private BaseException() {
    }

    @SuppressWarnings("unused")
    private BaseException(String message) {
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @author King
     *
     */
    public static class ErrCode {
    	
    	/**
    	 * 成功
    	 */
    	public static final int Success = 0;
    	/**
    	 * 未退款(默认)
    	 */
    	public static final int NoRefund = -1;
    	/**
    	 * 退款中
    	 */
    	public static final int RefundIng = 0;
    	/**
    	 * 退款成功
    	 */
    	public static final int RefundSuccess = 1;
    	/**
    	 * 退款失败
    	 */
    	public static final int RefundFail = 2;
    	
    	/**
    	 * 参数异常
    	 */
    	public static final int PARAMETER_EXCEPTION  = -1;	
    	/**
    	 * 无法识别OEM身份
    	 */
    	public static final int UNRECOGNIZED_OEM_IDENTITY = -2;
    	/**
    	 * 无法识别商户身份
    	 */
    	public static final int UNRECOGNIZED_MCH_IDENTITY = -3;
    	/**
    	 * 对称密钥失效或错误
    	 */
    	public static final int SYMMETRIC_KEY_FAILURE_OR_ERROR = -4;
    	/**
    	 * 公钥证书失效或错误
    	 */
    	public static final int PUBLIC_KEY_CERTIFICATE_INVALID_OR_INCORRECT  = -5;
    	
    	/**
    	 * 私钥证书失效或错误
    	 */
    	public static final int PRIVATE_KEY_CERTIFICATE_INVALID_OR_INCORRECT  = -6;
    	/**
    	 * 请求方式错误
    	 */
    	public static final int REQUEST_TYPE_ERROR  = -7;
    	
    	/**
    	 * 明文数据异常
    	 */
    	public static final int PLAINTEXT_EXCEPTION  = -8;	
    	/**
    	 * 消息头数据异常
    	 */
    	public static final int MESSAGEHEADER_EXCEPTION  = -9;	
    	/**
    	 * 调用微信API接口出现异常
    	 */
    	public static final int INVOKING_WEIXIN_API_EXCEPTION  = -10;	
    	/**
    	 * 重复提交的订单
    	 */
    	public static final int REPEAT_ORDER_EXCEPTION  = -11;	
    	/**
    	 * 调用支付宝API接口出现异常
    	 */
    	public static final int INVOKING_ALIPAY_API_EXCEPTION  = -12;	
    	
    	/**
		 * 查询无此订单信息
		 */
		public static final int NO_ORDER_INFO = -13;
		
		/**
		 * 终端类型异常
		 */
		public static final int TERMINAL_TYPE  = -14;
		
		/**
		 * 未知错误
		 */
		public static final int INTERNAL_ERROR = -9999;
		
		/**
		 * Alipay退款接口，返回msg缺少必选参数
		 */
		public static final int RETURN_MISSAGE_METHOD = -40001;
		
		/**
		 * Alipay退款接口，返回msg非法的参数
		 */
		public static final int RETURN_INVALID_PARAMETE = -40002;
		
		/**
		 * 系统繁忙
		 */
		public static final int SYSTEM_BUSY = -20000;
		
		
		/**
		 * /业务处理失败
		 */
		public static final int BUSINESS_PROCESS_FAILURE = -40004;
		

    }
}

