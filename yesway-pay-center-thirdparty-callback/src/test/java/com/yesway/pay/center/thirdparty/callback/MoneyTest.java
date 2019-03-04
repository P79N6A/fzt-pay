package com.yesway.pay.center.thirdparty.callback;



public class MoneyTest {

	public static void main(String[] args) {
		//微信的总金额要由分转为元，保留两位小数
//		double d1 =  Double.parseDouble(sdata.getString("total_amount"));
//		double d2 = FractionDigits.fractionDigits(d1*100, 0, false);
//		BigDecimal totalAmount = new BigDecimal(d2);
//		params.put("total_fee", totalAmount.toString());
		
//		float a = 0.1F;
		int a = 1;
		double b = a/100D;
		System.out.println(b);
//		double c = FractionDigits.fractionDigits(b*100, 2, false);
//		System.out.println(c);
	}

}
