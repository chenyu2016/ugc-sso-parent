package com.ugc.sso.server.service;

import com.ugc.sso.UserSession;
import com.ugc.sso.domain.Account;
import com.ugc.sso.domain.User;

import java.util.Map;

/**
 * sso 本地服务接口
 * Created with IntelliJ IDEA.
 * User: ChenYu
 * Date: 2018/5/24
 */
public interface ISSOService {

    /**
     * 登录
     * @param uId
     * @param password
     * @param params
     * @return
     */
    UserSession login(String uId, String password, Map<String, Object> params);

    /**
     * 检测
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
