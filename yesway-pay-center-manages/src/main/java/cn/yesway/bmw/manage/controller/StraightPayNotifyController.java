package cn.yesway.bmw.manage.controller;

import cn.yesway.bmw.manage.common.StraightPayEnum;
import cn.yesway.bmw.manage.entity.MerchantsAudit;
import cn.yesway.bmw.manage.service.MerchantsAuditService;
import cn.yesway.bmw.manage.utils.AlipayClientUtil;
import cn.yesway.bmw.manage.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("aliStraightpaynotify")
public class StraightPayNotifyController {

    Logger logger = LoggerFactory.getLogger(StraightPayNotifyController.class);

    @Autowired
    private MerchantsAuditService merchantsAuditService;
    /**
     * 支付宝直付通二级商户审核通过回调
     */
    @RequestMapping("straightpaynotify_url")
    @ResponseBody
    public void straightPayPass_url(HttpServletRequest request, HttpServletResponse response) {
        long threadId = Thread.currentThread().getId();
        logger.info("【" + threadId + "】" + "接收支付宝直付通后台通知，支付宝主商户应用ID：" + request.getParameter("app_id"));
        String desc = "【" + threadId + "】" + "接收支付宝直付通后台通知: ";
        PrintWriter printWriter = null;
        try {
            response.setContentType("text/html;charset=utf-8");
            request.setCharacterEncoding("utf-8");
            printWriter = response.getWriter();
            //参数处理
            Map requestParams = request.getParameterMap();
            logger.info(desc + "原始参数=" + JSON.toJSONString(requestParams));
            Map paramMap = null;
            try {
                paramMap = AlipayClientUtil.getRequestParam(request);
            } catch (Exception e) {
                logger.error("【解析参数出错】",e);
                desc = StraightPayEnum.AliResponseParamEnum.REJECT_PARAM.getDesp();
                return;
            }
            logger.info(desc + "处理后参数=" + JSON.toJSONString(paramMap));
            //校验申请记录
            MerchantsAudit query = new MerchantsAudit();
            query.setExternalId((String) paramMap.get("external_id"));
            query.setOrderId((String) paramMap.get("order_id"));
            query.setAppId(request.getParameter("app_id"));
            List<MerchantsAudit> merchantsAuditList = merchantsAuditService.findList(query);
            //回写数据库结果
            if (!CollectionUtils.isEmpty(merchantsAuditList)) {
                try {
                    desc = updateData(merchantsAuditList,paramMap);
                } catch (Exception e) {
                    logger.error("【修改申请记录出错】",e);
                    desc = StraightPayEnum.AliResponseParamEnum.REJECT_PARAM.getDesp();
                    return;
                }
            } else {
                logger.info("【未找到二级商户申请记录】");
                desc = StraightPayEnum.AliResponseParamEnum.REJECT_PARAM.getDesp();
            }
        } catch (Exception e) {
            logger.error("【未知错误】",e);
            desc = StraightPayEnum.AliResponseParamEnum.REJECT_PARAM.getDesp();
        } finally {
            logger.info("【" + threadId + "】" + "返回支付宝：" + desc);
            printWriter.write(desc);// 支付宝接收不到“success” 就会在24小时内重复调用多次
            printWriter.flush();
            printWriter.close();
        }
    }

    public String updateData(List<MerchantsAudit> merchantsAuditList,Map paramMap) {
        String desc = "";
        MerchantsAudit merchantsAudit = merchantsAuditList.get(0);
        //判断审核是否通过
        String msg_method = (String) paramMap.get("msg_method");
        int result = 0;
        if (!StringUtils.isEmpty(msg_method) && msg_method.equals(StraightPayEnum.AliRequestUrlEnum.PASS_URL.getDesp())) {//审核通过
            merchantsAudit.setSmId((String) paramMap.get("smid"));
            merchantsAudit.setCardAliasNo((String) paramMap.get("card_alias_no"));
            merchantsAudit.setMemo((String) paramMap.get("memo"));
            merchantsAudit.setAuditStatus(StraightPayEnum.AuditStatusEnum.FORE.getVal());
            merchantsAudit.setUpdateTime(new Date());
            result = merchantsAuditService.update(merchantsAudit);
            desc = StraightPayEnum.AliResponseParamEnum.PASS_PARAM.getDesp();
        }else if (!StringUtils.isEmpty(msg_method) && msg_method.equals(StraightPayEnum.AliRequestUrlEnum.REJECT_URL.getDesp())) {//审核未通过
            merchantsAudit.setAuditStatus(StraightPayEnum.AuditStatusEnum.THREE.getVal());
            merchantsAudit.setReason((String) paramMap.get("reason"));
            merchantsAudit.setUpdateTime(new Date());
            result = merchantsAuditService.update(merchantsAudit);
            desc = StraightPayEnum.AliResponseParamEnum.PASS_PARAM.getDesp();
        } else {
            logger.info("【未找到正确的回调方法("+msg_method+")】");
            desc = StraightPayEnum.AliResponseParamEnum.REJECT_PARAM.getDesp();
        }
        logger.info("【修改申请记录结果("+msg_method+")】");
        return desc;
    }
}
