package cn.yesway.bmw.manage.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BizContentStrengthen extends BizContent {

    /**
     * 是否必填：true
     * 最大长度：32
     * 描述：支付宝应用id
     */
    private String app_id;

    /**
     * 是否必填：false
     * 最大长度：256
     * 描述：营业执照图片url，本业务接口中，如果是特殊行业必填。其值为使用ant.merchant.expand.indirect.image.upload上传图片得到的一串oss key。
     */
    private String cert_image_url;

    /**
     * 是否必填：false
     * 最大长度：256
     * 描述：法人身份证正面url，其值为使用ant.merchant.expand.indirect.image.upload上传图片得到的一串oss key。本业务接口中，如果是特殊行业必填
     */
    private String legal_cert_front_image_url;

    /**
     * 是否必填：false
     * 最大长度：256
     * 描述：法人身份证反面url，其值为使用ant.merchant.expand.indirect.image.upload上传图片得到的一串oss key。本业务接口中，如果是特殊行业必填
     */
    private String legal_cert_back_image_url;

    /**
     * 是否必填：false
     * 最大长度：256
     * 描述：门头照，其值为使用ant.merchant.expand.indirect.image.upload上传图片得到的一串oss key。如果使用当面付服务则必填
     */
    private List<String> out_door_images_url;

    /**
     * 是否必填：false
     * 最大长度：256
     * 描述：营业执照授权函。其值为使用ant.merchant.expand.indirect.image.upload上传图片得到的一串oss key。当商户名与结算卡户名不一致时必填
     */
    private String license_auth_letter_image_url;

    /**
     * 是否必填：false
     * 最大长度：256
     * 描述：证件反面图片。目前只有当主证件为身份证时才需填写
     */
    private String cert_image_back_url;

    /**
     * 是否必填：true
     * 最大长度：8
     * 描述：纳税人资格开始时间,yyyyMMdd格式
     */
    private Date tax_payer_valid_format;

}
