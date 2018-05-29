package com.ugc.sso.client.listener;

import com.ugc.sso.client.spring.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 系统初始化监听
 * Created with IntelliJ IDEA.
 * User: ChenYu
 * Date: 2018/5/25
 */
@Component
public class InitListener implements ServletContextListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.debug("==============InitListener:init");
        // 设置spring 容器
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        SpringContext.setApplicationContext(context);
        logger.debug("==============InitListener:init:初始化spring 上下文");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
