package com.blysin.demo.mqtt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author daishaokun
 * @date 2019-11-20
 */
@Component
@Data
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {
    private String username;
    private String password;
    private String url;
    private Integer qos;
    private String downlinkTopic;
}