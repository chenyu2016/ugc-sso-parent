package com.ugc.sso.client.config;

import com.ugc.sso.UserSession;
import com.ugc.sso.client.common.Constant;
import com.ugc.sso.client.service.IClientService;
import com.ugc.sso.client.spring.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * web拦截器
 * Created with IntelliJ IDEA.
 * User: ChenYu
 * Date: 2018/5/28
 */
public class InterceptorConfig implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private IClientService clientService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        logger.debug(">>>MyInterceptor>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");
        HttpSession session = request.getSession();
        if (session.getAttribute(Constant.SESSION_KEY) != null) {
            logger.debug("use session token");
            return true;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            try {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(Constant.COOKIE_KEY)) {
                        // 检查token
                        if(clientService == null) {
                            clientService = (IClientService) SpringContext.getApplicationContext().getBean("clientService");
                        }

                        UserSession userSession = clientService.verifyLogin(cookie.getValue());
                        if(userSession == null){
                            logger.info("use cookie:{} token but token expire",cookie.getValue());
                        }else {
                            // 设置session
                            session.setAttribute(Constant.SESSION_KEY, userSession.getAccName());
                            logger.debug("use cookie token");
                            return true;
                        }
                    }
                }
            } catch (Exception exp) {
                logger.error(exp.toString());
            }
        }
        logger.debug("no use token");
        // 跳转登录
        String url = "/login";
        response.sendRedirect(url);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
