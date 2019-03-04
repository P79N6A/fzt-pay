package cn.yesway.pay.center.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author:Mr.Mo
 */
public class MessageHeader implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String oemid;
	private String mchid;
	private String transactionid;
	private Date createtime;
	private String version;
	private int resultcode = 0;	
	private String message;
	private String terminal_type;
	private String refund_id;//微信退款单号
	
	private Integer return_code;
	private String return_msg;
	

	public MessageHeader() {
		super();
	}

	public MessageHeader(int resultcode, String message) {
		super();
		this.resultcode = resultcode;
		this.message = message;
	}

	

	public String getOemid() {
		return oemid;
	}

	public void setOemid(String oemid) {
		this.oemid = oemid;
	}

	public String getMchid() {
		return mchid;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid;
	}

	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getResultcode() {
		return resultcode;
	}

	public void setResultcode(int resultcode) {
		this.resultcode = resultcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public String getTerminal_type() {
		return terminal_type;
	}

	public void setTerminal_type(String terminal_type) {
		this.terminal_type = terminal_type;
	}

	
	
	
	public Integer getReturn_code() {
		return return_code;
	}

	public void setReturn_code(Integer return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
    
	public String getRefund_id() {
		return refund_id;
	}

	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return "MessageHeader{" +
				"oemId=" + oemid +
				"mchId=" + mchid +
				"transactionId=" + transactionid +
				"createTime=" + sdf.format(createtime) +
				"version=" + version +
				"terminal_type=" + terminal_type +
				"resultCode=" + resultcode +
				", message='" + message + '\'' +
				'}';
	}

	
}
