package com.blysin.demo.mqtt.controller;

import com.blysin.demo.mqtt.config.MessageSender;
import com.blysin.demo.mqtt.properties.MqttProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author daishaokun
 * @date 2019-11-20
 */
@RestController
@RequestMapping("mqtt")
public class MqttController {
    @Autowired
    private MqttProperties mqttProperties;
    @Autowired
    private MqttPahoMessageDrivenChannelAdapter messageProducer;
    @Autowired
    private MessageSender messageSender;

    private Set<String> parkids = new ConcurrentSkipListSet<>();

    @GetMapping("add")
    public String addTopic(String parkId) {
        if (parkids.contains(parkId)) {
            return "该车场已经订阅";
        } else {
            messageProducer.addTopic("/park/" + parkId);
            parkids.add(parkId);
            return "订阅成功";
        }
    }

    @GetMapping("send")
    public String send(String parkId, String msg) {
        messageSender.sendToMqtt("/park/" + parkId, msg);
        return "发送成功" + System.currentTimeMillis();
    }
}