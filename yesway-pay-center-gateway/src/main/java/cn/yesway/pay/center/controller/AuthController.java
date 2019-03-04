
package cn.yesway.pay.center.controller;

import org.apache.log4j.Logger;

/**  鉴权管理
 * @author ghk
 *  2017年2月7日上午11:21:04
 *  AuthController
 */
public class AuthController extends BaseController{
	
	private final Logger logger = Logger.getLogger(this.getClass());
    
	/** 向缓存写数据时使用的key的格式，的key，。 */
	public static final String PREFIX_KEY = "cacheKeyPrefix";
	public static final String EXPIRE_KEY = "token_expire";
}
