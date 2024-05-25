package com.koral.vister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Description:
 * @Author: chejianyun
 * @Date: 2024/5/25
 */
@SpringBootApplication
//@MapperScan("com.koral.vister.mapper")
@EnableConfigurationProperties
public class App {
    static {
        System.out.println("加载类了");
    }

    public static void main(String[] args) {

//        String s = new String("aaa");

        SpringApplication application = new SpringApplication(App.class);
//        application.setApplicationStartup(new BufferingApplicationStartup(10000));
// 11      application.setApplicationStartup(new FlightRecorderApplicationStartup());
//        application.addListeners(new Mylistener());
        ConfigurableApplicationContext context = application.run(args);

        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
    }

}

