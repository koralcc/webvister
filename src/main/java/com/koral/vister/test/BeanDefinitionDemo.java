package com.koral.vister.test;

import com.koral.vister.model.Child;
import com.koral.vister.model.Parent;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanDefinitionDemo {
    public static void main(String[] args) {
        extracted();
    }

    private static void extracted() {
        String parentName = "parent";
        String childName = "child";
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue("name","tom");
        propertyValues.addPropertyValue("age","18");
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Parent.class,null,propertyValues);
        ChildBeanDefinition childBeanDefinition = new ChildBeanDefinition(parentName);
        childBeanDefinition.setParentName(parentName);
        childBeanDefinition.setBeanClass(Child.class);
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.registerBeanDefinition(parentName,rootBeanDefinition);
        applicationContext.registerBeanDefinition(childName,childBeanDefinition);
        applicationContext.refresh();
        Child bean = applicationContext.getBean(childName, Child.class);
        System.out.println(bean.toString());
    }
}
