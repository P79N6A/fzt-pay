
package cn.yesway.pay.center.exception;

/**
 * @author 终端类型异常
 *  2017年4月11日下午4:03:21
 *  RequestTerminalTypeException
 */
public class RequestTerminalTypeException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 896010245801766293L;

	public RequestTerminalTypeException() {
		super(BaseException.ErrCode.TERMINAL_TYPE,"终端类型异常");
	}

}
