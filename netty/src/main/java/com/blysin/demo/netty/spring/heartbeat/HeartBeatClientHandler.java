package com.blysin.demo.netty.spring.heartbeat;


import com.alibaba.fastjson.JSON;
import com.blysin.demo.netty.spring.req.AuthReq;
import com.blysin.demo.netty.spring.req.StpApiBaseReq;
import com.blysin.demo.netty.spring.req.StpApiBaseResp;
import com.blysin.demo.netty.util.MD5Util;
import com.blysin.demo.netty.util.SocketMessageUtils;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import sun.security.provider.MD5;

/**
 * @author daishaokun
 * @date 2019-09-11
 */
@Sharable
@Slf4j
public class HeartBeatClientHandler extends SimpleChannelInboundHandler<String> {
    private final String HEARTBEAT_CONTENT = "heartbeat\r\n";


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接服务端成功");
        ctx.fireChannelActive();

        // TODO 客户端连接建立成功，需要发送鉴权信息，10秒钟内如果没有发送鉴权信息连接会被中断
        auth(ctx);
    }

    /**
     * 上报鉴权消息
     *
     * @param ctx
     */
    private void auth(ChannelHandlerContext ctx) {
        int lotCode = 2264;
        String token = "b8f622c4137c437eaf070dc154d011ab";

        AuthReq authReq = new AuthReq();
        authReq.setLotCode(lotCode);
        authReq.setTs(System.currentTimeMillis());
        String auth = MD5Util.convert(authReq.getLotCode() + authReq.getTs() + token);
        authReq.setAuth(auth);

        StpApiBaseReq apiBaseReq = new StpApiBaseReq("auth", authReq);

        ctx.writeAndFlush(SocketMessageUtils.buildMsg(apiBaseReq));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("socket连接断开");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        if (StringUtils.isNotBlank(message)) {
            StpApiBaseResp resp = JSON.parseObject(message, StpApiBaseResp.class);
            if ("1002".equals(resp.getCode())) {
                // 认证失败
                log.error(resp.getMsg());
            } else {
                log.info("收到服务端消息：" + message);
                // TODO 处理客户端返回的数据
            }
        }
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
        // log.info("心跳连接");
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(HEARTBEAT_CONTENT);
                ctx.flush();
            }
        }
    }
}
