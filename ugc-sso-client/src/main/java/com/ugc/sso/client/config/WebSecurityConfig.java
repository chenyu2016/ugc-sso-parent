package com.ugc.sso.client.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 登录配置
 * Created with IntelliJ IDEA.
 * User: ChenYu
 * Date: 2018/5/25
 */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorConfig interceptorConfig = new InterceptorConfig();
        InterceptorRegistration addInterceptor = registry.addInterceptor(interceptorConfig);
        // 排除配置
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login**");
        addInterceptor.excludePathPatterns("/addCookie");
        addInterceptor.excludePathPatterns("/registerPost");

        // 拦截配置
        addInterceptor.addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
