package com.koral.vister;

import com.koral.vister.model.Child;
import com.koral.vister.model.Parent;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties
@MapperScan("com.koral.vister.mapper")
public class App {
    static{
        System.out.println("加载类了");
    }
    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(App.class);
        application.setApplicationStartup(new BufferingApplicationStartup(10000));
// 11      application.setApplicationStartup(new FlightRecorderApplicationStartup());
//        application.addListeners(new Mylistener());
        ConfigurableApplicationContext context = application.run(args);

        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
    }

}

