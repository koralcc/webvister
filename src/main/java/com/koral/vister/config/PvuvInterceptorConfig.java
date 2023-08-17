package com.koral.vister.config;

import com.koral.vister.intercept.PvuvInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class PvuvInterceptorConfig  implements WebMvcConfigurer {
    @Resource
    private PvuvInterceptor pvuvInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pvuvInterceptor).addPathPatterns("/**");
    }

}
