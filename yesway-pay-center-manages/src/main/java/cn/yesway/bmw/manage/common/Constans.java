package cn.yesway.bmw.manage.common;

public class Constans {
	/**
	 * 登录成功
	 */
	public static String LOGIN_SUCCESS="1";
	
	/**
	 * 验证码过期
	 */
	public static String VALIDATE_CODE_OVERDUE="2";

	/**
	 * 验证码为空
	 */
	public static String VALIDATE_CODE_IS_NULL="3";
	
	/**
	 * 验证码错误
	 */
	public static String VALIDATE_CODE_WRONG="4";
	
	/**
	 * 操作员被停用
	 */
	public static String OPERATOR_UNUSED="5";
	
	/**
	 * session中保存用户的key
	 */
	public static String CURRENT_USER_KEY="current_user";
	/**
	 * session中保存用户的登录时间
	 */
	public static String LOGIN_TIME="login_time";
	/**
	 * session中保存用户的登录ip
	 */
	public static String LOGIN_IP="login_ip";

	/**
	 * AppId 即创建应用后生成
	 */
	public static String APP_ID = "2016120103687613";
	/**
	 * ali公钥
	 */
	public static String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkXp1PIBEFgFo9GysNH/lawe2a6zY4M/7VbB7c1KaBGsY7wJ0q9F3M8VnnaWNzF1p4hhIVXKnsqvnISIpZMC6XJRqB13l2e8meVk+sL2U1aAxqtQXyhsYStcN7XRntdz/OPHQ8CXnoThgl+oin7kZ2vD0cGyPz3gTEZOYD28f2he3CL+vVNqnaeQx8ogxt7KX0UCWwzF9KDrxiLKLT+fGSURvuzVgt/c/0uWIerJ6pPqGnHPIHUfzWv4pmlCk3n22IbE80cyDKPBpoZj8VBH39BQ5R+H1hOFkKEPiOZgw8Tlc312xIc4y8KLfCUkzPbfScaD9tVHDL8suLLsuWPI9NwIDAQAB";

	/**
	 * ali私钥
	 */
	public static String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCDk2n3n6cv0FdICtql8tYYDApkSZg2aFtcqBziPAiXKJNznxmuAtee/xnQ7/WxEB8DeSsZ6Tn1foyWZyKOdgQuF9TgM09XEfZMdj9lLLCCE+de/xhtmED6lyMyDevGtk/BIUL6sBdQLoCJFXhhY6yw0YmQjh5jwl6iGbge+VRV31vVGIGBOcuZsFxxG0+6bY5i4IUcU39wB0ONhPTfOjn27/GEmBhcxO+bkqNEA+fPTbS7hcxi2+p9p4gbZBWBKlwHeFhbzOa6poj1n7F1F47eyzwxhfxyMgD6PzIa1eSMcxReKt8m1RGMwRV2DnxBwyVw83TfJYxRkYZxfXnP+6XZAgMBAAECggEAKx3Ph9nFi6GWOIF2DRN6nP61GRAU1R91cPOWI3zQw9gEGct1OtSrm+q0pwc7V/6Uayr1uNP4rj3+C/tYE58Q15nvpFisEzhLodrOA3WVkcq78yFc++z8viFw4yI0tTVhBUTFEilKOnhCnqlufVuQZ5ECp2ArDT3QjQYok93YiRrkSXH6EM0CvSTceyk5rcOXCA6Qlljb632OHLW1s8VnkjRXtTVRUNLH19ZL4u8ktQ/rjcLVdo4+OizNTlF5zzK8Y0T8+EXvuf7KjqfEQ/ohBdGm15yKRzuTUu+wHZ9d1JhgWmYZer5w454FhEip0W0xoTrIjyThu31b6+fFAFRaQQKBgQC7XQfcJRQojxdjm2umk0ebBXOMJK3DM21Pblu+6WBT4QEaCyhoI1Ow1z8ZRUX5cxmQHTSzkx6jix3tpUNWtuNVZanxz76ysN3WX44JlFG4Be89FJFxVhN+lbyd6cAoO10uJSXIQgpA0SMru54kFIZHSPEXtz6+FO0Q8A7E2hZqZQKBgQCzxp3V6fYzG6wSoUOnKZCalj4YI9SgYwPJX1eHMPyF6uDAwni3u4PtoU2I1gim1gWA6Lf0UaBTSrYky33dcOqf819M+ZeSlzlQWIhY02S/oIQcwXZnfqdrSPZP8cVBojZoErPj0TI9eBDPnoT3HuXD/4XlEmFV7tZvk/vtdiQ8ZQKBgHc3Rp0I6r6z7YnvhpvC8rjsywzM+dNOkasM6UucazJz5KORnfBFKidHjlqh/VgvqIBOVQ49+izNhAmGMkamsBfSTEQUj55bc4vH2TS3LxgMjqEqyTB4OIc+93jzFtWCkFOgR14YxzVe3SYihwkmFhqdiuowKrf/0KgKVxtiOQkRAoGACvvDgOI+bHjwVmML4xajAw7S1F68CuCBxrfQfS38/6GOAbjf7znBoa8NMaMB/E9vHqUZOBAUn4IdBrp4AiN38jXISMem/wnGMqZ2Dhc8PIjuCWO4fDI8wE1A3zmCN7RVZp5d5zlMvHytakHhFcgJeaQO5iPtVna1GzQLXEKvjBECgYEAiYp6E7Q8Wx7ETh0896zXs9nvOsBSIzOI2P6lR8RYhhTKCq0V6QWnbakLYgKF5m0o4Y/yVqQstLiw1D1b5WuzZQ1+2cx7Y6N1gJ80NgTpS6oXcyRuNS8MR57DKxek2/eUDSTf8r+wpAWMRWh4V7eWhZaR6ZNKAWlTHxVeN1Gbm1g=";

	/**
	 * 编码
	 */
	public static String CHARSET = "utf-8";

	/**
	 * 支付宝网关（固定）
	 */
	public static String SERVER_URL = "https://openapi.alipay.com/gateway.do";

	/**
	 * 签名方式
	 */
	public static String SIGN_TYPE = "RSA2";

	/**
	 * 数据格式化方式
	 */
	public static String FORMAT = "JSON";
}
