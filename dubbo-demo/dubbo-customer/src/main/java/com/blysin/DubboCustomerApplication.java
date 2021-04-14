package com.blysin;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author daishaokun
 * @date 2020/12/25
 */
@SpringBootApplication
@EnableDubbo
public class DubboCustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DubboCustomerApplication.class, args);
    }
}
