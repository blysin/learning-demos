package com.blysin.demo.netty.framework.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 系统配置
 *
 * @author daishaokun
 * @date 2020/3/12
 */
@Component
@Data
public class AppProperties {
    @Value("${netty.host}")
    private String host;
    @Value("${netty.port}")
    private Integer port;
}