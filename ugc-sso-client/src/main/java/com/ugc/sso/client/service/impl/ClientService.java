package com.ugc.sso.client.service.impl;

import com.ugc.sso.UserSession;
import com.ugc.sso.api.facade.ISSOServiceFacade;
import com.ugc.sso.client.service.IClientService;
import com.ugc.sso.domain.Account;

import java.util.Map;

/**
 * sso 客户端服务实现
 * Created with IntelliJ IDEA.
 * User: ChenYu
 * Date: 2018/5/25
 */
public class ClientService implements IClientService {

    private ISSOServiceFacade sSoServiceFacade;

    public ISSOServiceFacade getsSoServiceFacade() {
        return sSoServiceFacade;
    }

    public void setsSoServiceFacade(ISSOServiceFacade sSoServiceFacade) {
        this.sSoServiceFacade = sSoServiceFacade;
    }

    @Override
    public UserSession checkLogin(String accName, String password,Map<String,Object> params) {
        UserSession userSession = sSoServiceFacade.login(accName,password,params);
        return userSession;
    }

    @Override
    public UserSession checkCookie(String cookieName, String cookieValue) {
        return null;
    }

    @Override
    public UserSession verifyLogin(String token) {
        return sSoServiceFacade.verifyLogin(token);
    }

    @Override
    public Account register(String uId, String password, Map<String, Object> params) {
        return sSoServiceFacade.register(uId,password,params);
    }
}
