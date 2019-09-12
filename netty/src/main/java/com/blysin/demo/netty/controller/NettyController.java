package com.blysin.demo.netty.controller;

import com.blysin.demo.netty.spring.heartbeat.HeartBeatsClient;
import com.blysin.demo.netty.spring.server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daishaokun
 * @date 2019-09-11
 */
@RestController
@RequestMapping("netty")
public class NettyController {
    @Autowired
    private NettyServer nettyServer;
    @Autowired
    private HeartBeatsClient heartBeatsClient;


    @RequestMapping("info")
    public String info() {
        return "netty:" + nettyServer.getChannel().localAddress() + " is open:" + nettyServer.getChannel().isOpen();
    }


    @RequestMapping("/server/group")
    public String group(String msg) {
        if (nettyServer.getChannel().isOpen()) {
            nettyServer.sendGroupMsg(msg);
            return "发送消息成功：" + msg;
        }
        return "netty启动失败，无法发送！";
    }

    @RequestMapping("/server/{parkId}")
    public String client(@PathVariable Integer parkId, String msg) {
        return nettyServer.sendParkMsg(parkId, msg);
    }

    @RequestMapping("/client/send")
    public String client(String msg) {
        boolean result = heartBeatsClient.sendMsg(msg);
        return result ? "发送成功" : "发送失败";
    }
}