package com.ugc.sso.domain;

/**
 * 玩家帐户信息
 * Created with IntelliJ IDEA.
 * User: ChenYu
 * Date: 2018/5/24
 */
public class Account implements java.io.Serializable {

    private static final long serialVersionUID = 7624640297392166251L;

    private Long accId;

    /** 帐号名称 */
    private String accName;

    /** 帐号密码 */
    private String password;

    /** 平台账号昵称 */
    private String accDesc;

    /**
     * 0-正常
     * 1-锁定
     * 2-未激活
     * */
    private int status;

    /** 锁定原因描述 */
    private String statusDec;

    /** ip */
    private String loginIp;

    /** 创建时间 */
    private long createDttm;

    /** 最后一次登录时间 */
    private long lastLoginDttm;

    public Long getAccId() {
        return accId;
    }

    public void setAccId(Long accId) {
        this.accId = accId;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccDesc() {
        return accDesc;
    }

    public void setAccDesc(String accDesc) {
        this.accDesc = accDesc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusDec() {
        return statusDec;
    }

    public void setStatusDec(String statusDec) {
        this.statusDec = statusDec;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public long getCreateDttm() {
        return createDttm;
    }

    public void setCreateDttm(long createDttm) {
        this.createDttm = createDttm;
    }

    public long getLastLoginDttm() {
        return lastLoginDttm;
    }

    public void setLastLoginDttm(long lastLoginDttm) {
        this.lastLoginDttm = lastLoginDttm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account() {
    }

    public Account(String accName, String password) {
        this.accName = accName;
        this.password = password;
    }
}
