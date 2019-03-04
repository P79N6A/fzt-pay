package cn.yesway.bmw.manage.entity.res;

import cn.yesway.bmw.manage.entity.MerchantsAudit;
import lombok.Data;

import java.util.Date;

@Data
public class MerchantsAuditRes extends MerchantsAudit {

    private String nameLike;

    private Date beginTime;

    private Date endTime;
}
