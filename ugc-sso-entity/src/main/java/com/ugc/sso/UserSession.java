package com.ugc.sso;

/**
 * Created with IntelliJ IDEA.
 * User: ChenYu
 * Date: 2018/5/24
 */
public class UserSession implements java.io.Serializable{

    private static final long serialVersionUID = 4178297400111396517L;

    /**
     * 账户Id
     */
    private Long accId;

    /**
     * 账户Name
     */
    private String accName;

    /**
     * token值
     */
    private String token;

    /**
     * 最后更新日期,用于判断是否过期
     */
    private long lastTime;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
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
}
