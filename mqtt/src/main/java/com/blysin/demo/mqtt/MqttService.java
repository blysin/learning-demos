package com.blysin.demo.mqtt;

/**
 * @author daishaokun
 * @date 2019-11-20
 */

import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

import static com.blysin.demo.mqtt.config.MqttConfig.CHANNEL_NAME_IN;

