package com.gowonco.house.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author gowonco
 * @date 2019-08-02 9:27
 */
@Configuration
public class WebMvcConf implements WebMvcConfigurer {
    @Autowired
    private AuthInterceptor authInterceptor;
    @Autowired
    private AuthActionInterceptor authActionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).excludePathPatterns("/static").addPathPatterns("/**");
        // 拦截需要登录的请求
        registry.addInterceptor(authActionInterceptor).addPathPatterns("/accounts/profile");

    }
}
