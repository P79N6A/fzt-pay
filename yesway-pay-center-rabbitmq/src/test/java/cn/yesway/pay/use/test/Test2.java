
package cn.yesway.pay.use.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;

import cn.yesway.utils.MessageHeader;
import cn.yesway.utils.RSAUtils;
import cn.yesway.utils.SHADigestUtils;
import cn.yesway.utils.SecurityUtil_AES;
import cn.yesway.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 请求数据的加解密方法测试
 * @author y
 *  2017年12月18日下午1:50:49
 *  Test2
 */
public class Test2 {

	
	public static void main(String[] args) {
		String yeswayPublicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEA8OzyfCVrTFHvtKxfSJDZknQufZ6DQNU6FbFfHNpEjOTIUJ96YTOWLeHweDyiidDFfb/ey3BMAZokOKvRzwFLdcxKueX21YVcRftKDXD7Q94e0Lp16a5nnGyh+zPlF7QrM925DCSYk9GijjgevGrh9ZMvbJEdeNhhbx1iuWRzQIDAQAB";
		String jsonMessage ="{\"sdata\":{\"orderid\":\"acuraacura0010020171215175837615278\",\"payToolType\":1,\"out_trade_no\":\"acuraacura0010020171215175827087362\",\"trade_no\":\"2017121521001004960287864373\",\"payStatus\":\"1\",\"paidStartTime\":\"2017-12-15 17:59:10\",\"paymentChannel\":\"PCREDIT\",\"amount\":\"0.01\"},\"messageheader\":{\"oemid\":\"acura\",\"mchid\":\"acura001\",\"resultcode\":0,\"transactionid\":\"20171215175911\",\"version\":\"1.0\",\"createtime\":\"20171215175911\"}}";
        JSONObject obj = JSONObject.parseObject(jsonMessage);
        
        JSONObject msgheader = obj.getJSONObject("messageheader");
		String oemId = msgheader.getString("oemid");
		String mchId = msgheader.getString("mchid");
		int resultcode = msgheader.getInteger("resultcode");
		JSONObject sdata = (JSONObject)obj.get("sdata");
		// 1 所有请求参数按参数名做字典序升序排列，将排序好的请求参数格式化成“参数名称”“参数值”的形式
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderid", sdata.getString("orderid"));
		params.put("payToolType", sdata.getInteger("payToolType")+"");
		params.put("out_trade_no", sdata.getString("out_trade_no"));
		params.put("trade_no", sdata.getString("trade_no"));
		params.put("payStatus", sdata.getString("payStatus"));
		params.put("paidStartTime", sdata.getString("paidStartTime"));
		params.put("paidEndTime", sdata.getString("paidEndTime"));
		params.put("paymentChannel", sdata.getString("paymentChannel"));
		params.put("amount",  sdata.getString("amount"));
        
		String data = SecurityUtils.createLinkString(params);
		System.out.println("生成摘要的原始字符串：" + data);
		String sign = null;
		String sData = null;
		try {
			// 2 发送方（S）将待发送明文数据（Data）进行哈希算法（SHA1）得到数字摘要（SDigest）
			String sDigest = SHADigestUtils.encryptSHA(data);
			com.alibaba.fastjson.JSONObject stringA = new com.alibaba.fastjson.JSONObject();
			stringA.put("mchid", mchId);
			stringA.put("oemid", oemId);
			stringA.put("sdigest", sDigest);
			// 4 使用RSA算法和接收方（R）公钥（RPublicKey）对签名明文（StringA）进行加密，得到数字签名（Sign）
			sign = RSAUtils.encryptByPublicKey(stringA.toJSONString(),yeswayPublicKey);
			// 5 将使用AES128算法和对称密钥（SecretKey）加密明文数据（params），得到数据密文（SData）
			sData = com.alibaba.fastjson.JSONObject.toJSONString(params);
			String aesKey="1234567890123456";
			sData = SecurityUtil_AES.encrypt(sData, aesKey);
		} catch (Exception e) {
			System.out.println("支付中心回调请求第三方接口请求参数加密失败：" + e.getMessage());
		}
		// 6 合并数据密文（SData）和数字签（Sign），得到待发送密文（SecretData）
		JSONObject secretData = new JSONObject();
		secretData.put("sdata", sData);
		secretData.put("sign", sign);
		// 7 将MessageHeader合并到发送密文（SecretData）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String transactionid = sdf.format(new Date()) + RandomUtils.nextInt();
		MessageHeader messageheader = new MessageHeader();
		messageheader.setOemid(oemId);
		messageheader.setMchid(mchId);
		messageheader.setResultcode(resultcode);
		messageheader.setVersion("1.0");
		messageheader.setCreatetime(new Date());
		messageheader.setTransactionid(transactionid);
		secretData.put("messageheader", messageheader);
		System.out.println("生成输出请求数据" +secretData.toString());
	}

}
