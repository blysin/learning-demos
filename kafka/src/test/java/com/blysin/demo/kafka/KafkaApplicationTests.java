package com.blysin.demo.kafka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaApplicationTests {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    public void contextLoads() {
        for (int i = 0; i < 5000000; i++) {
            kafkaTemplate.send("TEST", "hello world : " + (i + 5000000));
        }
    }

}
