package com.blysin.demo.netty.business.controller;

import com.blysin.demo.netty.business.domain.StpApiBaseResp;
import com.blysin.demo.netty.framework.base.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * netty下行测试
 *
 * @author daishaokun
 * @date 2019-11-26
 */
@Slf4j
@RestController
@RequestMapping(value = "/netty")
public class NettyTestController {
    @Autowired
    private NettyServer nettyServer;

    @GetMapping("send")
    public Boolean send(String parkId, String msg) {
        StpApiBaseResp resp = StpApiBaseResp.success(RandomStringUtils.randomAlphabetic(32));
        resp.setData(msg);
        return nettyServer.sendParkMsg(parkId, resp);
    }
}