package com.blysin.demo.netty.spring.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * descripiton: 客户端
 *
 * @author: www.iknowba.cn
 * @date: 2018/3/23
 * @time: 16:40
 * @modifier:
 * @since:
 */
public class NettyClient {

    private String host;

    private int port;


    protected final HashedWheelTimer timer = new HashedWheelTimer();

    public NettyClient(String ip, int port) {
        this.host = ip;
        this.port = port;
    }

    public void connect() throws IOException {
        //设置一个多线程循环器
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //启动附注类
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        //指定所使用的NIO传输channel
        bootstrap.channel(NioSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO));
        ;

        final ClientConnectionWatcher watcher = new ClientConnectionWatcher(bootstrap, timer, port, host, true) {
            @Override
            public ChannelHandler[] handlers() {
                return new ChannelHandler[]{this, new LineBasedFrameDecoder(1024), new IdleStateHandler(0, 3, 0, TimeUnit.SECONDS), new StringDecoder(), new StringEncoder(), new ClientHandler()};
            }
        };


        try {
            ChannelFuture channelFuture = null;
            synchronized (bootstrap) {
                bootstrap.handler(new ChannelInitializer<Channel>() {

                    //初始化channel
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(watcher.handlers());
                    }
                });

                channelFuture = bootstrap.connect(host, port);
            }

            channelFuture.sync();

            //连接服务
            Channel channel = channelFuture.channel();

            channel.closeFuture().syncUninterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("启动");
        new NettyClient("127.0.0.1", 8899).connect();
        while (true) {

        }
    }
}