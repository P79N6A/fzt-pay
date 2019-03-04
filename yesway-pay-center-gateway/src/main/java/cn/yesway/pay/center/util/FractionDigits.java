package cn.yesway.pay.center.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 保留小数位
 *
 */
public class FractionDigits {
	/**保留小数位
	 * @param d 数字
	 * @param maximumFraction 小数位数
	 * @param mode 舍入模式：<br/>
	 * 			false 不四舍五入<br/>
	 * 			true 四舍五入<br/>
	 * @return double
	 */
	public static double fractionDigits(double d,int maximumFraction,boolean mode){
		BigDecimal bg = new BigDecimal(d).setScale(maximumFraction, mode?RoundingMode.UP:RoundingMode.DOWN);	  
        return bg.doubleValue();
	}
	
	public static void main(String[] args) {
		String s = "123456789.111";
		double d1 = Double.parseDouble(s);
//		BigDecimal bd = new BigDecimal(d1);
//		System.out.println(bd.toString());
		
//		System.out.println(FractionDigits.fractionDigits(888888.1111, 2, true)*100);
		
		double d = FractionDigits.fractionDigits(d1*10, 0, false);
		System.out.println(d);
		BigDecimal bd2 = new BigDecimal(d);
		System.out.println(bd2.toString());
		
//		double d = FractionDigits.fractionDigits(d1, 2, false)*1000;
////		System.out.println(d);
//		BigDecimal bd2 = new BigDecimal(d);
//		System.out.println(bd2.toString());
		
//		System.out.println(Integer.parseInt(new BigDecimal(d).toString()));
		
	}
}
