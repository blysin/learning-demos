package com.blysin.service;

import com.blysin.config.TopicConstant;
import com.blysin.domain.MsgBody;
import io.github.majusko.pulsar.annotation.PulsarConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.stereotype.Service;

/**
 * @author daishaokun
 * @date 2021/1/20
 */
@Service
@Slf4j
public class ConsumerService {

    @PulsarConsumer(topic = TopicConstant.TOPIC_STRING, clazz = String.class, subscriptionType = SubscriptionType.Shared)
    public void stringMsg(String body) {
        log.info("收到消息 msg :{}", body);
    }

    //@PulsarConsumer(topic = TopicConstant.TOPIC_DOMAIN, clazz = MsgBody.class)
    public void domainMsg(MsgBody body) {
        log.info("domain msg :{}", body);
        if (body.getAge() == 10) {
            throw new RuntimeException("丢弃");
        }
    }
}
