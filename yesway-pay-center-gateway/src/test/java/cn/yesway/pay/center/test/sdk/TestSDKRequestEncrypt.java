package cn.yesway.pay.center.test.sdk;

import java.math.BigInteger;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import scala.util.parsing.json.JSONArray;
import cn.yesway.pay.center.test.alipay.Contants;
import cn.yesway.pay.center.util.AESSecurityUtils;
import cn.yesway.pay.center.util.RSAUtils;
//import cn.yesway.pay.center.util.AESSecurityUtilsTest;
import cn.yesway.pay.center.util.SHADigestUtils;
import cn.yesway.pay.center.util.SecurityUtil_AES;
import cn.yesway.pay.center.util.SecurityUtils;
import cn.yesway.pay.center.util.rsa.PublicRSA;
//import cn.yesway.pay.center.util.rsa.PublicRSA;
import cn.yesway.pay.center.vo.MessageHeader;

import com.alibaba.fastjson.JSONObject;
/*
 * 说明：因为测试类这块调用的公钥私钥加解密的
 */
/**
 * @author 生成文档里需要的请求数据sign和sdata数据在这儿 2017年3月10日下午2:27:41 TestSDKRequestEncrypt
 */
public class TestSDKRequestEncrypt {

	public static void main(String[] args) throws Exception {

	   alipayRequestUnifiedOrder();
//        weixinRequestUnifiedOrder();
        //微信支付宝退款
//		weixinRequestRefundOrder();
		 // encryptOrderParams();
		//统一下单
	   //alipayRequestUnifiedOrder2();
//		test();
//	   refundQuery();
	}
    
	public static void alipayRequestUnifiedOrder() throws Exception {
		System.out.println(">>>生成支付宝SDK请求数据。。。");
		// 1 所有请求参数按参数名做字典序升序排列，将排序好的请求参数格式化成“参数名称”“参数值”的形式
		Map<String, String> params = new HashMap<String, String>();
		/*params.put("out_trade_no",(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))+ RandomUtils.nextInt(5));
		params.put("total_amount", "100");
		params.put("pay_tool_type", "1");
		params.put("subject", "购买手机");
		params.put("body", "购买手机");		
		//params.put(	"sub_orders",	"[{\"sub_order_no\":\"20161232132132\",\"partner_no\":\"123312112\",\"partner_role_id\":\"2\",\"quantity\":1,\"price\":528800,\"goods_category\":\"123456\",\"body\":\"苹果手机\"},{\"items\":\"iphone6s_32G\",\"wxpay_goods_id\":\"1002\",\"goods_name\":\"iPhone6s 32G\",\"quantity\":1,\"price\":608800,\"goods_category\":\"123789\",\"body\":\"苹果手机\",items[{\"item_id\": \"12523\",\"settle_type\":0}],\"amount\":200}]");
		//params.put("sub_orders","[{\"sub_order_no\": \"20161232132132\",\"partner_no\": \"100001\",\"partner_role_id\": 2,\"items\": [{\"item_id\": \"12523\",\"quantity\": 2,\"unit_price\": 100,\"service_charge\": 5,\"settle_type\": 0}],\"amount\":20}]");
		params.put("time_start", "20170802111401");
		params.put("time_expire","20170802121401");
		params.put("spbill_create_ip", "10.1.2.128");
		params.put("region", "00");
		params.put("tariff", "0.85");
		params.put("sub_orders","[{\"sub_order_no\": \"001\",\"partner_no\": \"100001\",\"partner_role_id\": 2,\"items\": [{\"item_id\": \"201\",\"quantity\": 1,\"unit_price\": 25,\"service_charge\": 25,\"settle_type\": 0}],\"amount\":50},{\"sub_order_no\": \"004\",\"partner_no\": \"100001\",\"partner_role_id\": 2,\"items\": [{\"item_id\": \"2104\",\"quantity\": 1,\"unit_price\": 25,\"service_charge\": 25,\"settle_type\": 0}],\"amount\":50}]");
*/
		/*String sdata="{\"out_trade_no\": \"BIO17080900004291\",\"sub_orders\":[{\"sub_order_no\":\"BIO1708090000430\",\"partner_no\":\"P000002\",\"partner_role_id\":3,"
    			+ "\"items\":[{\"item_id\":\"764387\",\"item_type\":\"001贵宾室\",\"quantity\":1,\"unit_price\":100.00,\"service_charge\":22.00,\"settle_type\":0}],"
    			+ "\"amount\":122.00,\"settle_level\":1\"settle_mode\":1}],\"coupons\": [{\"coupon_category_code\": \"3212****3232\",\"coupon_code\": \"3212****3232\","
    					+ "\"coupon_amount\": 100.0,\"coupon_partner_no\": \"P000002\"}]}";*/
		
		/*
		params.put("out_trade_no",(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))+ RandomUtils.nextInt(5));
		params.put("total_amount", "98");
		params.put("pay_tool_type", "1");
		params.put("subject", "违章代缴");
		params.put("body", "违章代缴");		
		params.put("sub_orders","[{\"sub_order_no\":\"BIO1708090000430\",\"partner_no\":\"P000002\",\"partner_role_id\":3,"
    			+ "\"items\":[{\"item_id\":\"764387\",\"item_type\":\"001贵宾室\",\"quantity\":1,\"unit_price\":100.00,\"service_charge\":22.00,\"settle_type\":0}],"
    			+ "\"amount\":122.00,\"settle_level\":1\"settle_mode\":1}]}]");
		params.put("trade_type", "APP");		
		params.put("time_start", "20170929032906");
		params.put("time_expire","20170929033506");
		params.put("spbill_create_ip", "1.1.1.1");
		params.put("coupons", "[{\"coupon_category_code\": \"3212****3232\",\"coupon_code\": \"3212****3232\","
    					+ "\"coupon_amount\": 100.0,\"coupon_partner_no\": \"P000002\"}]");
*/
		
		//支付状态回调请求数据
		params.put("out_trade_no",(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))+ RandomUtils.nextInt(5));
		params.put("sub_orders","[{\"sub_order_no\":\"BIO1708090000430\",\"partner_no\":\"P000002\",\"partner_role_id\":3,"
    			+ "\"items\":[{\"item_id\":\"764387\",\"item_type\":\"001\",\"quantity\":1,\"unit_price\":100.00,\"service_charge\":22.00,\"settle_type\":0}],"
    			+ "\"amount\":122.00,\"settle_level\":0,\"settle_mode\":1}]");
		params.put("coupons", "[{\"coupon_category_code\": \"3212****3232\",\"coupon_code\": \"3212****3232\","
				+ "\"coupon_amount\": 100.0,\"coupon_partner_no\": \"P000002\"}]");
		
		
		String data = SecurityUtils.createLinkString(params);
		System.out.println("生成摘要的原始字符串：\n" + data);
        //String data="body购买手机out_trade_no2016122612352912345pay_tool_type2region00spbill_create_ip14.23.150.211sub_orders[{\"sub_order_no\":\"20161232132132\",\"items\":[{\"item_id\":\"12523\",\"quantity\":2,\"unit_price\":100,\"service_charge\":5,\"partner_no\":\"123312112\",\"partner_role_id\":2,\"settle_type\":0}],\"amount\":200}]subject购买手机tariff0.85time_expire20170427091010total_amount205trade_typeAPP";
		// 2 发送方（S）将待发送明文数据（Data）进行哈希算法（SHA1）得到数字摘要（SDigest）
		String sDigest = SHADigestUtils.encryptSHA(data);

		// 3 合并数字摘要（SDigest）、OEM编号、商户编号
		String oemid = "1fe9be57-2edc-45";
		String mchid = "3a0c76b4-1f6a-41";
		//String oemid = "SDFDSF346HJKJKDSF3GTKLPMD";
		//String mchid = "1422167202";
		JSONObject stringA = new JSONObject();
		stringA.put("mchid", mchid);
		stringA.put("oemid", oemid);
		stringA.put("sdigest", sDigest);

		// 4 使用RSA算法和接收方（R）公钥（RPublicKey）对签名明文（StringA）进行加密，得到数字签名（Sign）
		String sign = RSAUtils.encryptByPublicKey(stringA.toJSONString());
		
		// 5 将使用AES128算法和对称密钥（SecretKey）加密明文数据（params），得到数据密文（SData）
		String sData = JSONObject.toJSONString(params);
		System.out.println("发送的明文原始字符串：\n" + sData);

		String aesSecretKey = "1234567890123456";
		
		/*String sign="dC2kzMhwRzIwBcNgJmTC0UMk5MkffEVKhWwn13N8PPjfreynQa3yCz95c20PGfoHGEpT8PZaOBf84sW2r"
				+ "YPMwfkEqI8+qwfu6DnUGtLCzbKEQypH/XL6cLh8T0/d4Ra8PpJYTbvB11mQCR6BKANLC793LL/FwvW3fc99CrjtF/M=";
		sign =  RSAUtils.decryptByPrivateKey(sign);
		JSONObject signJSON = JSONObject.parseObject(sign);
        String oemids = signJSON.getString("oemid");
        String sdigest = signJSON.getString("sdigest");*/
		
		
		sData =SecurityUtil_AES.encrypt(sData, aesSecretKey);
		// 6 合并数据密文（SData）和数字签（Sign），得到待发送密文（SecretData）
		JSONObject secretData = new JSONObject();
		secretData.put("sdata", sData);
		secretData.put("sign", sign);

		// 7 将MessageHeader合并到发送密文（SecretData）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String transactionid = sdf.format(new Date()) + RandomUtils.nextInt();
		MessageHeader messageheader = new MessageHeader();
		messageheader.setOemid(oemid);
		messageheader.setMchid(mchid);
		messageheader.setTransactionid(transactionid);
		messageheader.setVersion("1.0");
		messageheader.setCreatetime(new Date());
		//messageheader.setTerminal_type("android");
		secretData.put("messageheader", messageheader);

		// 8 将密文（SecretData）发送到接收方（R）
		System.out.println("发送的密文（SecretData）：\n" + secretData.toJSONString());
		
	}

	public static void weixinRequestUnifiedOrder() throws Exception {
		System.out.println(">>>生成微信SDK请求数据。。。");
		// 1 所有请求参数按参数名做字典序升序排列，将排序好的请求参数格式化成“参数名称”“参数值”的形式
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", "wxcc87001da05f04a0");
		params.put("mch_id", "1422167202");//1422167202
		params.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));
		params.put("body", "购买手机");
		params.put("detail","{\"goods_detail\":[{\"goods_id\":\"iphone6s_16G\",\"wxpay_goods_id\":\"1001\",\"goods_name\":\"iPhone6s 16G\",\"quantity\":1,\"price\":528800,\"goods_category\":\"123456\",\"body\":\"苹果手机\"},{\"goods_id\":\"iphone6s_32G\",\"wxpay_goods_id\":\"1002\",\"goods_name\":\"iPhone6s 32G\",\"quantity\":1,\"price\":608800,\"goods_category\":\"123789\",\"body\":\"苹果手机\"}]}");
		params.put("total_amount", "0.04");
		params.put("spbill_create_ip", "14.23.150.211");
		params.put("trade_type", "APP");
		params.put("out_trade_no",(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))+ RandomUtils.nextInt(5));
		params.put("pay_tool_type", "2");
		params.put("sign_type", Contants.SIGN_TYPE);
		params.put("time_start",  "20170426121500");
		params.put("time_expire", "20170426121800");
		params.put("spbill_create_ip", "10.1.2.128");
		params.put("region", "00");
		params.put("tariff", "0.85");
		//params.put("sub_orders","[{\"sub_order_no\": \"123\",\"partner_no\": \"100001\",\"partner_role_id\": 2,\"items\": [{\"item_id\": \"010\",\"quantity\": 2,\"unit_price\": 50,\"service_charge\": 50,\"settle_type\": 0}],\"amount\":100}]");
		//params.put("sub_orders","[{\"sub_order_no\": \"001\",\"partner_no\": \"100001\",\"partner_role_id\": 2,\"items\": [{\"item_id\": \"201\",\"quantity\": 1,\"unit_price\": 25,\"service_charge\": 25,\"settle_type\": 0},{\"item_id\": \"204\",\"quantity\": 2,\"unit_price\": 25,\"service_charge\": 25,\"settle_type\": 1}],\"amount\":100},{\"sub_order_no\": \"004\",\"partner_no\": \"100001\",\"partner_role_id\": 2,\"items\": [{\"item_id\": \"2104\",\"quantity\": 2,\"unit_price\": 25,\"service_charge\": 25,\"settle_type\": 0},{\"item_id\": \"203\",\"quantity\": 3,\"unit_price\": 25,\"service_charge\": 25,\"settle_type\": 1}],\"amount\":100}]");
		params.put("sub_orders","[{\"sub_order_no\": \"001\",\"partner_no\": \"100001\",\"partner_order_no\":\"123456789\",\"partner_role_id\": 2,\"items\": [{\"item_id\": \"201\",\"item_name\":\"乱停车辆,扣分了\",\"quantity\": 1,\"unit_price\": 0.01,\"service_charge\": 0.01,\"settle_type\": 0}],\"amount\":0.02},{\"sub_order_no\": \"004\",\"partner_no\": \"100001\",\"partner_role_id\": 2,\"items\": [{\"item_id\": \"2104\",\"quantity\": 1,\"unit_price\": 0.01,\"service_charge\": 0.01,\"settle_type\": 0}],\"amount\":0.02}]");

		
		String data = SecurityUtils.createLinkString(params);
		System.out.println("生成摘要的原始字符串：\n" + data);

		// 2 发送方（S）将待发送明文数据（Data）进行哈希算法（SHA1）得到数字摘要（SDigest）
		String sDigest = SHADigestUtils.encryptSHA(data);

		// 3 合并数字摘要（SDigest）、OEM编号、商户编号
		String oemid = "1000";
		String mchid = "100001";
		JSONObject stringA = new JSONObject();
		stringA.put("sdigest", sDigest);
		stringA.put("oemid", oemid);
		stringA.put("mchid", mchid);

		// 4 使用RSA算法和接收方（R）公钥（RPublicKey）对签名明文（StringA）进行加密，得到数字签名（Sign）
		/*PublicRSA publicRSA = PublicRSA.getInstance();
		String sign = publicRSA.encryptByPublicKey(stringA.toJSONString());*/
        
		String sign = RSAUtils.encryptByPublicKey(stringA.toJSONString());
		// 5 将使用AES128算法和对称密钥（SecretKey）加密明文数据（params），得到数据密文（SData）
		String sData = JSONObject.toJSONString(params);
		System.out.println("发送的明文原始字符串：\n" + sData);

		String aesSecretKey = "1234567890123456";
		sData =SecurityUtil_AES.encrypt(sData, aesSecretKey);
		
		// 6 合并数据密文（SData）和数字签（Sign），得到待发送密文（SecretData）
		JSONObject secretData = new JSONObject();
		secretData.put("sdata", sData);
		secretData.put("sign", sign);

		// 7 将MessageHeader合并到发送密文（SecretData）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String transactionid = sdf.format(new Date()) + RandomUtils.nextInt();
		MessageHeader messageheader = new MessageHeader();
		messageheader.setOemid(oemid);
		messageheader.setMchid(mchid);
		messageheader.setTransactionid(transactionid);
		messageheader.setVersion("1.0");
		messageheader.setCreatetime(new Date());
		messageheader.setTerminal_type("ios");
		secretData.put("messageheader", messageheader);

		// 8 将密文（SecretData）发送到接收方（R）
		System.out.println("发送的密文（SecretData）：\n" + secretData.toJSONString());
	}

	/**
	 * 微信、支付宝退款
	 * 
	 * @throws Exception
	 */
	public static void weixinRequestRefundOrder() throws Exception {
		System.out.println(">>>生成微信、支付宝退款SDK请求数据。。。");
		
		
	    UUID  aa = UUID.randomUUID();//I4xTrylnJsnjqbKN
		
		Map<String, String> params = new HashMap<String, String>();
		 String mchid = "123312112";
		// 微信
		params.put("appid", "wxcc87001da05f04a0");
		params.put("mch_id", "1422167202");//1422167202
		params.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));
		params.put("out_refund_no", (DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))+ RandomUtils.nextInt(5));
		params.put("out_trade_no", "BIO17110900023391");
		//params.put("refund_fee", "0.01");
		params.put("total_fee", "70");
		params.put("total_refund_amount", "70");
		params.put("refund_batch_no", (DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))+ RandomUtils.nextInt(5));
		params.put("transaction_id", "");
//		params.put("pay_tool_type", "2");
		params.put("refund_account", "");
		//params.put("payOrderNo", "BIO1711070002159");
//		params.put("payRefundNo", "20171101165121152237");
		params.put("sub_orders","[{\"sub_order_no\": \"BIO1711090002340\",\"refund_reason\":\"reason\",\"refund_amount\": 70,\"items\": ["
	    		+ "{ \"item_id\": \"1710310103495\",\"quantity\": 1,\"refund_amount\": 70,\"refund_reason\":\"reason\"} ] }]");

		
//		,{ \"item_id\": \"202\",\"quantity\": 1,\"refund_amount\": 0.01,\"refund_reason\":\"reason\"}
		 
		 
		 // 支付宝
		 /*
		  params.put("appid", "2016113003624708"); //检证：2016113003624708///号口：2016120103687613
		  params.put("mch_id", mchid); 
		  params.put("out_trade_no", "BIO17110900023411");
		  params.put("out_refund_no", (DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))+ RandomUtils.nextInt(5));
		  params.put("total_refund_amount", "70");
		  params.put("refund_batch_no", (DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))+ RandomUtils.nextInt(5)); 
		  params.put("refund_reason", "退款原因");
//		  params.put("pay_tool_type", "1");//1:支付宝，2：微信
		  params.put("sign_type", Contants.SIGN_TYPE);//ge101102//ge10090021
		  params.put("sub_orders","[{\"sub_order_no\": \"BIO1711090002342\",\"refund_reason\":\"reason\",\"refund_amount\": 70,\"items\": ["
	    		+ "{ \"item_id\": \"1711030105285\",\"quantity\": 1,\"refund_amount\": 70,\"refund_reason\":\"reason\"} ] }]");
		*/
      
		String data = SecurityUtils.createLinkString(params);
		System.out.println("1、生成摘要的原始字符串：\n" + data);
		// 2 发送方（S）将待发送明文数据（Data）进行哈希算法（SHA1）得到数字摘要（SDigest）
		String sDigest = SHADigestUtils.encryptSHA(data);

		// 3 合并数字摘要（SDigest）、OEM编号、商户编号
		String oemid = "1000";
		//String mchid = "100001";
		JSONObject stringA = new JSONObject();
		stringA.put("sdigest", sDigest);
		stringA.put("oemid", oemid);
		stringA.put("mchid", mchid);

		String sign = RSAUtils.encryptByPublicKey(stringA.toJSONString());
		// 5 将使用AES128算法和对称密钥（SecretKey）加密明文数据（params），得到数据密文（SData）
		String sData = JSONObject.toJSONString(params);
		System.out.println("2、发送的明文原始字符串：\n" + sData);

		String aesSecretKey = "1234567890123456";
		sData =SecurityUtil_AES.encrypt(sData, aesSecretKey);
		//System.out.println("4、加密数据sdata"+sData);
		// 6 合并数据密文（SData）和数字签（Sign），得到待发送密文（SecretData）
		JSONObject secretData = new JSONObject();
		secretData.put("sdata", sData);
		secretData.put("sign", sign);

		// 7 将MessageHeader合并到发送密文（SecretData）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String transactionid = sdf.format(new Date()) + RandomUtils.nextInt();
		MessageHeader messageheader = new MessageHeader();
		messageheader.setOemid(oemid);
		messageheader.setMchid(mchid);
		messageheader.setTransactionid(transactionid);
		messageheader.setVersion("1.0");
		messageheader.setCreatetime(new Date());
		secretData.put("messageheader", messageheader);

		// 8 将密文（SecretData）发送到接收方（R）
		System.out.println("3、发送的密文（SecretData）：\n" + secretData.toJSONString());
		System.out.println("3、发送的密文（SecretData）：\n" + secretData.toJSONString());
		
		/*JSONObject refundJson = new JSONObject();
		MessageHeader message = new MessageHeader();=
		message.setReturn_code(0);
		message.setReturn_msg("测试");
		refundJson.put("messageheader", message);
		JSONObject messagehead = JSONObject.parseObject(refundJson.toString()); 
		JSONObject json= messagehead.getJSONObject("messageheader");
		int code1 = json.getIntValue("return_code");
		int code2 =json.getInteger("return_code");
		BigInteger code=json.getBigInteger("return_code");
		if(code2==0){
			System.out.println("");
		}*/
		
		

	}

	/**
	 * 微信退款查询请求方法
	 * 
	 * @throws Exception
	 */
	public static void weixinRequestRefundOrderQuery() throws Exception {
		System.out.println(">>>生成微信退款查询SDK请求数据。。。");
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", "wxcc87001da05f04a0");
		params.put("mch_id", "1422167202");
		params.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));
		params.put("out_refund_no", "201703161453245537");
		params.put("out_trade_no", "10001000010020170601140047613258");
		params.put("transaction_id", "");
		params.put("pay_tool_type", "2");

	}

	/**
	 * 统一下单参数加密
	 * 
	 * @param order
	 * @return
	 * @throws Exception 
	 */
	public static String encryptOrderParams() throws Exception {
		// 1、
		Map<String, String> params = new HashMap<String, String>();
		params.put("out_trade_no",
				(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))
						+ RandomUtils.nextInt(5));
		params.put("total_amount", "0.01");
		params.put("pay_tool_type", "1");
		params.put("subject", "cesih");
		params.put("body", "ceshi测试");
		params.put("spbill_create_ip", "11");
		params.put("goods_detail", "ceshishuju ");

		String data = SecurityUtils.createLinkString(params);
		// /Log.e("zhangke", data);

		// 2、
		String sDigest = SHADigestUtils.encryptSHA(data);

		// 3、
		String oemid = "02864ccf-9745-43c9-a975-1bc182277192";
		String mchid = "6FB0645B-9CC6-4754-A6A8-D251ACA95F0C";
		/*
		 * String oemid = order.getOem_id(); String mchid = order.getMch_id();
		 */
		JSONObject stringA = new JSONObject();
		stringA.put("sdigest", sDigest);
		stringA.put("oemid", oemid);
		stringA.put("mchid", mchid);

		// 4、
		PublicRSA publicRSA = PublicRSA.getInstance();
		String sign = publicRSA.encryptByPublicKey(stringA.toJSONString());

		// 5、
		String sData = JSONObject.toJSONString(params);
		String aesSecretKey = "1234567890123456";
		sData = AESSecurityUtils.AESEncode(aesSecretKey, sData);

		// 6、
		JSONObject secretData = new JSONObject();
		secretData.put("sdata", sData);
		secretData.put("sign", sign);

		// 7、
		/*secretData.put("messageheader",
				HttpRequestBuilder.createHeader(oemid, mchid));*/
		// 7 将MessageHeader合并到发送密文（SecretData）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String transactionid = sdf.format(new Date()) + RandomUtils.nextInt();
		MessageHeader messageheader = new MessageHeader();
		messageheader.setOemid(oemid);
		messageheader.setMchid(mchid);
		messageheader.setTransactionid(transactionid);
		messageheader.setVersion("1.0");
		messageheader.setCreatetime(new Date());
		secretData.put("messageheader", messageheader);
		System.out.println("\n输出"+secretData.toJSONString());
		return secretData.toJSONString();
	}
	//多订单统一下单
	public static void alipayRequestUnifiedOrder2() throws Exception {
		System.out.println(">>>生成支付宝SDK请求数据。。。");
		// 1 所有请求参数按参数名做字典序升序排列，将排序好的请求参数格式化成“参数名称”“参数值”的形式
		Map<String, String> params = new HashMap<String, String>();
		params.put("out_trade_no",(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))+ RandomUtils.nextInt(5));
		params.put("total_amount", "0.01");
		params.put("pay_tool_type", "1");
		params.put("subject", "购买手机");
		params.put("body", "购买手机");		
		params.put("spbill_create_ip", "125.35.75.142");
		params.put("sub_orders", "[{\"sub_order_no\":\"20161232132132\",\"items\":[{\"item_id\":\"12523\",\"quantity\": 2,\"unit_price\": 100,\"service_charge\": 5,\"partner_no\": 123312112,\"partner_role_id\": 2,\"settle_type\": 0}]  },{\"sub_order_no\":\"20161232132131\",\"items\":[{\"item_id\":\"12523\",\"quantity\": 2,\"unit_price\": 100,\"service_charge\": 5,\"partner_no\": 123312112,\"partner_role_id\": 2,\"settle_type\": 0}] }]");
		//params.put(	"sub_orders","[{\"sub_order_no\":\"20161232132132\",\"wxpay_goods_id\":\"1001\",\"goods_name\":\"iPhone6s 16G\",\"quantity\":1,\"price\":528800,\"goods_category\":\"123456\",\"body\":\"苹果手机\"},{\"goods_id\":\"iphone6s_32G\",\"wxpay_goods_id\":\"1002\",\"goods_name\":\"iPhone6s 32G\",\"quantity\":1,\"price\":608800,\"goods_category\":\"123789\",\"body\":\"苹果手机\"}]");
		//params.put("sub_orders","[{\"sub_order_no\":\"20161232132132\",\"items\":[{\"item_id\":\"12523\",\"quantity\": \"2\",\"unit_price\": \"100\",\"service_charge\": \"5\",\"partner_no\": \"123312112\",\"partner_role_id\": \"2\",\"settle_type\": \"0\"}]"}]",);
		params.put("time_start", "20170516111401");
		params.put("time_expire", "20170516121401");
		String data = SecurityUtils.createLinkString(params);
		System.out.println("生成摘要的原始字符串：\n" + data);

		// 2 发送方（S）将待发送明文数据（Data）进行哈希算法（SHA1）得到数字摘要（SDigest）
		String sDigest = SHADigestUtils.encryptSHA(data);
		//String sDigest = "0ce48bb2859f4210ac2e4d9eda1913ea2d3004fa";

		// 3 合并数字摘要（SDigest）、OEM编号、商户编号
		String oemid = "1000";
		String mchid = "100001";
		JSONObject stringA = new JSONObject();
		stringA.put("sdigest", sDigest);
		stringA.put("oemid", oemid);
		stringA.put("mchid", mchid);

		// 4 使用RSA算法和接收方（R）公钥（RPublicKey）对签名明文（StringA）进行加密，得到数字签名（Sign）
		/*PublicRSA publicRSA = PublicRSA.getInstance();
		String sign = publicRSA.encryptByPublicKey(stringA.toJSONString());*/

		String sign = RSAUtils.encryptByPublicKey(stringA.toJSONString());
		// 5 将使用AES128算法和对称密钥（SecretKey）加密明文数据（params），得到数据密文（SData）
		String sData = JSONObject.toJSONString(params);
		System.out.println("发送的明文原始字符串：\n" + sData);

		String aesSecretKey = "1234567890123456";
		/*根据终端 类型，针对Android和ios分别进行处理，加解密处理方式*/
		
		//sData = AESSecurityUtils.AESEncode(aesSecretKey, sData);
		//sData = AESOperator.getInstance().Encrypt(sData,aesSecretKey,aesSecretKey);
		sData =SecurityUtil_AES.encrypt(sData, aesSecretKey);
		// 6 合并数据密文（SData）和数字签（Sign），得到待发送密文（SecretData）
		JSONObject secretData = new JSONObject();
		secretData.put("sdata", sData);
		secretData.put("sign", sign);

		// 7 将MessageHeader合并到发送密文（SecretData）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String transactionid = sdf.format(new Date()) + RandomUtils.nextInt();
		MessageHeader messageheader = new MessageHeader();
		messageheader.setOemid(oemid);
		messageheader.setMchid(mchid);
		messageheader.setTransactionid(transactionid);
		messageheader.setVersion("1.0");
		messageheader.setCreatetime(new Date());
		messageheader.setTerminal_type("android");
		secretData.put("messageheader", messageheader);

		// 8 将密文（SecretData）发送到接收方（R）
		System.out.println("发送的密文（SecretData）：\n" + secretData.toJSONString());
	}
	public static void test() throws Exception {
		
		
		
		System.out.println(">>>生成支付宝SDK请求数据。。。");
		// 1 所有请求参数按参数名做字典序升序排列，将排序好的请求参数格式化成“参数名称”“参数值”的形式
		Map<String, String> params = new HashMap<String, String>();
		params.put("create_time","1503479951895");
		params.put("out_trade_no",(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))+ RandomUtils.nextInt(5));
		params.put("trd_party_pay_bill_no", (DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"))+ RandomUtils.nextInt(5));
		String data = SecurityUtils.createLinkString(params);
		System.out.println("生成摘要的原始字符串：\n" + data);
		// 2 发送方（S）将待发送明文数据（Data）进行哈希算法（SHA1）得到数字摘要（SDigest）
		String sDigest = SHADigestUtils.encryptSHA(data);
		// 3 合并数字摘要（SDigest）、OEM编号、商户编号
		String oemid = "1000";
		String mchid = "100001";
		JSONObject stringA = new JSONObject();
		stringA.put("sdigest", sDigest);
		stringA.put("oemid", oemid);
		stringA.put("mchid", mchid);

		
		// 4 使用RSA算法和接收方（R）公钥对签名明文（StringA）进行加密，得到数字签名（Sign）
		String sign = RSAUtils.encryptByPublicKey(stringA.toJSONString());
		// 5 将使用AES128算法和对称密钥（SecretKey）加密明文数据（params），得到数据密文（SData）
		String sData = JSONObject.toJSONString(params);
		System.out.println("发送的明文原始字符串：\n" + sData);
		String aesSecretKey = "1234567890123456";
		sData =SecurityUtil_AES.encrypt(sData, aesSecretKey);
		// 6 合并数据密文（SData）和数字签（Sign），得到待发送密文（SecretData）
		JSONObject secretData = new JSONObject();
		
        
		// 7 将MessageHeader合并到发送密文（SecretData）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String transactionid = sdf.format(new Date()) + RandomUtils.nextInt();
		MessageHeader messageheader = new MessageHeader();
		messageheader.setOemid(oemid);
		messageheader.setMchid(mchid);
		messageheader.setTransactionid(transactionid);
		messageheader.setVersion("1.0");
		messageheader.setCreatetime(new Date());
		secretData.put("messageheader", messageheader);
		secretData.put("sdata", sData);
		secretData.put("sign", sign);
		// 8 将密文（SecretData）发送到接收方（R）
		System.out.println("发送的密文（SecretData）：\n" + secretData.toJSONString());
		
	}
	
	/**
	 * 退款查询
	 * @throws Exception
	 */
	public static void refundQuery() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		//支付宝
		params.put("trade_no","2017091121001004690295007230");//2017090921001004690284002801
		params.put("out_trade_no","ge0911001");
		params.put("out_request_no", "20170911132448189468");//20170909133055252912
		params.put("pay_Order_No", "20170911130819131887");//20170909132859184844
//		params.put("pay_tool_type", "1");//20170909132859184844
		
		//微信
		/*params.put("refund_id","1503479951895");
		params.put("out_refund_no", "");
		params.put("transaction_id","");
		params.put("out_trade_no","");*/
		
		String sData = JSONObject.toJSONString(params);
		
		String oemid = "1000";
		String mchid = "100001";
		JSONObject secretData = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String transactionid = sdf.format(new Date()) + RandomUtils.nextInt();
		MessageHeader messageheader = new MessageHeader();
		messageheader.setOemid(oemid);
		messageheader.setMchid(mchid);
		messageheader.setTransactionid(transactionid);
		messageheader.setVersion("1.0");
		messageheader.setCreatetime(new Date());
		secretData.put("messageheader", messageheader);
		secretData.put("sdata", sData);
		System.out.println("退款查询生成请求数据:secretData:"+secretData.toString());
	}
}
