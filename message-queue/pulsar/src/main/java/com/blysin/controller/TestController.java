package com.blysin.controller;

import com.blysin.config.TopicConstant;
import com.blysin.domain.MsgBody;
import io.github.majusko.pulsar.producer.PulsarTemplate;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daishaokun
 * @date 2021/1/20
 */
@RestController
public class TestController {
    @Autowired
    private PulsarTemplate stringPulsarTemplate;


    @PostMapping("push")
    public void push(String body) throws PulsarClientException {
        stringPulsarTemplate.send(TopicConstant.TOPIC_STRING, body);
    }

    @PostMapping("msg")
    public void msg(String name,Integer age) throws PulsarClientException {
        stringPulsarTemplate.send(TopicConstant.TOPIC_DOMAIN, new MsgBody(name, age));
    }
}
