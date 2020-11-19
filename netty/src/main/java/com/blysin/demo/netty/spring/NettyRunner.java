package com.blysin.demo.netty.spring;

import com.blysin.demo.netty.spring.heartbeat.HeartBeatsClient;
import com.blysin.demo.netty.spring.server.NettyServer;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author daishaokun
 * @date 2019-09-11
 */
@Component
@Slf4j
public class NettyRunner implements ApplicationRunner {
    @Autowired
    private NettyServer nettyServer;
    @Value("${netty.host}")
    private String host;
    @Value("${netty.port}")
    private Integer port;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private HeartBeatsClient heartBeatsClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        threadPoolExecutor.submit(() -> {
            log.info("服务端启动");
            InetSocketAddress address = new InetSocketAddress(host, port);

            ChannelFuture channelFuture = nettyServer.bind(address);

            channelFuture.channel().closeFuture().syncUninterruptibly();
        });

        threadPoolExecutor.submit(() -> {
            try {
                // 等待服务端启动成功
                TimeUnit.SECONDS.sleep(5);
                log.info("客户端启动");
                heartBeatsClient.connect(host, port);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 绑定注销事件
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            heartBeatsClient.destroy();
            nettyServer.destroy();
        }));
    }
}