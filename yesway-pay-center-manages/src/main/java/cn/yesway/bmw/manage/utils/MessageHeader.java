package cn.yesway.bmw.manage.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

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
