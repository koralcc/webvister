package com.koral.vister.testnoaop;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(MyConfigurationProperty.class)
public class InjectDemo {
    private MyConfigurationProperty myConfigurationProperty;
    private Hello hell;
    private Hallo hall;
    public InjectDemo(Hello hell) {
        this.hell = hell;
    }

    @Autowired
    public void setHall(Hallo hall) {
        this.hall = hall;
    }

    @Component
    static class Hello{

    }

    @Component
    static class Hallo{

    }
}
