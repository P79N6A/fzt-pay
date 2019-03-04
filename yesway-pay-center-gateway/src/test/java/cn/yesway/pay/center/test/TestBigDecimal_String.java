package cn.yesway.pay.center.test;

import java.math.BigDecimal;

import cn.yesway.pay.center.util.FractionDigits;

public class TestBigDecimal_String {  
    public static void main(String[] args) {  
    	double d = FractionDigits.fractionDigits(888888.1151, 2, false)*100;
        BigDecimal bigDecimal = new BigDecimal(d);  
        String result = bigDecimal.toString();  
        System.out.println(result);  
          
    }  
} 