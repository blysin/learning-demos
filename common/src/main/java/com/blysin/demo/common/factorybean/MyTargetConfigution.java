package com.blysin.demo.common.factorybean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean配置类
 *
 * @author daishaokun
 * @date 2021/2/27
 */
@Configuration
public class MyTargetConfigution {
    @Bean
    public MyTargetObjectFactoryBean helloTarget() {
        MyTargetObjectFactoryBean myTargetObjectFactoryBean = new MyTargetObjectFactoryBean();
        myTargetObjectFactoryBean.setName("hello");
        return myTargetObjectFactoryBean;
    }

    @Bean
    public MyTargetObjectFactoryBean worldTarget() {
        MyTargetObjectFactoryBean myTargetObjectFactoryBean = new MyTargetObjectFactoryBean();
        myTargetObjectFactoryBean.setName("world");
        return myTargetObjectFactoryBean;
    }
}
