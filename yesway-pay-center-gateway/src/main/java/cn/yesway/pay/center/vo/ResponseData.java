package cn.yesway.pay.center.vo;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * 请求响应的结果
 * @author ys
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 协议头
	 */
	@JsonProperty
	private MessageHeader messageheader;
	/**
	 * 消息体
	 */
	@JsonProperty
	private Object data;
	/**
	 * 来源
	 */
	@JsonProperty
	private String source;
	
	public MessageHeader getMessageheader() {
		return messageheader;
	}
	public void setMessageheader(MessageHeader messageheader) {
		this.messageheader = messageheader;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	
	
}
