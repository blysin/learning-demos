package com.blysin.demo.netty.framework.base;


import com.blysin.demo.netty.framework.base.coder.ProtocolData;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.util.concurrent.ConcurrentHashMap;

/**
 * netty客户端
 *
 * @author daishaokun
 * @date 2019-11-29
 */
@Component
@Slf4j
public class NettyServer {
    /**
     * 所有的活动客户端
     */
    static final ChannelGroup GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 车场id与客户端映射map
     */
    static final ConcurrentHashMap<String, Channel> CLIENTS = new ConcurrentHashMap<>();

    /**
     * 服务端通道
     */
    private Channel channel;

    /**
     * EventLoopGroup事件处理器，通常需要处理客户端链接时间和io事件
     * bossGroup负责处理客户端链接事件，一般单线程就好
     */
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    /**
     * workerGroup负责处理io时间，线程数量可以自定义
     */
    private final EventLoopGroup workerGroup = new NioEventLoopGroup(1024);

    public ChannelFuture bind(InetSocketAddress address) {
        //启动 NIO 服务的辅助启动类
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        //配置 Channel
        bootstrap.channel(NioServerSocketChannel.class);
        //注册channel
        bootstrap.childHandler(new ServerChannelInitializer());
        //BACKLOG用于构造服务端套接字ServerSocket对象，
        // 标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024).childOption(ChannelOption.SO_KEEPALIVE, true);
        //是否启用心跳保活机制
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new LoggingHandler(LogLevel.INFO));
        // 下面这行什么作用？
        //bootstrap.localAddress(address);

        ChannelFuture channelFuture = null;
        try {
            channelFuture = bootstrap.bind(address).syncUninterruptibly();
            channel = channelFuture.channel();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != channelFuture && channelFuture.isSuccess()) {
                log.info("netty启动成功，监听地址：{}，端口：{}", address.getAddress(), address.getPort());
            } else {
                log.error("netty启动失败！");
            }
        }
        return channelFuture;
    }

    public void destroy() {
        if (null == channel) {
            return;
        }
        channel.close();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public Channel getChannel() {
        return channel;
    }

    /**
     * 给所有客户端发送消息
     *
     * @param msg
     */
    public void sendGroupMsg(String msg) {
        GROUP.writeAndFlush(ProtocolData.buildMsg(msg));
    }

    /**
     * 给指定的车场发消息
     *
     * @param parkId
     * @param msg
     * @return
     */
    public boolean sendParkMsg(String parkId, Object msg) {
        if (msg == null) {
            log.info("消息为空，不作处理");
            return false;
        }
        Channel channel = CLIENTS.get(parkId);
        if (channel == null) {
            log.info("车场没有上线，无法发送");
            return false;
        }
        if (!channel.isOpen()) {
            log.info("车场掉线，无法发送");
            return false;
        }

        ProtocolData data = ProtocolData.buildMsg(msg);
        log.info("下发车场指令，车场：{}，内容：{}", parkId, data);
        channel.writeAndFlush(data);
        return true;
    }

}
