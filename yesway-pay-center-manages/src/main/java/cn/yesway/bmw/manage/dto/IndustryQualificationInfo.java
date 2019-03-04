package cn.yesway.bmw.manage.dto;

import lombok.Data;

/**
 * 商户行业资质，当商户是特殊行业时必填
 */
@Data
public class IndustryQualificationInfo {

    /**
     * 是否必填：true
     * 最大长度：20
     * 描述：商户行业资质类型，具体选值参见https://mif-pub.alipayobjects.com/QualificationType.xlsx
     */
    private String industry_qualification_type;

    /**
     * 是否必填：true
     * 最大长度：256
     * 描述：商户行业资质图片。其值为使用ant.merchant.expand.indirect.image.upload上传图片得到的一串oss key
     */
    private String industry_qualification_image;
}
