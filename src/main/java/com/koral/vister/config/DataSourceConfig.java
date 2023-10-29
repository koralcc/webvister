package com.koral.vister.config;

import com.koral.vister.model.PreOrder;
import com.koral.vister.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataSourceConfig {

	@Bean
    public PreOrder preOrder() {
        PreOrder preOrder = new PreOrder();
        return preOrder;
    }
    @Bean(name = "user")
    public User user() {
        PreOrder preOrder = preOrder();
        return new User(preOrder);
    }
}