package com.koral.vister.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Country {

    @Value("${country.name:中国}")
    private String name;

    @Resource
    private Province province;

    public void setProvince(Province province) {
        this.province = province;
    }
}
