package com.koral.vister.testnoaop;

import com.koral.vister.test.MyInstantiationObj;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    // 此发生在实例化之前：可以于此定义
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if("myInstantiationObj".equals(beanName)){
            MyInstantiationObj myInstantiationObj = new MyInstantiationObj();
            return myInstantiationObj;
        }
        return null;
    }
}
