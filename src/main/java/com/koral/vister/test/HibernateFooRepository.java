package com.koral.vister.test;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: chejianyun
 * @Date: 2024/5/25
 */
@Primary
@Component
public class HibernateFooRepository extends FooRepository {

//    public HibernateFooRepository(SessionFactory sessionFactory) {
//        // ...
//    }
}