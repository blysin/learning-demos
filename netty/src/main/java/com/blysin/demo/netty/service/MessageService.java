package com.blysin.demo.netty.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author daishaokun
 * @date 2019-09-12
 */
@Service
@Slf4j
public class MessageService {

    public String handleMessage(String msg) {
        log.info("处理消息:{}", msg);
        return "处理成功";
    }
}