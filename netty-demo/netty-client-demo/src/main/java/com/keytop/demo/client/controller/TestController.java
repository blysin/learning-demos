package com.keytop.demo.client.controller;

import com.keytop.demo.client.config.AppProperties;
import com.keytop.demo.client.domain.StpApiBaseReq;
import com.keytop.demo.client.netty.HeartBeatsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试与服务器通讯
 *
 * @author daishaokun
 * @date 2019-09-29
 */
@RestController
@RequestMapping("netty")
public class TestController {
    @Autowired
    private HeartBeatsClient heartBeatsClient;
    @Autowired
    private AppProperties appProperties;

    @GetMapping("send")
    public String send(String msg) {
        StpApiBaseReq req = new StpApiBaseReq(appProperties.getLotCode(), "test", msg);
        boolean result = heartBeatsClient.sendMsg(req);
        return "发送结果：" + result;
    }
}