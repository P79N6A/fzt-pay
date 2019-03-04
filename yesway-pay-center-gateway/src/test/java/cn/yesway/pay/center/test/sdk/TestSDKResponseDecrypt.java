package cn.yesway.pay.center.test.sdk;

import net.sf.json.JSONObject;
import cn.yesway.pay.center.util.SecurityUtil_AES;


public class TestSDKResponseDecrypt {

	public static void main(String[] args) throws Exception {
		String AESSECRETERY ="1234567890123456";
		String requestBody = "{\"sign\":\"YdPVXJr8EylgTudOv5/kMCYNeB41+rmDyaUaBXOh2h8ViK0jQSg025zuHF5hzmFujxbEVjDao+G5\neifcbya4mBcXbC/nsFep0ImgauUq+eQUAyRcrA0Yl2yz5avy4a6tkt00BoFBISIkrqY7YhFzIGN0\nGbkZnS6VoBteGix0e9w=CgLzL12Rs0qlI0IvQziQl5EVeuELFUqOa3a/Ot3+V/JvlZL1hibfkPurxB0JFIstvx+ycIlEymjH\nqQji1vPxpX6fYqYHNaR+yhgeCdROOAs/z6KOTdEY6geyim8HkUNOz7/u/lC9PL12dN6p+I5uqsKI\nlYbpd63BQcdQTPazQyA=\",\"messageheader\":{\"createtime\":1491011562978,\"mchid\":\"6FB0645B-9CC6-4754-A6A8-D251ACA95F0C\",\"oemid\":\"02864ccf-9745-43c9-a975-1bc182277192\",\"resultcode\":0,\"transactionid\":\"20170401095242607389242\",\"version\":\"1.0\"}}";
		String sdata2 = "{\"messageheader\":{\"createtime\":\"20171101202141\",\"mchid\":\"100001\",\"oemid\":\"1000\",\"transactionid\":\"20171101202141573\",\"version\":\"1.0\"},\"payOrderNoSign\":\"FRiRwCjO5621NudSuV+EjC/D0BDyGs6caDGORVHUrcOJX/e2hHLkaE+wbdIUXzmz0z468gJqbQuX2GiXWBwGhbV5G0aiKKAtXCQ8erJgRuOnAHBIG4XeJvTwbXC/lVh+RKyEY2ipgnt6QuxA7LW11K+f5SCs9z7AaHMIyvs3LO8=\",\"sdata\":\"aEBcYlCGeSY0FqiZdt2ygn78O9INZmBf3Q1MUTPMsahouZPUSky0CPGIQbGW O+AbIpHZWCSgJMIR9ff3TRQK3mBR1dQYA4M3Rs1GjWmnjRJ39vmE3sXEI5cT A52N0YgQfyFkASy7wXBUx6EWDodORDe2l4A2cs1Ha4RHW+v5FOvmzl/RQBYH AEG907qEq21dRTtoJVemJtvaPLInAxj0Pp07+lN94FDDOH2Ug5Fxw1uJEY6t mNVa8F81ezazf+zVM+NX0CFHmYc3Ft3Vc6hqHzsDeF6bISZXhMChRkHL/+yK 9GbzOvvnwpshe6oPgaRRjcQge2uXQ5cFmvoGlOjYEi7u+juFgdaoi6NT32KP OZpOPTeD67BhoR5PpnsHlHc62zegn+gni8i6+Wn6mHCJLFII0sTKWqIxAIAK CDkezRCUPXQGjwNCT0tFatRmPjvJ6nYiS1BxJszFvHPJEzQIZJk8m49fS+fC J8Zm1Y5WkQ9YuF4SinCzc35F1Z7qtltVJRLA0npYRa+W7UOjEoIkOjEQqASb dCkddqxLfCgb792RohlfFkqPAo/kB0vffVI1nXHiMXRCZQoDEugdncqHb8St +QBi1k+moo0P/sf1zBQlMWowCozEg72wwIGaYmbZbWqBUqPwa8FNBngPLT7W BipDT64p+fE+RRU2SPv4O0EkdvjRAzzN2xy/whzt3OBYaipnuSUvDwoYW/Cg dn6xek/V0jZ5RvOJY7iVAXsL4QxPhSljSZxrQAM/0V5E6d5sXkgbG03mtdJe bR9ZQ22aB+xPnYxci6TDSrrksyM/Z9HJlwowrznKI8SOv09UfQxwBhrC3eK1 RCAmvSNbIogBRruORxKyMmh7Ul8N2p11gl1wGy6yNoeFXR828emoz80bkDpR 543IWMM4gAcvBlBg4p2M/Ngb4F/PJ8esLg6ZhqD2nwobRBdFLYoa0n8k/DG7 QsNF1DzWgAGBCSJtPUu1Nb+DFGiUbnVMyVkNQesXPFukwk1dqnYZOAdGIzI5 QC128PuV3cEtCY9lbf8uJ2OXIjL5NKuQ2XlS4z9pNFVxmB4FAYegzeWphyy6 sJGrc+VT\",\"sign\":\"JIZK4rAFi/bCouEsjNDPIlJtlSLcTQY5ZPhlNrv7Fbpg9+e1ThyYQ4t5HqAP+E4kvzIAlqPO3vmjYxuS/vrSHbRgXZNxSpoEOL6ho2NQ0iVezZjVnUUjj1T1NvhcJI+PoNDtTcwBN4bWlMQuxvUVFLnJ0BbCQLP+oxkadkfds74=\"}";
		//String requestBody = "{"sign":"S/J4ABpmqDcEhB4F2rN4qR13ZM6O0qOmqdLhjuaZv5ET1pEm1ibnE7quqVSlzCi70pKKGlu6YDMofrHyMJ6DNPlf7dlYWpmRqffsaWRX7rx5LhoCcCf5s0pU1hx6kOXLZQFAvUcaxRFE3ZToHxEgWPArM437NZLm7xa/l8rC5FwYGEFmFZOEcJbqWRNSw4TiC2WmG9thBjIbRXQnasQHpKKz8IQryvSxypXweaOMSAn8pKnRUQ7urS7ecvgFnNLC8NI5yAwaNPZR2ABRpM6EP5Lipxw3OJNYZTtg5mDOBoc2ZwuyBXbdswTlClaHvOlHRaxlZSbNzbC6SiKWvcuJXw==";
		JSONObject json = JSONObject.fromObject(sdata2);
		String sdata = json.getString("sdata");
		sdata = SecurityUtil_AES.decrypt(sdata, AESSECRETERY);
	    System.out.println("输出数据sdata:"+sdata);
	    JSONObject obj = JSONObject.fromObject(sdata);
	    String out_trade_no =  obj.getString("out_trade_no");
	    System.out.println("输出数据out_trade_no:"+out_trade_no);
		
	}
	
	
}
