package cn.yesway.bmw.manage.dto;


import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 发送对象
 */
@Data
@ToString
public class BizContent {

    /**
     * 是否必填：true
     * 最大长度：32
     * 描述：商户编号，由机构定义，需要保证在机构下唯一
     */
    private String external_id;

    /**
     * 是否必填：false
     * 最大长度：90
     * 描述：外部业务号
     */
    private String out_biz_no;

    /**
     * 是否必填：true
     * 最大长度：128
     * 描述：进件的二级商户名称
     */
    private String name;

    /**
     * 是否必填：true
     * 最大长度：128
     * 描述：商户别名
     */
    private String alias_name;

    /**
     * 是否必填：true
     * 最大长度：20
     * 描述：商家类型：01：企业；02：事业单位
     */
    private String merchant_type;

    /**
     * 是否必填：true
     * 最大长度：10
     * 描述：商户类别码mcc，参见附件描述中的“类目code” https://mif-pub.alipayobjects.com/AlipayMCC.xlsx
     */
    private String mcc;

    /**
     * 是否必填：true
     * 最大长度：20
     * 描述：商户证件编号（企业或者个体工商户提供营业执照，事业单位提供事证号）
     */
    private String cert_no;

    /**
     * 是否必填：true
     * 最大长度：20
     * 描述：商户证件类型，取值范围：201：营业执照；2011:营业执照(统一社会信用代码)；218：事业单位法人证书
     */
    private String cert_type;

    /**
     * 是否必填：false
     * 最大长度：256
     * 描述：营业执照图片url，本业务接口中，如果是特殊行业必填。其值为使用ant.merchant.expand.indirect.image.upload上传图片得到的一串oss key。
     */
    private String cert_image;

    /**
     * 是否必填：true
     * 最大长度：64
     * 描述：法人名称
     */
    private String legal_name;

    /**
     * 是否必填：true
     * 最大长度：18
     * 描述：法人身份证号
     */
    private String legal_cert_no;

    /**
     * 是否必填：false
     * 最大长度：256
     * 描述：法人身份证正面url，其值为使用ant.merchant.expand.indirect.image.upload上传图片得到的一串oss key。本业务接口中，如果是特殊行业必填
     */
    private String legal_cert_front_image;

    /**
     * 是否必填：false
     * 最大长度：256
     * 描述：法人身份证反面url，其值为使用ant.merchant.expand.indirect.image.upload上传图片得到的一串oss key。本业务接口中，如果是特殊行业必填
     */
    private String legal_cert_back_image;

    /**
     * 是否必填：true
     * 最大长度：20
     * 描述：客服电话
     */
    private String service_phone;

    /**
     * 是否必填：false
     * 最大长度：256
     * 描述：门头照，其值为使用ant.merchant.expand.indirect.image.upload上传图片得到的一串oss key。如果使用当面付服务则必填
     */
    private List<String> out_door_images;

    /**
     * 是否必填：false
     * 最大长度：256
     * 描述：营业执照授权函。其值为使用ant.merchant.expand.indirect.image.upload上传图片得到的一串oss key。当商户名与结算卡户名不一致时必填
     */
    private String license_auth_letter_image;

    /**
     * 是否必填：true
     * 最大长度：20
     * 描述：商户使用服务，可选值有：当面付、app支付、wap支付、电脑支付
     */
    private List<String> service;

    /**
     * 是否必填：true
     * 最大长度：20
     * 描述：二级商户与服务商的签约时间
     */
    private String sign_time_with_isv;

    /**
     * 是否必填：特殊可选
     * 最大长度：64
     * 描述：商户支付宝账号，用作结算账号。与银行卡对象字段二选一必填
     */
    private String alipay_logon_id;

    /**
     * 是否必填：false
     * 最大长度：256
     * 描述：证件反面图片。目前只有当主证件为身份证时才需填写
     */
    private String cert_image_back;

    /**
     * 是否必填：true
     * 最大长度：
     * 描述：经营地址
     */
    private AddressInfo business_address;

    /**
     * 是否必填：true
     * 最大长度：256
     * 描述：商户联系人信息
     */
    private List<ContactInfo> contact_infos;

    /**
     * 是否必填：特殊可选
     * 最大长度：
     * 描述：商户结算卡信息
     */
    private List<SettleCardInfo> biz_cards;

    /**
     * 是否必填：false
     * 最大长度：
     * 描述：商户行业资质，当商户是特殊行业时必填
     */
    private List<IndustryQualificationInfo> qualifications;

    /**
     * 是否必填：false
     * 最大长度：
     * 描述：商户站点信息，包括网站、app、小程序。商户使用服务包含电脑支付或wap支付时，必须填充一个类型为01(网站)的SiteInfo对象；当包含app支付时，必须至少填充类型为02(APP)或06(支付宝小程序)中一种类型的SiteInfo对象
     */
    private List<SiteInfo> sites;

    /**
     * 是否必填：false
     * 最大长度：
     * 描述：开票资料信息
     */
    private MerchantInvoiceInfo invoice_info;
}
