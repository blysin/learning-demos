package com.blysin.demo.netty.spring;

import com.blysin.demo.netty.spring.server.NettyServer;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author daishaokun
 * @date 2019-09-11
 */
@Component
public class NettyRunner implements ApplicationRunner {
    @Autowired
    private NettyServer nettyServer;
    @Value("${netty.host}")
    private String host;
    @Value("${netty.port}")
    private Integer port;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        InetSocketAddress address = new InetSocketAddress(host, port);

        ChannelFuture channelFuture = nettyServer.bind(address);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> nettyServer.destroy()));
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }
}