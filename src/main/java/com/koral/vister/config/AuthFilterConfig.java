package com.koral.vister.config;

import com.koral.vister.filter.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.Filter;

@Configuration
public class AuthFilterConfig {

    @Resource
    private AuthFilter authFilter;

    @Bean
    public FilterRegistrationBean<Filter> authFilterRegisterBean(){
        FilterRegistrationBean<Filter> authFilterBean = new FilterRegistrationBean<>();
        authFilterBean.setFilter(authFilter);
        authFilterBean.addUrlPatterns("/*");
        return authFilterBean;
    }
}
