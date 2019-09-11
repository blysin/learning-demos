package com.blysin.demo.netty.spring.server;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import static com.blysin.demo.netty.spring.server.NettyServer.GROUP;

/**
 * descripiton: 服务器的处理逻辑
 *
 * @author: www.iknowba.cn
 * @date: 2018/3/23
 * @time: 15:50
 * @modifier:
 * @since:
 */
@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<String> {
    private Integer parkId;

    /**
     * 读取消息通道
     *
     * @param context
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext context, String msg) throws Exception {
        Channel channel = context.channel();

        if (msg.startsWith("auth:")) {
            String auth = msg.substring(5);
            parkId = Integer.parseInt(auth);
            channel.writeAndFlush("[you]: 鉴权成功，你好，车场：" + parkId + "\n");
        } else {
            //当有用户发送消息的时候，对其他的用户发送消息
            for (Channel ch : GROUP) {
                if (ch == channel) {
                    ch.writeAndFlush("[you]: " + msg + "\n");
                } else {
                    ch.writeAndFlush("[" + channel.remoteAddress() + "]: " + msg + "\n");
                }
            }
        }

        log.info("[" + channel.remoteAddress() + "]: " + msg + "\n");
    }

    /**
     * 处理新加的消息通道
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (Channel ch : GROUP) {
            if (ch == channel) {
                ch.writeAndFlush("[" + channel.remoteAddress() + "] coming");
            }
        }
        GROUP.add(channel);
    }

    /**
     * 处理退出消息通道
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (Channel ch : GROUP) {
            if (ch == channel) {
                ch.writeAndFlush("[" + channel.remoteAddress() + "] leaving");
            }
        }
        GROUP.remove(channel);
    }

    /**
     * 在建立连接时发送消息
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        boolean active = channel.isActive();
        if (active) {
            System.out.println("[" + channel.remoteAddress() + "] is online");
        } else {
            System.out.println("[" + channel.remoteAddress() + "] is offline");
        }
        ctx.writeAndFlush("[server]: welcome\n");
    }

    /**
     * 退出时发送消息
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        if (!channel.isActive()) {
            System.out.println("[" + channel.remoteAddress() + "] is offline");
        } else {
            System.out.println("[" + channel.remoteAddress() + "] is online");
        }
    }

    /**
     * 异常捕获
     *
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[" + channel.remoteAddress() + "] leave the room");
        ctx.close().sync();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                if (parkId == null) {
                    ctx.writeAndFlush("无鉴权消息，连接中断！\r\n");
                    ctx.flush();
                    ctx.close();
                }
            }
        }

    }
}