package com.blysin.demo.netty.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.logging.SocketHandler;

/**
 * netty服务端
 *
 * @author daishaokun
 * @date 2019-09-10
 */
@Component
@Slf4j
public class NettyServer {

    public void bind(int port) {
        //Reactor线程组
        //1用于服务端接受客户端的连接，可以在参数中声明线程个数，默认个数是内核数*2
        EventLoopGroup acceptorGroup = new NioEventLoopGroup();
        //2用于进行SocketChannel的网络读写
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //Netty用于启动NIO服务器的辅助启动类
            ServerBootstrap sb = new ServerBootstrap();
            //将两个NIO线程组传入辅助启动类中
            sb.group(acceptorGroup, workerGroup)
                    //设置创建的Channel为NioServerSocketChannel类型
                    .channel(NioServerSocketChannel.class)
                    //配置NioServerSocketChannel的TCP参数
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 发送缓冲大小
                    .option(ChannelOption.SO_SNDBUF,32*1024)
                    // 接收缓冲大小
                    .option(ChannelOption.SO_RCVBUF,32*1024)
                    //设置绑定IO事件的处理类
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //每次连接都会执行initChannel方法
                        @Override
                        protected void initChannel(SocketChannel channel) {
                            log.info("客户端请求接入");
                            //每次数据传输都会执行addLast里面的方法
                            //处理粘包/拆包问题
                            channel.pipeline().addLast(new LineBasedFrameDecoder(1024));//换行之前最多只能有1024个字节
                            channel.pipeline().addLast(new StringDecoder());

                            channel.pipeline().addLast(new ServerHandler());

                        }
                    });
            //绑定端口，同步等待成功（sync()：同步阻塞方法）
            //ChannelFuture主要用于异步操作的通知回调
            ChannelFuture cf = sb.bind(port).sync();

            //等待服务端监听端口关闭
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //退出，释放线程池资源
            acceptorGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}