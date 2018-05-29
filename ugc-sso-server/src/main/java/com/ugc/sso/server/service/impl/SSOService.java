package com.ugc.sso.server.service.impl;

import com.ugc.sso.UserSession;
import com.ugc.sso.domain.Account;
import com.ugc.sso.domain.User;
import com.ugc.sso.server.mapper.IAccountMapper;
import com.ugc.sso.server.service.ISSOService;
import com.ugc.util.FastJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.UUID;

/**
 * SSO 本地服务实现类
 * Created with IntelliJ IDEA.
 * User: ChenYu
 * Date: 2018/5/24
 */
public class SSOService implements ISSOService {

    private static Logger logger = LoggerFactory.getLogger(SSOService.class);

    /**
     * redis 中用一个hashMap来保存token->userSession的对应关系,用一个map表示user.accId->token的对应关系
     */
    private static final String SESSION_KEY_ACCID = "online_session_accId";
    private static final String SESSION_KEY_TOKEN = "online_session_token";

    private IAccountMapper accountMapper;
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * userssion 过期时间
     */
    private int userSessionExpire = 1000 * 60 * 3;

    public IAccountMapper getAccountMapper() {
        return accountMapper;
    }

    public void setAccountMapper(IAccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public int getUserSessionExpire() {
        return userSessionExpire;
    }

    public void setUserSessionExpire(int userSessionExpire) {
        this.userSessionExpire = userSessionExpire;
    }

    @Override
    public UserSession login(String uId, String password, Map<String, Object> params) {
        logger.debug("用户{}登录", uId);
        Account account = accountMapper.findAccountByAccName(uId);
        UserSession userSession = null;
        if (account != null) {
            // 检查password
            if (!account.getPassword().equals(password)) {
                logger.debug("用户{}登录-------->失败", uId);
                return userSession;
            }

            logger.debug("用户{}登录-------->成功", uId);
            HashOperations<String, String, Object> hops = redisTemplate.opsForHash();
            // 缓存中是否有session信息?
            String token = (String) hops.get(SESSION_KEY_ACCID, uId);
            if (token != null) {
                userSession = (UserSession) hops.get(SESSION_KEY_TOKEN, token);
            }
            if (userSession == null) {
                token = UUID.randomUUID().toString();
                // /do some thing........................
                // hops.putIfAbsent(SESSION_KEY_TOKEN, accId,token );
                boolean succ = hops.putIfAbsent(SESSION_KEY_ACCID, uId, token);
                if (!succ) {
                    token = (String) hops.get(SESSION_KEY_ACCID, uId);
                    userSession = (UserSession) hops.get(SESSION_KEY_TOKEN, token);
                }
                if (userSession == null) {
                    userSession = new UserSession();
                    userSession.setToken(token);
                    userSession.setAccId(account.getAccId());
                    userSession.setAccName(account.getAccName());
                }
            }
            // 设置时间
            userSession.setLastTime(System.currentTimeMillis());
            hops.put(SESSION_KEY_TOKEN, token, userSession);
        } else {
            logger.debug("用户{}登录-------->失败", uId);
        }

        return userSession;
    }

    @Override
    public UserSession verifyLogin(String token) {
        HashOperations<String, String, Object> hops = redisTemplate.opsForHash();

        UserSession userSession = (UserSession) hops.get(SESSION_KEY_TOKEN, token);
        if (userSession != null) {
            if (System.currentTimeMillis() - userSessionExpire > userSession.getLastTime()) {
                logger.debug("token{}usersisson-------->过期", token);
//                HDEL key field1 [field2]
                hops.delete(SESSION_KEY_TOKEN, token);
                return null;
            }
            userSession.setLastTime(System.currentTimeMillis());
            hops.put(SESSION_KEY_TOKEN, token, userSession);
            logger.debug("token{}usersisson-------->更新", token);
        }
        return userSession;
    }

    @Override
    public Account register(String accName, String password, Map<String, Object> params) {
        Account account = accountMapper.findAccountByAccName(accName);
        if(account !=null){
            logger.error("用户{}注册-------->失败");
            return null;
        }
        account = new Account(accName, password);
        accountMapper.createAccount(account);
        logger.debug("用户{}注册-------->成功", FastJsonUtil.toJSONStringHaveClassName(account));
        return account;
    }
}
