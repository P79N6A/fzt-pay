package cn.yesway.bmw.manage.controller;

import cn.yesway.bmw.manage.common.Constans;
import cn.yesway.bmw.manage.common.StraightPayEnum;
import cn.yesway.bmw.manage.dto.*;
import cn.yesway.bmw.manage.entity.MerchantsAudit;
import cn.yesway.bmw.manage.entity.MgtUser;
import cn.yesway.bmw.manage.entity.Pager;
import cn.yesway.bmw.manage.entity.PayCenterConfiguration;
import cn.yesway.bmw.manage.entity.res.MerchantsAuditRes;
import cn.yesway.bmw.manage.service.MerchantsAuditService;
import cn.yesway.bmw.manage.service.PayCenterConfigurationService;
import cn.yesway.bmw.manage.utils.AlipayClientUtil;
import cn.yesway.bmw.manage.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.rmi.CORBA.Util;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Controller
@RequestMapping("aliStraightPay")
public class StraightPayController {

    private Logger logger = LoggerFactory.getLogger(StraightPayController.class);

    @Autowired
    private MerchantsAuditService merchantsAuditService;
    @Autowired
    private PayCenterConfigurationService payCenterConfigurationService;

    /**
     * 二级商户申请列表
     */
    @RequestMapping("straightPayList.html")
    public String roleList(MerchantsAuditRes merchantsAuditRes, Integer pageNumber , Integer pageSize, ModelMap model){
        Pager pager = merchantsAuditService.findPageList(merchantsAuditRes, pageNumber, pageSize);
        model.put("pager", pager);
        return "straightpay/straightPayList";
    }

    /**
     * 跳转二级商户申请提交页面
     * @return
     */
    @RequestMapping("add.html")
    public ModelAndView add(ModelAndView model) {
        MgtUser user = (MgtUser)SecurityUtils.getSubject().getPrincipal();
        model.addObject("appId",user.getAppId());
        model.setViewName("straightpay/straightPayAdd");
        return model;
    }

    /**
     * 提交二级商户申请到支付宝
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String save(@RequestBody BizContentStrengthen bizContentStrengthen) {
        try {
            logger.info("表单入参【"+JSON.toJSONString(bizContentStrengthen)+"】");
            BizContent bizContent = new BizContent();
            Date taxPayerValidTime = bizContentStrengthen.getTax_payer_valid_format();
            if (taxPayerValidTime != null) {
                MerchantInvoiceInfo merchantInvoiceInfo = bizContentStrengthen.getInvoice_info();
                merchantInvoiceInfo.setTax_payer_valid(new SimpleDateFormat("yyyyMMdd").format(taxPayerValidTime));
                bizContentStrengthen.setInvoice_info(merchantInvoiceInfo);
            }
            try {
                BeanUtils.copyProperties(bizContent,bizContentStrengthen);
            } catch (Exception e) {
                logger.error("【转换支付通参数错误】",e);
                return StraightPayEnum.AliResponseParamEnum.REJECT_PARAM.getDesp();
            }
            logger.info("请求直付通参数【"+JSON.toJSONString(bizContent)+"】");
            MgtUser user = (MgtUser)SecurityUtils.getSubject().getPrincipal();
            logger.info("操作员【"+user.getLoginName()+"】");
            //调用支付宝直付通提交审核接口
            PayCenterConfiguration query = new PayCenterConfiguration();
            query.setAppId(bizContentStrengthen.getApp_id());
            List<PayCenterConfiguration> payCenterConfigurationList = null;
            try {
                payCenterConfigurationList = payCenterConfigurationService.findList(query);
                logger.info("支付中心配置信息【"+JSON.toJSONString(payCenterConfigurationList)+"】");
            } catch (Exception e) {
                logger.error("【查询支付中心配置信息错误】",e);
                return StraightPayEnum.AliResponseParamEnum.REJECT_PARAM.getDesp();
            }
            PayCenterConfiguration payCenterConfiguration = null;
            String externalId = "";
            String orderId = "";
            if (!CollectionUtils.isEmpty(payCenterConfigurationList)) {
                payCenterConfiguration = payCenterConfigurationList.get(0);
                externalId = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+new Random().nextInt(9999-999);
                bizContent.setExternal_id(externalId);
                try {
                    Map<String,Object> bizContentMap = AlipayClientUtil.objectToMap(bizContent);
                    logger.info("bizContentMap="+JSON.toJSONString(bizContentMap));
                    bizContentMap.put("sign_type",Constans.SIGN_TYPE);
                    String linkStr = AlipayClientUtil.linkString(bizContentMap, "&");
                    logger.info("linkStr="+linkStr);
                    try {
                        String sign = AlipaySignature.rsa256Sign(linkStr, payCenterConfiguration.getYeswayAlipayPrivateKey(), Constans.CHARSET);
                        bizContentMap.put("sign",sign);
                    } catch (AlipayApiException e) {
                        logger.error("【直付通二级商户创建接口签名错误】",e);
                        return StraightPayEnum.AliResponseParamEnum.REJECT_PARAM.getDesp();
                    }
                    orderId = AlipayClientUtil.alizftcreate(JSON.toJSONString(bizContent),payCenterConfiguration.getAppId(),payCenterConfiguration.getYeswayPrivateKey(),payCenterConfiguration.getAlipayPublicKey());
                    logger.info("【调用直付通申请接口返回结果orderId=("+orderId+")】");
                } catch (Exception e) {
                    logger.error("【调用直付通二级商户创建接口失败】",e);
                    return StraightPayEnum.AliResponseParamEnum.REJECT_PARAM.getDesp();
                }
            }
            //保存信息到本地库
            if (!StringUtils.isEmpty(orderId)) {
                try {
                    MerchantsAudit merchantsAudit = new MerchantsAudit();
                    merchantsAudit.setExternalId(externalId);
                    merchantsAudit.setOrderId(orderId);
                    merchantsAudit.setAddTime(new Date());
                    merchantsAudit.setAuditStatus(1);
                    merchantsAudit.setBusinessStatus(1);
                    merchantsAudit.setIsDelete(0);
                    merchantsAudit.setPhone(bizContent.getContact_infos().get(0).getMobile());
                    merchantsAudit.setMerchants(bizContent.getAlias_name());
                    merchantsAudit.setAuditContext(JSON.toJSONString(bizContentStrengthen));
                    merchantsAudit.setUserId(user.getUserId());
                    merchantsAuditService.save(merchantsAudit);
                } catch (Exception e) {
                    logger.error("【保存申请信息错误】",e);
                    return StraightPayEnum.AliResponseParamEnum.REJECT_PARAM.getDesp();
                }
            } else {
                logger.error("【调用直付通申请接口返回结果错误:orderId为空】");
                return StraightPayEnum.AliResponseParamEnum.REJECT_PARAM.getDesp();
            }
        } catch (Exception e) {
            logger.error("【未知错误】",e);
            return StraightPayEnum.AliResponseParamEnum.REJECT_PARAM.getDesp();
        }
        return StraightPayEnum.AliResponseParamEnum.PASS_PARAM.getDesp();
    }

    /**
     * 跳转二级商户申请修改页面
     */
    @RequestMapping("edit.html")
    public ModelAndView edit(String externalId,ModelAndView model) {
        MerchantsAudit merchantsAudit = merchantsAuditService.getById(externalId);
        //获取审核内容反写审核内容到页面
        BizContent bizContent = JSON.parseObject(merchantsAudit.getAuditContext(),BizContent.class);
        model.addObject("bizContent",bizContent);
        AddressInfo addressInfo = bizContent.getBusiness_address();
        model.addObject("bizContent",bizContent);
        ContactInfo contactInfo = bizContent.getContact_infos().get(0);
        model.addObject("bizContent",bizContent);
        IndustryQualificationInfo industryQualificationInfo = bizContent.getQualifications().get(0);
        model.addObject("bizContent",bizContent);
        MerchantInvoiceInfo merchantInvoiceInfo = bizContent.getInvoice_info();
        model.addObject("bizContent",bizContent);
        SettleCardInfo settleCardInfo = bizContent.getBiz_cards().get(0);
        model.addObject("bizContent",bizContent);
        SiteInfo siteInfo = bizContent.getSites().get(0);
        model.addObject("bizContent",bizContent);
        model.setViewName("straightpay/straightPayUpdate");
        return model;
    }

    /**
     * 提交二级商户修改申请到支付宝
     */
    @RequestMapping("update")
    public String update() {
        return "";
    }

}
