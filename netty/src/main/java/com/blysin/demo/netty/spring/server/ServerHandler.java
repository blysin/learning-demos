package com.blysin.demo.netty.spring.server;


import com.blysin.demo.netty.service.MessageService;
import com.blysin.demo.netty.framework.util.SocketMessageUtils;
import com.blysin.demo.netty.framework.util.SpringBootBeanUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import static com.blysin.demo.netty.spring.server.NettyServer.CLIENTS;
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
            log.info("鉴权请求");

            String auth = msg.substring(5);
            parkId = Integer.parseInt(auth);
            CLIENTS.put(parkId, channel);
            channel.writeAndFlush("[you]: 鉴权成功，你好，车场：" + parkId + "\n");
        } else if ("heartbeat".equalsIgnoreCase(msg)) {
            // 心跳不做处理
        } else {
            MessageService service = SpringBootBeanUtils.getBean(MessageService.class);
            if (service == null) {
                log.error("无法注入service对象");
            } else {
                String result = service.handleMessage(msg);
                if (StringUtils.isNotEmpty(result)) {
                    channel.writeAndFlush(SocketMessageUtils.buildMsg(result));
                }
            }
        }
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
            // TODO 车场状态改成在线
            log.info("channelActive[" + channel.remoteAddress() + "] is online,当前连接数：{}", GROUP.size());
        } else {
            log.info("channelActive[" + channel.remoteAddress() + "] is offline");
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
            // TODO 车场状态改成离线
            log.info("channelInactive [" + channel.remoteAddress() + "] 车场：{} is offline,当前连接数：{}", parkId, GROUP.size());
            if (parkId != null) {
                CLIENTS.remove(parkId);
            }
        } else {
            log.info("channelInactive[" + channel.remoteAddress() + "] is online");
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
        log.info("[" + channel.remoteAddress() + "] leave the room");
        ctx.close().sync();
    }

    /**
     * 时间触发器，用于处理10秒内没有心跳的连接
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                ctx.writeAndFlush("无心跳，连接中断！\r\n");
                ctx.flush();
                ctx.close();
            }
        }

    }
}