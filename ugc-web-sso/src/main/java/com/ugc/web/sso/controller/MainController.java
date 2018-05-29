package com.ugc.web.sso.controller;

import com.ugc.sso.UserSession;
import com.ugc.sso.client.common.Constant;
import com.ugc.sso.client.service.IClientService;
import com.ugc.sso.domain.Account;
import com.ugc.util.FastJsonUtil;
import com.ugc.util.HttpUtils;
import com.ugc.util.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主控制器
 * Created with IntelliJ IDEA.
 * User: ChenYu
 * Date: 2018/5/28
 */
@Controller
public class MainController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "clientService")
    private IClientService clientService;

    @Value("${url.host}")
    private String urlHosts;

    private List<String> urlHostList;

    public List<String> getUrlHostList() {
        if(urlHostList == null && urlHosts!=null && !"".equals(urlHosts)){
            urlHostList = Arrays.asList(urlHosts.split(","));
        }
        return urlHostList;
    }

    public void setUrlHostList(List<String> urlHostList) {
        this.urlHostList = urlHostList;
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        String account = (String) session.getAttribute(Constant.SESSION_KEY);
        if (account == null || "".equals(account)) {
            logger.debug(Constant.SESSION_KEY+":no");
            return "login";
        }
        model.addAttribute("name", account);
        logger.debug(Constant.SESSION_KEY+":yes");
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/loginPost")
    @ResponseBody
    public String loginPost(String accName, String password, HttpSession session, HttpServletResponse response) {
        UserSession userSession = clientService.checkLogin(accName,password,null);
        if(userSession == null){
            logger.debug("account={},登录失败密码错误", accName);
            return null;
        }

        if(getUrlHostList()!=null && getUrlHostList().size()>0){
            for(String e1 : getUrlHostList()) {
                try {
                    String url = e1+"/addCookie?cookieName="+Constant.COOKIE_KEY+"cookieValue="+userSession.getToken();
                    HttpUtils.httpGet(url);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.toString());
                }
            }
        }else {
            // 设置当前的cookie
            addCookie(Constant.COOKIE_KEY, userSession.getToken(), response);
        }

        logger.debug("account={},登录成功", accName);
        return FastJsonUtil.toJSONStringHaveClassName(userSession);
    }

    /**
     * 注册
     *
     * @param accName
     * @param password
     * @return
     */
    @PostMapping("/registerPost")
    @ResponseBody
    public String registerPost(String accName, String password) {
        password = MD5.getMD5(password);
        Account account = clientService.register(accName, password, null);
        if (account == null) {
            logger.debug("account={},注册失败", account);
            return Constant.REGISTER_FALSE;
        } else {
            logger.debug("account={},注册成功", account);
            return Constant.REGISTER_TRUE;
        }
    }

    /**
     * 登出
     *
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 移除session
        session.removeAttribute(Constant.SESSION_KEY);
        return "redirect:/login";
    }

    /**
     * 向当前域添加cookie
     *
     * @param cookieName
     * @param cookieValue
     * @param response
     */
    @RequestMapping(value = "/addCookie")
    public void addCookie(String cookieName, String cookieValue, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName.trim(), cookieValue.trim());
        // 设置为30min
        // cookie.setMaxAge(30 * 60);
        // 顶级域名下，所有应用都是可见的
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @RequestMapping("/editCookie")
    public void editCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        Cookie[] cookies = request.getCookies();
        if (null == cookies) {
            logger.debug("没有cookie==============");
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    logger.debug("原值为:" + cookie.getValue());
                    cookie.setValue(value);
                    cookie.setPath("/");
                    // 设置为30min
                    cookie.setMaxAge(30 * 60);
                    logger.debug("被修改的cookie名字为:" + cookie.getName() + ",新值为:" + cookie.getValue());
                    response.addCookie(cookie);
                    break;
                }
            }
        }

    }

    /**
     * 删除cookie
     *
     * @param request
     * @param response
     * @param name
     */
    @RequestMapping("/delCookie")
    public void delCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (null == cookies) {
            logger.debug("没有cookie==============");
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue(null);
                    // 立即销毁cookie
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    logger.debug("被删除的cookie名字为:" + cookie.getName());
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }


}
