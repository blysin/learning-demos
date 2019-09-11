package com.blysin.demo.netty.spring.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author daishaokun
 * @date 2019-09-11
 */
@Slf4j
public class ClientFutureListener implements ChannelFutureListener {
    private String ip;
    private int port;
    public ClientFutureListener(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (channelFuture.isSuccess()) {
            return;
        }
        final EventLoop loop = channelFuture.channel().eventLoop();
        loop.schedule(() -> {
            try {
                new NettyClient(ip, port).connect();
                log.info("netty客户端重启成功");
                Thread.sleep(500);
            } catch (Exception e) {
                log.error("netty客户端重启失败");
                e.printStackTrace();
            }
        }, 1L, TimeUnit.SECONDS);
    }
}