package com.blysin.demo.netty.spring.heartbeat;


import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daishaokun
 * @date 2019-09-11
 */
@Sharable
@Slf4j
public class HeartBeatClientHandler extends SimpleChannelInboundHandler<String> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接服务端成功");
        ctx.fireChannelActive();

        // TODO 客户端连接建立成功，需要发送鉴权信息，10秒钟内如果没有发送鉴权信息连接可能会被中断
        String str = "auth:2013\r\n";
        ctx.writeAndFlush(str);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("socket连接断开");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        // TODO 处理客户端返回的数据
        log.info("收到服务端消息：" + message);
        if ("Heartbeat".equals(message)) {
            ctx.write("has read message from server");
            ctx.flush();
        }
        ReferenceCountUtil.release(message);
    }

    /**
     * 发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error("异常信息：{}", cause.getMessage());
    }

    /**
     * 时间触发器，心跳连接
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("心跳连接");
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush("heartbeat\r\n");
                ctx.flush();
            }
        }
    }
}
