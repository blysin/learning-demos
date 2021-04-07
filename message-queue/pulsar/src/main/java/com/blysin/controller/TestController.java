package com.blysin.controller;

import com.blysin.config.TopicConstant;
import com.blysin.domain.MsgBody;
import io.github.majusko.pulsar.producer.PulsarTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author daishaokun
 * @date 2021/1/20
 */
@RestController
@Slf4j
public class TestController {
    @Autowired
    private PulsarTemplate stringPulsarTemplate;

    private AtomicInteger counter = new AtomicInteger(1);

    @PostMapping("push")
    public void push(String body) throws PulsarClientException {
        //log.info("推送消息");
        stringPulsarTemplate.send(TopicConstant.TOPIC_STRING, body + " -- " + counter.getAndIncrement());
    }

}
