package com.koral.vister.controller;

import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @Resource
    private Environment environment;


    @GetMapping("/index")
    public Long index() {
        HyperLogLogOperations hyperLogLogOperations = redisTemplate.opsForHyperLogLog();
        return hyperLogLogOperations.size(environment.getProperty("spring.application.name"));
    }
}
