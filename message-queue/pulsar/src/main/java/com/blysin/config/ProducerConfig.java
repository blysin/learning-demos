package com.blysin.config;

import com.blysin.domain.MsgBody;
import io.github.majusko.pulsar.producer.ProducerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author daishaokun
 * @date 2021/1/20
 */
@Configuration
public class ProducerConfig {
    @Bean
    public ProducerFactory producerFactory(){
        return new ProducerFactory()
                //
                .addProducer(TopicConstant.TOPIC_DOMAIN, MsgBody.class)
                //
                .addProducer(TopicConstant.TOPIC_STRING, String.class);
    }
}
