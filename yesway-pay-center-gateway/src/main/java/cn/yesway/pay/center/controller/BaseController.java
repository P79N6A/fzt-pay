package cn.yesway.pay.center.controller;

import cn.yesway.commons.StringUtils;

/**
 * @author:yaosheng
 * @link:yaosheng@95190.com
 * @date:2016/11/29 10:48
 */
public class BaseController {

    public boolean authParams(String... params) throws Exception {
        for (String param : params) {
            if (StringUtils.isEmpty(param)) {
               return false;
            }
        }
        return true;
    }

}
