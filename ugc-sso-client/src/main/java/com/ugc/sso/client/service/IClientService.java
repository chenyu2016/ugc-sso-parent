package com.ugc.sso.client.service;

import com.ugc.sso.UserSession;
import com.ugc.sso.domain.Account;

import java.util.Map;

/**
 * sso 客户端接口
 * Created with IntelliJ IDEA.
 * User: ChenYu
 * Date: 2018/5/25
 */
public interface IClientService {

    /**
     * 登录用户名密码验证
     * @param accName
     * @param password
     * @return
     */
    UserSession checkLogin(String accName,String password,Map<String,Object> params);

    /**
     * 校验Cookie
     * @param cookieName
     * @param cookieValue
     * @return
     */
    UserSession checkCookie(String cookieName,String cookieValue);

    /**
     * 检查
     * @param token
     * @return
     */
    UserSession verifyLogin(String token);

    /**
     * 注册
     * @param uId
     * @param password
     * @param params
     * @return
     */
    Account register(String uId, String password, Map<String, Object> params);
}
