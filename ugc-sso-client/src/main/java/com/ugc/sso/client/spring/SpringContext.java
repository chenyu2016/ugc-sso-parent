package com.ugc.sso.client.spring;

import org.springframework.context.ApplicationContext;

/**
 * spring 上下文
 * Created with IntelliJ IDEA.
 * User: ChenYu
 * Date: 2018/5/25
 */
public class SpringContext {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void  setApplicationContext(ApplicationContext applicationContext) {
        SpringContext.applicationContext = applicationContext;
    }
}
