package cn.yesway.bmw.manage.common;

public enum  StraightPayEnum {
    ONE(0,"未删除"),
    TWO(1,"已删除");

    private int val;
    private String desp;

    StraightPayEnum(int val, String desp) {
        this.val = val;
        this.desp = desp;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    /**
     * 审核状态
     */
    public enum AuditStatusEnum {
        ONE(1,"已保存"),
        TWO(2,"审核中"),
        THREE(3,"审核失败"),
        FORE(4,"审核通过");

        private int val;
        private String desp;

        AuditStatusEnum(int val, String desp) {
            this.val = val;
            this.desp = desp;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public String getDesp() {
            return desp;
        }

        public void setDesp(String desp) {
            this.desp = desp;
        }
    }
    /**
     * 业务状态
     */
    public enum BusinessStatusEnum {
        ONE(1,"正常"),
        TWO(2,"停用");

        private int val;
        private String desp;

        BusinessStatusEnum(int val, String desp) {
            this.val = val;
            this.desp = desp;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public String getDesp() {
            return desp;
        }

        public void setDesp(String desp) {
            this.desp = desp;
        }
    }
    /**
     * 相应支付宝参数
     */
    public enum AliResponseParamEnum {
        PASS_PARAM("success"),
        REJECT_PARAM("fail");

        private String desp;

        AliResponseParamEnum(String desp) {
            this.desp = desp;
        }

        public String getDesp() {
            return desp;
        }

        public void setDesp(String desp) {
            this.desp = desp;
        }
    }
    /**
     * 支付宝回调方法
     */
    public enum AliRequestUrlEnum {
        PASS_URL("ant.merchant.expand.indirect.zft.passed"),
        REJECT_URL("ant.merchant.expand.indirect.zft.rejected");

        private String desp;

        AliRequestUrlEnum(String desp) {
            this.desp = desp;
        }

        public String getDesp() {
            return desp;
        }

        public void setDesp(String desp) {
            this.desp = desp;
        }
    }
}
