package cn.yesway.bmw.manage.dto;

import lombok.Data;

/**
 * 商户结算卡信息。本业务当前只允许传入一张结算卡。与支付宝账号字段二选一必填
 */
@Data
public class SettleCardInfo {

    /**
     * 是否必填：true
     * 最大长度：64
     * 描述：卡户名
     */
    private String account_holder_name;

    /**
     * 是否必填：true
     * 最大长度：20
     * 描述：银行卡号
     */
    private String account_no;

    /**
     * 是否必填：true
     * 最大长度：10
     * 描述：开户行所在地-省
     */
    private String account_inst_province;

    /**
     * 是否必填：true
     * 最大长度：10
     * 描述：开户行所在地-市
     */
    private String account_inst_city;

    /**
     * 是否必填：true
     * 最大长度：20
     * 描述：开户支行名
     */
    private String account_branch_name;

    /**
     * 是否必填：true
     * 最大长度：10
     * 描述：账号使用类型
     *       对公-01
     *       对私-02
     */
    private String usage_type;

    /**
     * 是否必填：true
     * 最大长度：10
     * 描述：卡类型
     *       借记卡-DC
     *       信用卡-CC
     */
    private String account_type;

    /**
     * 是否必填：true
     * 最大长度：10
     * 描述：银行名称
     */
    private String account_inst_name;

    /**
     * 是否必填：true
     * 最大长度：10
     * 描述：开户行简称缩写
     */
    private String account_inst_id;

    /**
     * 是否必填：false
     * 最大长度：64
     * 描述：联行号
     */
    private String bank_code;
}
