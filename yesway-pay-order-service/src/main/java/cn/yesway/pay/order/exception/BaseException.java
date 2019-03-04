package cn.yesway.pay.order.exception;

public class BaseException extends Exception {
    private static final long serialVersionUID = -481143207423657534L;

    private int errcode;
    private String errmsg;

    public BaseException(int errcode, String errmsg) {
        super(errmsg);
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{errcode:").append(errcode).append(", ");
        if (errmsg != null)
            builder.append("errmsg:").append(errmsg);
        builder.append("}");
        return builder.toString();
    }

    public BaseException(int errcode, String errmsg, Throwable e) {
        super(errmsg, e);
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    @SuppressWarnings("unused")
    private BaseException() {
    }

    @SuppressWarnings("unused")
    private BaseException(String message) {
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public static class ErrCode {
	  //参数异常
        public static final int INVALID_PARAM = -4;
		//系统错误/内部错误
		public static final int INTERNAL_ERROR = -9999;

    }
}

