package cn.yesway.bmw.manage.dto;

import lombok.Data;

/**
 * 商户站点信息，包括网站、app、小程序。商户使用服务包含电脑支付或wap支付时，必须填充一个类型为01(网站)的SiteInfo对象；当包含app支付时，必须至少填充类型为02(APP)或06(支付宝小程序)中一种类型的SiteInfo对象
 */
@Data
public class SiteInfo {

    /**
     * 是否必填：true
     * 最大长度：32
     * 描述：网站：01
     *      APP : 02
     *      服务窗:03
     *      公众号:04
     *      其他:05
     *      支付宝小程序:06
     */
    private String site_type;

    /**
     * 是否必填：false
     * 最大长度：256
     * 描述：站点地址
     */
    private String site_url;

    /**
     * 是否必填：false
     * 最大长度：512
     * 描述：站点名称
     */
    private String site_name;

    /**
     * 是否必填：false
     * 最大长度：128
     * 描述：测试账号
     */
    private String account;

    /**
     * 是否必填：false
     * 最大长度：128
     * 描述：测试密码
     */
    private String password;
}
