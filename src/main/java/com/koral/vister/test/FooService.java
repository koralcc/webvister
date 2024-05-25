package com.koral.vister.test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: chejianyun
 * @Date: 2024/5/25
 */
@Component
public class FooService {

    private FooRepository fooRepository;

    @Autowired
    public FooService(FooRepository fooRepository) {
        this.fooRepository = fooRepository;
    }
}
