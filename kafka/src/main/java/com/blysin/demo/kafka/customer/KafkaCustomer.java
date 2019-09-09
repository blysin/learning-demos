package com.blysin.demo.kafka.customer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class KafkaCustomer {

    @KafkaListener(topics = {"TEST"})
    public void receive(String message) {
        System.out.println("TEST接受消息：" + message);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

//    @KafkaListener(topics = {"MSG"})
    public void receiveMsg(String message) {
        System.out.println("MSG接受消息：" + message);
    }
}
