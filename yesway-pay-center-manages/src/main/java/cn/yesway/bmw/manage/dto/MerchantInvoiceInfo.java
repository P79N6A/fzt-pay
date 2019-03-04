package cn.yesway.bmw.manage.dto;

import lombok.Data;

/**
 * 开票资料信息
 */
@Data
public class MerchantInvoiceInfo {

    /**
     * 是否必填：true
     * 最大长度：10
     * 描述：是否自动开票，值为true/false
     */
    private Boolean auto_invoice;

    /**
     * 是否必填：true
     * 最大长度：10
     * 描述：是否接受电子发票 true/false
     */
    private Boolean accept_electronic;

    /**
     * 是否必填：true
     * 最大长度：10
     * 描述：纳税人资格种类:01一般纳税人，02小规模纳税人。一般纳税人开的是专票
     */
    private String tax_payer_qualification;

    /**
     * 是否必填：true
     * 最大长度：64
     * 描述：发票抬头
     */
    private String title;

    /**
     * 是否必填：true
     * 最大长度：64
     * 描述：纳税人识别号
     */
    private String tax_no;

    /**
     * 是否必填：true
     * 最大长度：8
     * 描述：纳税人资格开始时间,yyyyMMdd格式
     */
    private String tax_payer_valid;

    /**
     * 是否必填：true
     * 最大长度：64
     * 描述：开票地址
     */
    private String address;

    /**
     * 是否必填：true
     * 最大长度：10
     * 描述：开票电话
     */
    private String telephone;

    /**
     * 是否必填：true
     * 最大长度：32
     * 描述：银行账号
     */
    private String bank_account;

    /**
     * 是否必填：true
     * 最大长度：64
     * 描述：收件人名称
     */
    private String mail_name;

    /**
     * 是否必填：true
     * 最大长度：64
     * 描述：寄送电话
     */
    private String mail_telephone;

    /**
     * 是否必填：true
     * 最大长度：64
     * 描述：开户行名称
     */
    private String bank_name;

    /**
     * 是否必填：true
     * 最大长度：64
     * 描述：收件人地址
     */
    private AddressInfo mail_address;
}
