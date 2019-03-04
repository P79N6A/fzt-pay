package cn.yesway.bmw.manage.dto;

import lombok.Data;

/**
 * 经营地址。地址对象中省、市、区、地址必填，其余选填
 */
@Data
public class AddressInfo {

    /**
     * 是否必填：true
     * 最大长度：10
     * 描述：城市编码，城市编码是与国家统计局一致，请查询:http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/
     *       国标省市区号下载：http://aopsdkdownload.cn-hangzhou.alipay-pub.aliyun-inc.com/doc/2016.xls?spm=a219a.7629140.0.0.qRW4KQ&file=2016.xls
     */
    private String city_code;

    /**
     * 是否必填：true
     * 最大长度：10
     * 描述：区县编码，区县编码是与国家统计局一致，请查询: http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/
     *       国标省市区号下载：http://aopsdkdownload.cn-hangzhou.alipay-pub.aliyun-inc.com/doc/2016.xls?spm=a219a.7629140.0.0.qRW4KQ&file=2016.xls
     */
    private String district_code;

    /**
     * 是否必填：true
     * 最大长度：256
     * 描述：地址。商户详细经营地址或人员所在地点
     */
    private String address;

    /**
     * 是否必填：true
     * 最大长度：10
     * 描述：省份编码，省份编码是与国家统计局一致，请查询: http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/
     *       国标省市区号下载：http://aopsdkdownload.cn-hangzhou.alipay-pub.aliyun-inc.com/doc/2016.xls?spm=a219a.7629140.0.0.qRW4KQ&file=2016.xls
     */
    private String province_code;

    /**
     * 是否必填：false
     * 最大长度：11
     * 描述：经度，浮点型, 小数点后最多保留6位。
     *       如需要录入经纬度，请以高德坐标系为准，录入时请确保经纬度参数准确。高德经纬度查询：http://lbs.amap.com/console/show/picker
     */
    private String longitude;

    /**
     * 是否必填：false
     * 最大长度：10
     * 描述：纬度，浮点型,小数点后最多保留6位，如需要录入经纬度，请以高德坐标系为准，录入时请确保经纬度参数准确。高德经纬度查询：http://lbs.amap.com/console/show/picker
     */
    private String latitude;

    /**
     * 是否必填：false
     * 最大长度：32
     * 描述：地址类型。取值范围：BUSINESS_ADDRESS：经营地址（默认）
     */
    private String type;
}
