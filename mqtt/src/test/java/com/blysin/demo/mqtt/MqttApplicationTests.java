package com.blysin.demo.mqtt;

import com.blysin.demo.mqtt.config.MessageSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class MqttApplicationTests {
    @Autowired
    private MessageSender messageSender;

    @Test
    void contextLoads() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        messageSender.sendToMqtt("/park/123", "hello world");
        System.out.println("123");
        while (true) {

        }
    }

}
