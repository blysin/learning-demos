package com.keytop.demo.client.netty;


import com.alibaba.fastjson.JSON;
import com.keytop.demo.client.domain.AuthReq;
import com.keytop.demo.client.domain.StpApiBaseReq;
import com.keytop.demo.client.domain.StpApiBaseResp;
import com.keytop.demo.client.netty.coder.ProtocolData;
import com.keytop.demo.client.util.MD5Util;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author daishaokun
 * @date 2019-09-11
 */
@Sharable
@Slf4j
public class HeartBeatClientHandler extends SimpleChannelInboundHandler<String> {

    private String lotCode;
    private String token;

    public HeartBeatClientHandler(String lotCode, String token) {
        this.lotCode = lotCode;
        this.token = token;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接服务端成功");
        ctx.fireChannelActive();

        // 客户端连接建立成功，需要发送鉴权信息，如果超时没有发送鉴权信息连接会被中断
        auth(ctx);
    }

    /**
     * 上报鉴权消息
     *
     * @param ctx
     */
    private void auth(ChannelHandlerContext ctx) {
        // TODO 连接鉴权
        AuthReq authReq = new AuthReq();
        authReq.setLotCode(lotCode);
        authReq.setTs(System.currentTimeMillis());
        String auth = MD5Util.convert(authReq.getLotCode() + authReq.getTs().toString() + token);
        authReq.setAuth(auth);

        StpApiBaseReq apiBaseReq = new StpApiBaseReq(lotCode, "auth", authReq);

        ctx.writeAndFlush(ProtocolData.buildMsg(apiBaseReq));
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
                ctx.writeAndFlush(ProtocolData.HEARTBEAT_PROTOCOL);
                ctx.flush();
            }
        }
    }
}
