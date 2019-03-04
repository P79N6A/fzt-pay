package cn.yesway.pay.order.exception;

//import cn.yesway.ycpsp.common.exception.BaseException;


public class DueServiceException extends BaseException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8592112901585675693L;

	public DueServiceException() {
        super(-7, "此服务已过期");
    }
}
