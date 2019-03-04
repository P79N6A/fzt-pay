package cn.yesway.pay.center.exception;

public class ReturnInvalidParameteException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6867276270949066996L;

	public ReturnInvalidParameteException() {
		super(BaseException.ErrCode.RETURN_INVALID_PARAMETE, "非法的参数");
	}

}
