package com.ugc.web;

import com.ugc.sso.client.listener.InitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created with IntelliJ IDEA.
 * User: ChenYu
 * Date: 2018/5/25
 */
@ComponentScan(basePackages = { "com.ugc.sso.client","com.ugc.web" })
@SpringBootApplication
public class Demo2Application {

    @Autowired
    private InitListener initListener;

    /**
     * 注册监听器
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean(){
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(initListener);
        return servletListenerRegistrationBean;
    }

    /**
     * 启动类
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Demo2Application.class,args);
    }
}
