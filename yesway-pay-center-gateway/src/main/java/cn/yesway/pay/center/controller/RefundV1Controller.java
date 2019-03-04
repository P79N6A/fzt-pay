package cn.yesway.pay.center.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.yesway.pay.center.exception.BaseException;
import cn.yesway.pay.center.exception.NoOrderInfoException;
import cn.yesway.pay.center.exception.RetunrnMissageMethodException;
import cn.yesway.pay.center.exception.ReturnInvalidParameteException;
import cn.yesway.pay.center.exception.SystemBusy;
import cn.yesway.pay.center.exception.SystemException;
import cn.yesway.pay.center.exception.handle.ExceptionHandlerFactory;
import cn.yesway.pay.center.util.ClientCustomSSL;
import cn.yesway.pay.center.util.Constants;
import cn.yesway.pay.center.util.Contants;
import cn.yesway.pay.center.util.FractionDigits;
import cn.yesway.pay.center.util.HttpUtil;
import cn.yesway.pay.center.util.SecurityUtils;
import cn.yesway.pay.center.util.StringUtil;
import cn.yesway.pay.center.util.XMLUtil;
import cn.yesway.pay.center.util.sign.MD5;
import cn.yesway.pay.center.vo.MessageHeader;
import cn.yesway.pay.order.entity.Orders;
import cn.yesway.pay.order.entity.PayCenterConfiguration;
import cn.yesway.pay.order.entity.Refund;
import cn.yesway.pay.order.service.CredentialService;
import cn.yesway.pay.order.service.OrderService;
import cn.yesway.pay.order.service.PayCenterConfigurationService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

/**
 * 退款操作(包括支付宝和微信的退款以及查询操作功能)
 * 
 * @author ghk 2017年2月22日上午9:14:05 RefundController
 */
@Controller
@RequestMapping(value = "/pay/v1")
public class RefundV1Controller extends BaseController {
	private final Logger logger = Logger.getLogger(this.getClass());
	@Value("#{globalConfig.WEIXIN_MCH_API_SECRET}")
	private String KEY_TO_WEIXIN ;
	//微信appid
	/*@Value("#{globalConfig.WEIXIN_APPID}")
	private String WEIXIN_APPID ;*/
	//微信mchid
	/*@Value("#{globalConfig.WEIXIN_MCHID}")
	private String WEIXIN_MCHID ;*/
	//alipay appid
	/*@Value("#{globalConfig.ALIPAY_APPID}")
	private String ALIPAY_APPID ;*/
	
	@Value("#{globalConfig.INPUT_CHARSET}")
	private String INPUT_CHARSET ;
	
	//private String BUTTON_CODE = "ON";//关于退款选择哪种结算方式的按钮，ON:开，no:关
	@Value("#{globalConfig.BUTTON_CODE}")
	private String BUTTON_CODE ;
	//微信商户
	@Value("#{globalConfig.OP_USER_ID}")
	private String OP_USER_ID ;
	/*@Value("#{globalConfig.WEIXIN_MCH_API_SECRET}")
	private String WEIXIN_MCH_API_SECRET ;*/
	//微信退款查询URL
	private  String WEIXIN_REFUND_QUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	//微信退款URL
	private String WEIXIN_REFUND_URL ="https://api.mch.weixin.qq.com/secapi/pay/refund";
	//支付宝退款URL
	private String ALIPAY_GATEWAY ="https://openapi.alipay.com/gateway.do";
	
	private String CODE_SUCCESS = "SUCCESS";
	private String RETURN_MSG = "OK";
	private String PARARM = "传入的参数为空：[";
	private String ENCODE_TYPE = "UTF-8";
	private String CODE_FAIL = "FAIL";
	private String ERRCODE = "NOTENOUGH";//指定未结算资金不足，请使用余额退款方法
	private String ERR_CODE = "999999";
	
	StringBuffer responseSign = new StringBuffer();

	@Autowired
	OrderService orderService;
	@Autowired
	CredentialService credentialService;
	@Autowired
	PayCenterConfigurationService payCenterConfigurationService;

	/**
	 * 退款统一操作
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/refund")
	@ResponseBody
	public Map<String, Object> refund(HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.info("进入退款方法[" + request.getRequestURI() + "]");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		MessageHeader message = (MessageHeader) request.getAttribute("messageheader");
		JSONObject sdata =JSONObject.parseObject((String)request.getAttribute("sdata"));
		JSONObject payOrderData =JSONObject.parseObject((String)request.getAttribute("payOrderNo"));
		JSONObject payRefundNoData =JSONObject.parseObject((String)request.getAttribute("payRefundNo"));
		
		logger.info(message);
		logger.info(sdata);
		logger.info(payOrderData);
		logger.info(payRefundNoData);
		
		try {
			MessageHeader returnMsgHeader = new MessageHeader();
			String oemid = message.getOemid();
			String mchid = message.getMchid();
			logger.info("退款请求头输出oemid:"+oemid+"mchid:"+mchid);
			if(StringUtils.isBlank(oemid) || StringUtils.isBlank(mchid))
			{
				logger.error("退款方法oemid或mchid为空,oemid:"+oemid+"mchid:"+mchid);
				returnMsgHeader.setResultcode(BaseException.ErrCode.PARAMETER_EXCEPTION);
				returnMsgHeader.setMessage("参数异常!");
				returnMap.put("messageheader", returnMsgHeader);
			}
			String outTradeNo = sdata.getString("out_trade_no");// 商户订单号
			if (StringUtil.isBlank(outTradeNo)) {
				logger.info("传入的参数为空：[outTradeNo]");
			}
			String refundAmount = sdata.getString("total_refund_amount");//退款金额
			String totalFee = sdata.getString("total_fee");//总金额
			if (StringUtil.isBlank(refundAmount)) {
				logger.info("传入的参数为空：[refundAmount]");
			} 
			String payOrderNo = null;
			String payRefundNo =null;
			if(payOrderData!=null ){
				payOrderNo = payOrderData.getString("payOrderNo");//支付单号唯一,目前为了订单的唯一性，发送给支付宝和微信的唯一out_trade_no
			}
			if(payRefundNoData!=null){
				payRefundNo = payRefundNoData.getString("payRefundNo");//退款单号
			}
			String  refundReason = sdata.getString("refund_reason");//退款原因
			if(StringUtil.isBlank(payOrderNo)){
				payOrderNo = sdata.getString("payOrderNo");
			}
			if(StringUtil.isBlank(payRefundNo)){
				payRefundNo = sdata.getString("payRefundNo");
			}
			// 1、先根据订单号查询一下，是否存在该订单信息以及该订单的支付状态
			logger.info("支付单号payOrderNo："+payOrderNo);
			List<Orders> resultList = orderService.orderquery(payOrderNo);
			if(resultList==null || resultList.size()==0){
				// 查询失败，直接返回错误信息给请求端
				logger.error("无法查询到该订单号对应的订单信息：商户订单号[" + outTradeNo + "]");
				MessageHeader resultMsgHeader = new MessageHeader();
				resultMsgHeader.setResultcode(BaseException.ErrCode.NO_ORDER_INFO);
				resultMsgHeader.setMessage("查询无此订单信息");
				returnMap.put("messageheader", resultMsgHeader);
			}else{
				logger.info("订单存在，商户订单号:"+outTradeNo);
				Integer  payToolType = resultList.get(0).getPaytooltype();
				logger.info("支付类型："+payToolType);
				PayCenterConfiguration obj = new PayCenterConfiguration();
				obj.setOemId(oemid);
				obj.setMchId(mchid);
				obj.setPayToolType(payToolType);
				PayCenterConfiguration payCenter =  payCenterConfigurationService.findByCondition(obj);
				if(payCenter==null){
					obj.setMchId("all");
					payCenter = payCenterConfigurationService.findByCondition(obj);
				}
				if(payCenter==null){
					logger.error("根据oemid和mchid查询支付配置表为空！");
					returnMsgHeader.setResultcode(BaseException.ErrCode.PARAMETER_EXCEPTION);
					returnMsgHeader.setMessage("没有配置对应的支付配置信息!");
					returnMap.put("messageheader", returnMsgHeader);
					return returnMap;
				}
				String orderid=resultList.get(0).getOrderid();
				double price =0;
				double totalMoney =0;
				price=Double.parseDouble(refundAmount);
				if(null==totalFee || "".equals(totalFee)){
					totalMoney = resultList.get(0).getTotalAmount();
					totalFee = Double.toString(totalMoney);
				}
				if(payToolType==1){//支付宝
					AlipayClient alipayClient = new DefaultAlipayClient(ALIPAY_GATEWAY,payCenter.getAppId(),payCenter.getYeswayAlipayPrivateKey(),"JSON",Contants.CHARSET,payCenter.getAlipayPublicKey(),Contants.SIGN_TYPE);
					AlipayTradeRefundRequest requestRefund = new AlipayTradeRefundRequest();
					Map<String,Object> param = new HashMap<String,Object>();
					param.put("out_trade_no", payOrderNo);
					param.put("out_request_no", payRefundNo);
					param.put("refund_amount", refundAmount);
					param.put("refund_reason", refundReason);
					param.put("sign_type", Contants.SIGN_TYPE);
					
					String linkStr = StringUtil.linkString(param, "&");
					logger.info("调用支付宝的退款接口之前拼接对应的请求参数，linkStr："+linkStr);
					String sign = AlipaySignature.rsa256Sign(linkStr, payCenter.getYeswayAlipayPrivateKey(), Contants.CHARSET);		
					param.put("sign", sign);
					
					String json = JSON.toJSONString(param);	
					logger.info("拼装Alipay请求退款接口参数json:"+json);
					requestRefund.setBizContent(json);		
					AlipayTradeRefundResponse responseRefund = alipayClient.execute(requestRefund);
					String refundresponse=responseRefund.getBody();
					logger.info("调用支付宝退款接口返回信息responseRefund："+refundresponse.toString());
					MessageHeader resultMsgHeader = new MessageHeader();
					if(responseRefund.isSuccess()){
						logger.info("调用Alipay退款接口成功");
						resultMsgHeader.setResultcode(BaseException.ErrCode.Success);
						resultMsgHeader.setMessage("退款成功!");
						returnMap.put("messageheader", resultMsgHeader);
					} else {
						logger.info("调用Alipay退款接口失败");
						//针对退款失败以后，重新执行退款操作
						AlipayTradeRefundResponse repeatRefund = alipayClient.execute(requestRefund);
						String repeatResponse=repeatRefund.getBody();
						logger.info("重新调用退款接口返回信息："+repeatResponse.toString());
						if(repeatRefund.isSuccess()){
							logger.info("重新调用Alipay退款接口成功");
							resultMsgHeader.setResultcode(BaseException.ErrCode.Success);
							resultMsgHeader.setMessage("退款成功!");
							returnMap.put("messageheader", resultMsgHeader);
						}else{
							logger.info("重新调用Alipay退款接口失败");
						}
					}
					String data=responseRefund.getBody();
					JSONObject object=JSONObject.parseObject(data);
					JSONObject ob = (JSONObject) object.get("alipay_trade_refund_response");
					String code =  (String)ob.get("code");
					String msg =  (String)ob.get("msg");
					String subcode =  (String)ob.get("sub_code");
					String submsg =  (String)ob.get("sub_msg");
					if(Constants.STATUS.RETURN_SUCCESS.equals(code)){//调Alipay的退款接口成功10000
						Refund refund = new Refund();
						refund.setRefundid(payRefundNo);// 退款单id
						refund.setOutTradeNo(outTradeNo);//商户订单号
						refund.setPayOrderNo(payOrderNo);
						refund.setRefundamount(new BigDecimal(refundAmount));//退款金额
						refund.setRefundreason(refundReason);//退款原因
						refund.setAddtime(new Timestamp(System.currentTimeMillis()));
						refund.setEndtime(new Timestamp(System.currentTimeMillis()));
						refund.setOrderid(orderid);
						int refundResult = orderService.refund(refund);
						if (refundResult == 1) {
							logger.info("alipay退款操作写入数据库表refund成功");
						} else {
							logger.info("alipay退款写入数据库refund表操作失败");
						}
					}else if(Constants.STATUS.RETURN_MISSAGE_METHOD.equals(code)){
						logger.info("调用Alipay退款接口失败，返回码code:"+code+"返回信息:msg"+msg);
						resultMsgHeader.setResultcode(BaseException.ErrCode.RETURN_MISSAGE_METHOD);
						resultMsgHeader.setMessage("退款失败,缺少必选参数!");
						returnMap.put("messageheader", resultMsgHeader);
					}else if(Constants.STATUS.RETURN_INVALID_PARAMETE.equals(code)){
						logger.info("调用Alipay退款接口失败，返回码code:"+code+"返回信息:msg"+msg);
						resultMsgHeader.setResultcode(BaseException.ErrCode.RETURN_INVALID_PARAMETE);
						returnMap.put("messageheader", resultMsgHeader);
					}else if(Constants.STATUS.BUSINESS_PROCESS_FAILURE.equals(code)){//业务处理失败
						logger.info("调用Alipay退款接口失败，返回码code:"+code+"返回信息:msg"+msg+"subcode明细返回码"+subcode+"明细返回码描述submsg"+submsg);
						resultMsgHeader.setResultcode(BaseException.ErrCode.BUSINESS_PROCESS_FAILURE);
						returnMap.put("messageheader", resultMsgHeader);
					}else{
						logger.info("调用Alipay退款接口失败，返回码code:"+code+"返回信息:msg"+msg);
						resultMsgHeader.setResultcode(BaseException.ErrCode.SYSTEM_BUSY);
						returnMap.put("messageheader", resultMsgHeader);
					}
					
				}else if(payToolType==2){//微信
					//微信的总金额要转为int类型
					double d1 =  Double.parseDouble(refundAmount);
					double d2 = FractionDigits.fractionDigits(d1*100, 0, false);
					BigDecimal refundprice = new BigDecimal(d2);
					
					double a = Double.parseDouble(totalFee);
					double b = FractionDigits.fractionDigits(a*100, 0, false);
					BigDecimal refundTotalprice = new BigDecimal(b);
					
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("appid", payCenter.getAppId());
					params.put("mch_id", payCenter.getWeixinMchId());		
					params.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));		
					//params.put("op_user_id", op_user_id);
					params.put("out_refund_no", payRefundNo);
					params.put("out_trade_no", payOrderNo);
					params.put("refund_fee", refundprice.toString());
					params.put("total_fee", refundTotalprice.toString());
					params.put("transaction_id", "");
					params.put("refund_account", "");//REFUND_SOURCE_RECHARGE_FUNDS
					params = getSignStr(params,payCenter.getWeixinApiSecret());
					logger.info("微信退款请求数据:"+params);
					String xmlStr = XMLUtil.map2xmlBody(params, "xml");
					String wxUrl = WEIXIN_REFUND_URL; 
					// 发送请求并接收响应数据
					String weixinPost = null;
					try {
						logger.info("调用微信退款接口:"+wxUrl);
						weixinPost = ClientCustomSSL.doRefund(wxUrl, xmlStr,payCenter.getWeixinMchId(),payCenter.getCredentialAdress()).toString();
						logger.info("微信退款接口返回信息：weixinPost"+weixinPost.toString());
					} catch (Exception e) {
						logger.error("调用微信退款接口失败！"+e.getMessage());
					}
					if (StringUtils.isBlank(weixinPost)) {
						logger.info("微信退款接口返回为空！！！");
						
						MessageHeader resultMsgHeader = new MessageHeader();
						resultMsgHeader.setResultcode(BaseException.ErrCode.INVOKING_WEIXIN_API_EXCEPTION);
						returnMap.put("messageheader", resultMsgHeader);
						//针对微信退款操作失败，重新执行调用退款操作接口
						weixinPost = ClientCustomSSL.doRefund(wxUrl,xmlStr,payCenter.getWeixinMchId(),payCenter.getCredentialAdress()).toString();
						logger.info("微信退款接口返回信息：weixinPost"+weixinPost.toString());
						if(StringUtils.isBlank(weixinPost)){
							logger.info("微信退款接口返回为空！！！");
							resultMsgHeader.setResultcode(BaseException.ErrCode.INVOKING_WEIXIN_API_EXCEPTION);
							resultMsgHeader.setMessage("调用微信API接口出现异常");
							returnMap.put("messageheader", resultMsgHeader);
						}
					}
					Map<String, String> mapParam = XMLUtil.readStringXmlOut(weixinPost);
					String return_code = mapParam.get("return_code");// SUCCESS/FAIL，此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
					String return_msg = mapParam.get("return_msg");// SUCCESS/FAIL，此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
					String resultCode = mapParam.get("result_code");
					String refundId = mapParam.get("refund_id");//微信退款单号
					String transactionId = mapParam.get("transaction_id");//微信订单号
					String errCodeDes = mapParam.get("err_code_des");//错误描述
					String errCode = mapParam.get("err_code");//错误描述
					
					
					if (return_code.equals(CODE_FAIL)) {
						logger.info("调用微信退款查询接口返回信息失败，返回码：return_code"+return_code +"返回信息是return_msg"+return_msg);
						MessageHeader resultMsgHeader = new MessageHeader();
						resultMsgHeader.setResultcode(BaseException.ErrCode.INVOKING_WEIXIN_API_EXCEPTION);
						resultMsgHeader.setMessage(return_msg);
						returnMap.put("messageheader", resultMsgHeader);
					}
					if(return_code.equals(CODE_SUCCESS)&& resultCode.equals(CODE_SUCCESS)){
						logger.info("调用微信退款接口返回成功,result_code:"+return_code+"返回信息return_msg:"+return_msg);
						
						MessageHeader resultMsgHeader = new MessageHeader();
						resultMsgHeader.setResultcode(BaseException.ErrCode.Success);
						resultMsgHeader.setTransactionid(transactionId);
						resultMsgHeader.setRefund_id(refundId);
						resultMsgHeader.setMessage("退款申请成功");
						returnMap.put("messageheader", resultMsgHeader);
					}
					if(return_code.equals(CODE_SUCCESS) && resultCode.equals(CODE_FAIL)){
						logger.info("调用微信退款接口返回失败,result_code:"+return_code+"返回信息resultCode:"+resultCode);
						MessageHeader resultMsgHeader = new MessageHeader();
						resultMsgHeader.setResultcode(BaseException.ErrCode.BUSINESS_PROCESS_FAILURE);
						resultMsgHeader.setMessage(errCodeDes);
						returnMap.put("messageheader", resultMsgHeader);
					}
					logger.info("退款重试开关状态BUTTON_CODE="+BUTTON_CODE);
					if(BUTTON_CODE.equals("ON")){//代表开
						logger.info("退款重试开启,未结算金额不足，使用余额退款开始!");
						//指定判断退款"可使用未结算资金不足，使用余额退款的方法"解决方法，如果可用结算资金不够改为“可用余额”进行退款
						if(resultCode.equals(CODE_FAIL) && errCode.equals(ERRCODE)){
							logger.info("未结算金额不足，使用余额退款开始!");
							Map<String, Object> params2 = new HashMap<String, Object>();
							params2.put("appid", payCenter.getAppId());
							params2.put("mch_id", payCenter.getWeixinMchId());		
							params2.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));		
							//params.put("op_user_id", op_user_id); 
							params2.put("out_refund_no", payRefundNo);
							params2.put("out_trade_no", payOrderNo);
							params2.put("refund_fee", refundprice.toString());
							params2.put("total_fee", refundTotalprice.toString());
							params2.put("transaction_id", "");
							params2.put("refund_account", "REFUND_SOURCE_RECHARGE_FUNDS");//REFUND_SOURCE_RECHARGE_FUNDS
							
							params2 = getSignStr(params2,payCenter.getWeixinApiSecret());
							logger.info("微信退款请求数据:"+params2);
							String xmlStr2 = XMLUtil.map2xmlBody(params2, "xml");
							// 发送请求并接收响应数据
							String weixinPost2 = null;
							// 发送请求并接收响应数据
							logger.info("调用微信退款接口:"+wxUrl);
							weixinPost2 = ClientCustomSSL.doRefund(wxUrl, xmlStr2,payCenter.getWeixinMchId(),payCenter.getCredentialAdress()).toString();
							logger.info("微信退款接口返回信息：weixinPost"+weixinPost2.toString());
							if (StringUtils.isBlank(weixinPost2)) {
								logger.info("微信退款接口返回为空！！！");
								MessageHeader resultMsgHeader = new MessageHeader();
								resultMsgHeader.setResultcode(BaseException.ErrCode.INVOKING_WEIXIN_API_EXCEPTION);
								returnMap.put("messageheader", resultMsgHeader);
							}
							Map<String, String> mapParam2 = XMLUtil.readStringXmlOut(weixinPost2);
							String return_code2 = mapParam2.get("return_code");// SUCCESS/FAIL，此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
							String return_msg2 = mapParam2.get("return_msg");// SUCCESS/FAIL，此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
							String resultCode2 = mapParam2.get("result_code");
							String refundId2 = mapParam2.get("refund_id");//微信退款单号
							String transactionId2 = mapParam2.get("transaction_id");//微信订单号
							String errCodeDes2 = mapParam2.get("err_code_des");//错误描述
							String errCode2 = mapParam2.get("err_code");//错误描述
							if (return_code2.equals(CODE_FAIL)) {
								logger.info("调用微信退款查询接口返回信息失败，返回码：return_code"+return_code2 +"返回信息是return_msg"+return_msg2);
								MessageHeader resultMsgHeader = new MessageHeader();
								resultMsgHeader.setResultcode(BaseException.ErrCode.INVOKING_WEIXIN_API_EXCEPTION);
								resultMsgHeader.setMessage(return_msg2);
								returnMap.put("messageheader", resultMsgHeader);
							}
							if(return_code2.equals(CODE_SUCCESS)&& resultCode2.equals(CODE_SUCCESS)){
								logger.info("调用微信退款接口返回成功,result_code:"+return_code2+"返回信息return_msg:"+return_msg2);
								MessageHeader resultMsgHeader = new MessageHeader();
								resultMsgHeader.setResultcode(BaseException.ErrCode.Success);
								resultMsgHeader.setTransactionid(transactionId2);
								resultMsgHeader.setRefund_id(refundId2);
								resultMsgHeader.setMessage("退款申请成功");
								returnMap.put("messageheader", resultMsgHeader);
							}
							if(return_code2.equals(CODE_SUCCESS) && resultCode2.equals(CODE_FAIL)){
								logger.info("调用微信退款接口返回失败,result_code:"+return_code+"返回信息resultCode:"+resultCode);
								MessageHeader resultMsgHeader = new MessageHeader();
								resultMsgHeader.setResultcode(BaseException.ErrCode.BUSINESS_PROCESS_FAILURE);
								resultMsgHeader.setMessage(errCodeDes2);
								returnMap.put("messageheader", resultMsgHeader);
							}
						}
						
					}
					// 1、先根据订单id去db查询是否存在，如果不存在，直接insert，否则
					// 调用dubbo接口插入数据库的金额
					Refund refund = new Refund();
					refund.setRefundid(payRefundNo);//outRefundNo
					refund.setOutTradeNo(outTradeNo);
					refund.setPayOrderNo(payOrderNo);
					refund.setRefundamount(new BigDecimal(price));//转换为分的金额
					refund.setAddtime(new Timestamp(System.currentTimeMillis()));
					refund.setEndtime(new Timestamp(System.currentTimeMillis()));
					refund.setRefundreason(refundReason);
					refund.setOrderid(orderid);
					int refundResult = orderService.refund(refund);
					if (refundResult == 1) {
						logger.info("退款操作写入数据库refund成功");
					} else {
						logger.info("退款操作写入数据库refund失败");
					}
					
				}else{
					logger.error("未知的支付工具类型");
				}
			}
		} catch (Exception e) {
			logger.error("未知的错误"+e.getMessage());
			MessageHeader resultMsgHeader = new MessageHeader();
			resultMsgHeader.setResultcode(BaseException.ErrCode.INTERNAL_ERROR);
			resultMsgHeader.setMessage("未知的错误");
			returnMap.put("messageheader", resultMsgHeader);
		} 
		logger.info("结束‘申请退款’方法 ["+request.getRequestURI()+"]");
		return returnMap;
		
	}
   
	
	
	/**
	 * 退款查询统一入口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/refundquery")
	@ResponseBody
	public Map<String, Object> refundquery(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> returnMap = new HashMap<String,Object>();
		logger.info("开始进入‘退款查询’方法 ["+request.getRequestURI()+"]");
		MessageHeader messageheader = (MessageHeader) request.getAttribute("messageheader");
		JSONObject sdata =JSONObject.parseObject((String)request.getAttribute("sdata"));
		
		logger.info(messageheader);
		logger.info(sdata);
		
		try {
			MessageHeader returnMsgHeader = new MessageHeader();
			String oemid = messageheader.getOemid();
			String mchid = messageheader.getMchid();
			logger.info("退款请求头输出oemid:"+oemid+"mchid:"+mchid);
			if(StringUtils.isBlank(oemid) || StringUtils.isBlank(mchid))
			{
				logger.error("退款方法oemid或mchid为空,oemid:"+oemid+"mchid:"+mchid);
				returnMsgHeader.setResultcode(BaseException.ErrCode.PARAMETER_EXCEPTION);
				returnMsgHeader.setMessage("参数异常!");
				returnMap.put("messageheader", returnMsgHeader);
			}
			
			String outTradeNo = sdata.getString("out_trade_no");// 订单编号
			String outRefundNo = sdata.getString("out_request_no");// 退款单号
			String refundId = sdata.getString("refund_id");// 微信生成的退款单号，在申请退款接口有返回
			String tradeNo = sdata.getString("trade_no");// 
			String payOrderNo = sdata.getString("pay_Order_No");//流水号
			String  payToolType = sdata.getString("pay_tool_type");//工具类型
			List<Refund> refundResult=orderService.selectRefundInfo(outTradeNo);
			if(refundResult==null || refundResult.size()==0){
				logger.error("无法查询到该订单号对应的订单信息：商户订单号[" + outTradeNo + "]");
				returnMap.put("messageheader",ExceptionHandlerFactory.getHandler().process(new NoOrderInfoException()));
				return returnMap;
			}
			   logger.info("查询该订单存在:"+refundResult.toString());
			   List<Orders> resultList = orderService.orderquery(payOrderNo);
			   if(resultList!=null && resultList.size()>0){
				   if(payToolType==null ){
					 Integer  payToolType2 = resultList.get(0).getPaytooltype();
					 payToolType = payToolType2+"";
				   }
			   }
			  
			   PayCenterConfiguration obj = new PayCenterConfiguration();
				obj.setOemId(oemid);
				obj.setMchId(mchid);
				obj.setPayToolType(Integer.valueOf(payToolType));
				PayCenterConfiguration payCenter =  payCenterConfigurationService.findByCondition(obj);
				if(payCenter==null){
					obj.setMchId("all");
					payCenter =  payCenterConfigurationService.findByCondition(obj);
				}
				if(payCenter==null){
					logger.error("根据oemid和mchid查询支付配置表为空！");
					returnMsgHeader.setResultcode(BaseException.ErrCode.PARAMETER_EXCEPTION);
					returnMsgHeader.setMessage("没有配置对应的支付配置信息!");
					returnMap.put("messageheader", returnMsgHeader);
					return returnMap;
				}
				
			   if(payToolType.equals("1")){//支付宝
				    AlipayClient alipayClient = new DefaultAlipayClient(ALIPAY_GATEWAY,payCenter.getAppId(),payCenter.getYeswayAlipayPrivateKey(),"JSON",Contants.CHARSET,payCenter.getAlipayPublicKey(),Contants.SIGN_TYPE);
				    AlipayTradeFastpayRefundQueryRequest requestQuery = new AlipayTradeFastpayRefundQueryRequest();
					Map<String,Object> param = new HashMap<String,Object>();
					param.put("out_trade_no", payOrderNo);
					param.put("trade_no", tradeNo);
					param.put("out_request_no", outRefundNo);
					param.put("sign_type", Contants.SIGN_TYPE);
					
					String linkStr = StringUtil.linkString(param, "&");
					logger.info("调用支付宝的退款接口之前拼接对应的请求参数，linkStr："+linkStr);
					String sign = AlipaySignature.rsa256Sign(linkStr, payCenter.getYeswayAlipayPrivateKey(), Contants.CHARSET);		
					param.put("sign", sign);
					
					String json = JSON.toJSONString(param);	
					logger.info(json);
					requestQuery.setBizContent(json);		
				   
					AlipayTradeFastpayRefundQueryResponse responseQuery = alipayClient.execute(requestQuery);
				    String data =responseQuery.getBody();
				    logger.info("alipay退款查询接口返回body信息:"+data.toString());
					if(responseQuery.isSuccess()){
						logger.info("调用成功");
					} else {
						logger.info("调用失败");
					}
					JSONObject object=JSONObject.parseObject(data);
					JSONObject ob = (JSONObject) object.get("alipay_trade_fastpay_refund_query_response");
					String code =  (String)ob.get("code");
					String msg =  (String)ob.get("msg");
					String sub_msg =  (String)ob.get("sub_msg");
					
					if(Constants.STATUS.RETURN_SUCCESS.equals(code)){//调Alipay的退款接口成功10000
						logger.info("调用Alipay退款接口成功，返回码code:"+code+"返回信息:msg"+msg);
						MessageHeader resultMsgHeader = new MessageHeader();
						resultMsgHeader.setResultcode(BaseException.ErrCode.RefundSuccess);
						resultMsgHeader.setMessage(msg);
						returnMap.put("messageheader", resultMsgHeader);
					}else if(Constants.STATUS.RETURN_MISSAGE_METHOD.equals(code)){
						logger.info("调用Alipay退款接口失败，返回码code:"+code+"返回信息:msg"+msg);
						returnMap.put("messageheader",ExceptionHandlerFactory.getHandler().process(new RetunrnMissageMethodException()));
						return returnMap;
					}else if(Constants.STATUS.RETURN_INVALID_PARAMETE.equals(code)){
						logger.info("调用Alipay退款接口失败，返回码code:"+code+"返回信息:msg"+msg);
						returnMap.put("messageheader",ExceptionHandlerFactory.getHandler().process(new ReturnInvalidParameteException()));
						return returnMap;
					}else{
						logger.info("调用Alipay退款接口失败，返回码code:"+code+"返回信息:msg"+msg);
						MessageHeader resultMsgHeader = new MessageHeader();
						resultMsgHeader.setResultcode(BaseException.ErrCode.SYSTEM_BUSY);
						resultMsgHeader.setMessage(sub_msg);
						resultMsgHeader.setReturn_code(Integer.parseInt(code));
						returnMap.put("messageheader", resultMsgHeader);
					}
					//String rData=JSONObject.toJSONString(data);
					returnMap.put("rData", ob);
					
			   }else if(payToolType.equals("2")){//微信
				   Map<String, Object> params = new HashMap<String, Object>();
					params.put("appid", payCenter.getAppId());
					params.put("mch_id", payCenter.getWeixinMchId());
					params.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));
					params.put("out_refund_no", outRefundNo);
					params.put("out_trade_no", payOrderNo);
					params.put("refund_id", refundId);
					params.put("transaction_id", "");
					
					String asciiString = StringUtil.linkString(params, "&");
					// 拼接asciiString和商户密钥
					String key = payCenter.getWeixinApiSecret();
					asciiString += "&key=" + key;
					// md5编码并转成大写
					String sign = SecurityUtils.getMD5(asciiString).toUpperCase();
					params.put("sign", sign);
					String xmlStr = XMLUtil.map2xmlBody(params, "xml");
					String result = HttpUtil.postByXml(WEIXIN_REFUND_QUERY_URL, xmlStr);
					logger.info("调用微信退款查询接口回调返回信息："+result.toString());
					if (StringUtils.isBlank(result)) {
						logger.info("调用微信退款查询接口失败");
						MessageHeader resultMsgHeader = new MessageHeader();
						resultMsgHeader.setResultcode(BaseException.ErrCode.INVOKING_WEIXIN_API_EXCEPTION);
						returnMap.put("messageheader", resultMsgHeader);
					}
					Map<String, String> mapParam = XMLUtil.readStringXmlOut(result);
					String return_code = mapParam.get("return_code");// SUCCESS/FAIL，此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
					String return_msg = mapParam.get("return_msg");// SUCCESS/FAIL，此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
					String resultCode = mapParam.get("result_code");
					String errCodeDes = mapParam.get("err_code_des");
					if (return_code.equals(CODE_FAIL)) {
						logger.info("调用微信退款查询接口返回信息失败，返回码：return_code"+return_code +"返回信息是return_msg"+return_msg);
						MessageHeader resultMsgHeader = new MessageHeader();
						resultMsgHeader.setResultcode(BaseException.ErrCode.RefundFail);
						resultMsgHeader.setMessage(return_msg);
						returnMap.put("messageheader", resultMsgHeader);
					}
					if(return_code.equals(CODE_SUCCESS) && resultCode.equals(CODE_SUCCESS)){
						logger.info("调用微信退款查询接口返回成功,return_code:"+return_code+"返回信息return_msg:"+return_msg);
						MessageHeader resultMsgHeader = new MessageHeader();
						resultMsgHeader.setResultcode(BaseException.ErrCode.RefundSuccess);
						resultMsgHeader.setMessage(return_msg);
						returnMap.put("messageheader", resultMsgHeader);
					}
					if(return_code.equals(CODE_SUCCESS)&& resultCode.equals(CODE_FAIL)){
						logger.info("调用微信退款接口返回失败,result_code:"+return_code+"返回信息resultCode:"+resultCode);
						MessageHeader resultMsgHeader = new MessageHeader();
						resultMsgHeader.setResultcode(BaseException.ErrCode.BUSINESS_PROCESS_FAILURE);
						resultMsgHeader.setMessage(errCodeDes);
						returnMap.put("messageheader", resultMsgHeader);
					}
					String rData=JSONObject.toJSONString(mapParam);
					returnMap.put("rData", rData);
			   }
		} catch (Exception e) {
			logger.error("退款查询失败！"+e.getMessage());
			returnMap.put("messageheader",ExceptionHandlerFactory.getHandler().process(new Exception()));
			return returnMap;
		}
		logger.info("结束‘退款查询’方法 ["+request.getRequestURI()+"]");
		return returnMap;	
	}
	
	/**
	 * 查询退款操作(支付宝)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/alipayrefundquery")
	@ResponseBody
	public Map<String, Object> alipayrefundquery(HttpServletRequest request,HttpServletResponse response) {
		
			//把请求参数打包成数组
			Map<String, Object> returnMap = new HashMap<String,Object>();
			logger.info("开始进入‘查询退款’方法 ["+request.getRequestURI()+"]");
			MessageHeader messageheader = (MessageHeader) request.getAttribute("messageheader");
			JSONObject sdata =JSONObject.parseObject((String)request.getAttribute("sdata"));
			logger.info(messageheader);
			logger.info(sdata);
		try {	
			String outTradeNo = sdata.getString("out_trade_no");
			String tradeNo = sdata.getString("trade_no");// 支付宝交易号，和商户订单号不能同时为空
			String outRefundNo = sdata.getString("out_request_no");// 退款单号
			String payOrderNo = sdata.getString("pay_Order_No");//流水号
			if (StringUtils.isBlank(outTradeNo)) {
				//throw new SystemException(PARARM + "outTradeNo]");
			}
			if (StringUtils.isBlank(outRefundNo)) {
				//throw new SystemException(PARARM + "outRefundNo]");
			}
			
		   List<Refund> refundResult=orderService.selectRefundInfo(outTradeNo);
		   if(refundResult.size()>0){
			   logger.info("查询该订单存在:"+refundResult.toString());
		   }else{
			 logger.error("该订单不存在:"+outTradeNo);  
			 throw new SystemException("该订单不存在:" + outRefundNo);
		   }
		   
		   AlipayClient alipayClient = new DefaultAlipayClient(Contants.ALIPAY_GATWAY_URL,Contants.APP_ID,Contants.YESWAY_PRIVATE_KEY,"JSON",Contants.CHARSET,Contants.ALIPAY_PUBLIC_KEY,Contants.SIGN_TYPE);
		   AlipayTradeFastpayRefundQueryRequest requestQuery = new AlipayTradeFastpayRefundQueryRequest();
		   Map<String,Object> param = new HashMap<String,Object>();
			param.put("out_trade_no","");//payOrderNo
			param.put("trade_no", tradeNo);//tradeNo
			param.put("out_request_no", outRefundNo);//outRefundNo
			param.put("sign_type", Contants.SIGN_TYPE);
			
			String linkStr = StringUtil.linkString(param, "&");
			System.out.println(linkStr);
			
			String mchPrivateKey=Contants.YESWAY_PRIVATE_KEY;
			String sign = AlipaySignature.rsa256Sign(linkStr, mchPrivateKey, Contants.CHARSET);		
			param.put("sign", sign);
			String json = JSON.toJSONString(param);		
			
			requestQuery.setBizContent(json);		 
			AlipayTradeFastpayRefundQueryResponse responseQuery = alipayClient.execute(requestQuery);
		    String data =responseQuery.getBody();
		    System.out.println("Alipay退款查询接口返回信息"+data.toString());
			if(responseQuery.isSuccess()){
				logger.info("调用成功");
			} else {
				logger.info("调用失败");
			}
			JSONObject object=JSONObject.parseObject(data);
			JSONObject ob = (JSONObject) object.get("alipay_trade_fastpay_refund_query_response");
			String code =  (String)ob.get("code");
			String msg =  (String)ob.get("msg");
			
			if(Constants.STATUS.RETURN_SUCCESS.equals(code)){//调Alipay的退款接口成功10000
				logger.info("调用Alipay退款接口成功，返回码code:"+code+"返回信息:msg"+msg);
				MessageHeader resultMsgHeader = new MessageHeader();
				resultMsgHeader.setResultcode(BaseException.ErrCode.Success);
				returnMap.put("messageheader", resultMsgHeader);
			}else if(Constants.STATUS.RETURN_MISSAGE_METHOD.equals(code)){
				logger.info("调用Alipay退款接口失败，返回码code:"+code+"返回信息:msg"+msg);
				returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new RetunrnMissageMethodException())));
				return returnMap;
			}else if(Constants.STATUS.RETURN_INVALID_PARAMETE.equals(code)){
				logger.info("调用Alipay退款接口失败，返回码code:"+code+"返回信息:msg"+msg);
				returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new ReturnInvalidParameteException())));
				return returnMap;
			}else{
				logger.info("调用Alipay退款接口失败，返回码code:"+code+"返回信息:msg"+msg);
				returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new SystemBusy())));
				return returnMap;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("messageheader",JSON.toJSONString(ExceptionHandlerFactory.getHandler().process(new Exception())));
			return returnMap;
		}
		logger.info("结束‘查询退款’方法 ["+request.getRequestURI()+"]");
		return returnMap;	

	}

	/**
	 * 微信退款查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/wxrefundquery")
	public void weixinrefundquery(HttpServletRequest request,HttpServletResponse response) {
		PrintWriter printWriter = null;
		String returnCodeToApp = CODE_SUCCESS;
		String returnMsg = RETURN_MSG;
		String resultCode = CODE_SUCCESS;
		String errCode = "";
		String errCodeDes = "";
		String weixin_key = KEY_TO_WEIXIN;
		int status=0;
		Map<String, Object> returnMap = new HashMap<String,Object>();
		logger.info("开始进入‘查询微信退款’方法 ["+request.getRequestURI()+"]");
		MessageHeader messageheader = (MessageHeader) request.getAttribute("messageheader");
		JSONObject sdata =JSONObject.parseObject((String)request.getAttribute("sdata"));
		
		logger.info(messageheader);
		logger.info(sdata);
		
		try {
			PrintWriter writer = response.getWriter();
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			printWriter = response.getWriter();
			String appId = "";// 微信开放平台审核通过的应用APPID
			String mchId = messageheader.getMchid();// 微信支付分配的商户号
			String outTradeNo = sdata.getString("out_trade_no");// 订单编号
			String outRefundNo = sdata.getString("out_refund_no");// 退款单号
			String refundId = sdata.getString("refund_id");// 微信生成的退款单号，在申请退款接口有返回 
			String transactionId = messageheader.getTransactionid();// 微信订单号
			String signStr = sdata.getString("sign");
			if (StringUtils.isBlank(outTradeNo)) {
				throw new SystemException(PARARM + "outTradeNo]");
			}
			if (StringUtils.isBlank(outRefundNo)) {
				throw new SystemException(PARARM + "outRefundNo]");
			}
			// 生成随机数
			Random random = new Random();
			String nonce_str = String.valueOf(Math.abs(random.nextLong()));
			// 2.验证签名
			String infoStr = appId + ";" + mchId + ";" + nonce_str + ";"
					+ outRefundNo + ";" + outTradeNo + ";" + refundId + ";"
					+ transactionId;// 签名串,所有的参数
			String infoMd5ser = MD5.sign(infoStr, "", ENCODE_TYPE)
					.toUpperCase();
			if (!signStr.equals(infoMd5ser)) {
				resultCode = CODE_FAIL;
				errCode = ERR_CODE;
				errCodeDes = "微信退款：签名验证失败！";
				logger.error(errCodeDes);
				return;
			}
			// 签名
			String sign = "";
			StringBuilder signBefore = new StringBuilder();
			signBefore.append("appid=").append(appId);
			signBefore.append("&mch_id=").append(mchId);
			signBefore.append("&nonce_str=").append(nonce_str);
			signBefore.append("&out_refund_no=").append(outRefundNo);
			signBefore.append("&out_trade_no=").append(outTradeNo);
			signBefore.append("&refund_id=").append(refundId);
			signBefore.append("&transaction_id=").append(transactionId);

			signBefore.append("&key=").append(weixin_key);
			logger.info("签名前=" + signBefore.toString());
			sign = MD5.sign(signBefore.toString(), "", "utf-8").toUpperCase();
			logger.info("签名后=" + sign);

			StringBuilder requstStr = new StringBuilder();
			requstStr.append("<xml>");
			requstStr.append("	<appid>").append(appId).append("</appid>");//
			requstStr.append("	<mch_id>").append(mchId).append("</mch_id>");// 商户号
			requstStr.append("	<nonce_str>").append(nonce_str).append("</nonce_str>");// 随机字符串
			requstStr.append("	<out_refund_no>").append(outRefundNo).append("</out_refund_no>");// 退款单号
			requstStr.append("	<out_trade_no>").append(outTradeNo).append("</out_trade_no>");// 商户订单号
			requstStr.append("	<refund_id>").append(refundId).append("</refund_id>");// 微信生成的退款单号，在申请退款接口有返回 
			requstStr.append("	<transaction_id>").append(transactionId).append("</transaction_id>");// 微信订单号
			requstStr.append("	<sign>").append(sign).append("</sign>");// 签名
			requstStr.append("</xml>");

			// 发送请求并接收响应数据
			String xmlParam = HttpUtil.postByXml(WEIXIN_REFUND_URL,requstStr.toString());// 查询退款地址

			// 解析响应数据
			if (StringUtils.isBlank(xmlParam)) {
				resultCode = CODE_FAIL;
				errCode = ERR_CODE;
				errCodeDes = "微信查询退款接口返回参数为空！";
			    //status = Constants.STATUS.PAYED_FAILED;
				logger.error(errCodeDes);
				return;
			}
			Map<String, String> mapParam = XMLUtil.readStringXmlOut(xmlParam);
			// 验证响应数据签名
			verifiySign(mapParam, weixin_key);

			// 分析响应数据
			String returnCode = mapParam.get("return_code");// SUCCESS/FAIL
															// 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
			if (CODE_FAIL.equals(returnCode)) {
				resultCode = CODE_FAIL;
				errCode = ERR_CODE;
				errCodeDes = mapParam.get("return_msg");
				//status = Constants.STATUS.PAYED_FAILED;
				logger.error(errCodeDes);
				return;
			}

			String result_code = mapParam.get("result_code");
			if (CODE_FAIL.equals(result_code)) {
				resultCode = CODE_FAIL;
				errCode = mapParam.get("err_code");
				errCodeDes = mapParam.get("err_code_des");
				//status = Constants.STATUS.PAYED_FAILED;
				logger.error(errCodeDes);
				return;
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			resultCode = CODE_FAIL;
			errCode = ERR_CODE;
			errCodeDes = "查询请求失败：" + e.getMessage();
			logger.error(errCodeDes);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private boolean verifiySign(Map<String, String> mapParam, String weixin_key) {
		boolean flag = true;
		String signAfter = sign(mapParam, weixin_key);

		if (!signAfter.equals(mapParam.get("sign"))) {
			flag = false;
		}
		return flag;
	}

	private String sign(Map<String, String> mapParam, String weixin_key) {
		// key排序
		String[] keyArr = new String[mapParam.size()];
		int i = 0;
		for (Iterator iter = mapParam.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			keyArr[i] = name;
			i++;
		}
		Arrays.sort(keyArr);

		// 拼接加密串
		StringBuffer signBefore = new StringBuffer();
		for (int j = 0; j < keyArr.length; j++) {
			if (StringUtils.isNotBlank(mapParam.get(keyArr[j]))
					&& !"sign".equals(keyArr[j])) {
				if (j > 0) {
					signBefore.append("&");
				}
				signBefore.append(keyArr[j]).append("=")
						.append(mapParam.get(keyArr[j]));
			}
		}

		// 验证签名
		signBefore.append("&key=").append(weixin_key);
		logger.info("签名前=" + signBefore.toString());
		String signAfter = MD5.sign(signBefore.toString(), "", ENCODE_TYPE)
				.toUpperCase();
		logger.info("签名后=" + signAfter);
		return signAfter;
	}
	
	/**
	 * 解决有时生成的签名长度为31位
	 */
	public Map<String, Object> getSignStr(Map<String, Object> params,String weixin_api_secret){
		int num = 0;
		String sign = "";
		//为了避免有时生成的签名长度为31位
		while(sign.length() != 32){
			if(num > 100){
				break;
			}
			params.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));
			String asciiString = StringUtil.linkString(params, "&");
			//拼接asciiString和商户密钥
			String key = weixin_api_secret;
			asciiString +="&key="+key;
			//md5编码并转成大写
			sign=SecurityUtils.getMD5(asciiString).toUpperCase();
			num ++;
		}
		params.put("sign", sign);
		return params;
	}
}
