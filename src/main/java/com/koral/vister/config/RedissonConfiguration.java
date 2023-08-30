package com.koral.vister.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson配置
 */
@ConfigurationProperties(
        prefix = "spring.redis"
)
@Configuration
public class RedissonConfiguration {

    private String host;
    private String port;
    private String password;
    private int database;

    @Bean(destroyMethod="shutdown")
    Redisson redisson(){
        String address = "redis://"+host+":"+port;
        Config config = new Config();
        config.useSingleServer().setAddress(address)
                .setConnectionPoolSize(30)
                .setConnectionMinimumIdleSize(10)
                .setPassword(password)
                .setDatabase(database);
        return (Redisson) Redisson.create(config);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDatabase(int database) {
        this.database = database;
    }
}
