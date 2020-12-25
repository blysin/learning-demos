package com.blysin.demo.netty.framework.config;

import com.blysin.demo.netty.framework.base.NettyServer;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 服务启动器
 *
 * @author daishaokun
 * @date 2019-09-10
 */
@Component
@Slf4j
public class ApplicationStarter implements ApplicationRunner {

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private NettyServer nettyServer;
    @Autowired
    private AppProperties appConstant;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        threadPoolExecutor.submit(() -> {
            log.info("启动netty服务端");
            InetSocketAddress address = new InetSocketAddress(appConstant.getHost(), appConstant.getPort());

            ChannelFuture channelFuture = nettyServer.bind(address);

            channelFuture.channel().closeFuture().syncUninterruptibly();
        });

        // 绑定netty注销事件
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            nettyServer.destroy();
        }));

    }
}