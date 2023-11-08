package com.koral.vister.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyInstantiationObj {

    @Value("${myinstantiation.name:boy}")
    private String name;
}
