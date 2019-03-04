package cn.yesway.bmw.manage.dto;

import lombok.Data;

/**
 * 商户联系人信息。在本业务中，ContactInfo对象中名称，类型、手机号必填，其他选填
 */
@Data
public class ContactInfo {

    /**
     * 是否必填：true
     * 最大长度：128
     * 描述：联系人名字
     */
    private String name;

    /**
     * 是否必填：false
     * 最大长度：20
     * 描述：电话
     */
    private String phone;

    /**
     * 是否必填：特殊可选
     * 最大长度：20
     * 描述：手机号。必填与否参见外层对象描述，无特别说明认为是非必填
     */
    private String mobile;

    /**
     * 是否必填：false
     * 最大长度：128
     * 描述：电子邮箱
     */
    private String email;

    /**
     * 是否必填：true
     * 最大长度：6
     * 描述：商户联系人业务标识枚举，表示商户联系人的职责。异议处理接口人:02;商户关键联系人:06;数据反馈接口人:11;服务联动接口人:08
     */
    private String tag;

    /**
     * 是否必填：true
     * 最大长度：20
     * 描述：联系人类型，取值范围：LEGAL_PERSON：法人；CONTROLLER：实际控制人；AGENT：代理人；OTHER：其他
     */
    private String type;

    /**
     * 是否必填：false
     * 最大长度：32
     * 描述：身份证号
     */
    private String id_card_no;
}
