package com.blysin.demo.netty.spring.server;


import com.blysin.demo.netty.framework.util.SocketMessageUtils;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * descripiton:服务端
 *
 * @author: www.iknowba.cn
 * @date: 2018/3/23
 * @time: 15:37
 * @modifier:
 * @since:
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
    static final ConcurrentHashMap<Integer, Channel> CLIENTS = new ConcurrentHashMap<>();

    private Channel channel;

    /**
     * EventLoopGroup是用来处理IO操作的多线程事件循环器，负责接收客户端连接线程
     */
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    /**
     * 负责处理客户端i/o事件、task任务、监听任务组
     */
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    public ChannelFuture bind(InetSocketAddress address) {
        //启动 NIO 服务的辅助启动类
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        //配置 Channel
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ServerInitializer());
        //BACKLOG用于构造服务端套接字ServerSocket对象，
        // 标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024).childOption(ChannelOption.SO_KEEPALIVE, true);

        //是否启用心跳保活机制
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new LoggingHandler(LogLevel.INFO));
        // 下面这行什么作用？
        bootstrap.localAddress(address);


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
        GROUP.writeAndFlush("[ 群消息 ] " + SocketMessageUtils.buildMsg(msg));
    }

    public String sendParkMsg(Integer parkId, String msg) {
        if (StringUtils.isBlank(msg)) {
            return "消息为空，不作处理";
        }
        Channel channel = CLIENTS.get(parkId);
        if (channel == null) {
            return "车场没有上线，无法发送";
        }
        if (!channel.isOpen()) {
            return "车场掉线，无法发送";
        }

        channel.writeAndFlush(SocketMessageUtils.buildMsg(msg));
        return "success";
    }

}