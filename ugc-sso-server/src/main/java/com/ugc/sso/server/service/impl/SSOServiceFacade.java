package com.ugc.sso.server.service.impl;

import com.ugc.sso.UserSession;
import com.ugc.sso.api.facade.ISSOServiceFacade;
import com.ugc.sso.domain.Account;
import com.ugc.sso.domain.User;
import com.ugc.sso.server.service.ISSOService;

import java.util.Map;

/**
 * sso 远程服务实现
 * Created with IntelliJ IDEA.
 * User: ChenYu
 * Date: 2018/5/24
 */
public class SSOServiceFacade implements ISSOServiceFacade {

    private ISSOService ssoService;

    public ISSOService getSsoService() {
        return ssoService;
    }

    public void setSsoService(ISSOService ssoService) {
        this.ssoService = ssoService;
    }

    @Override
    public UserSession login(String uId, String password, Map<String, Object> params) {
        return ssoService.login(uId,password,params);
    }

    @Override
    public UserSession verifyLogin(String token) {
        return ssoService.verifyLogin(token);
    }

    @Override
    public Account register(String uId, String password, Map<String, Object> params) {
        return ssoService.register(uId,password,params);
    }
}
