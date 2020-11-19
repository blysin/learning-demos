package com.blysin.demo.netty.framework.base;


import com.alibaba.fastjson.JSON;
import com.blysin.demo.netty.framework.base.coder.ProtocolData;
import com.blysin.demo.netty.framework.constant.NettyConstant;
import com.blysin.demo.netty.business.domain.StpApiBaseReq;
import com.blysin.demo.netty.business.domain.StpApiBaseResp;
import com.blysin.demo.netty.framework.util.SpringBootBeanUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import static com.blysin.demo.netty.framework.base.NettyServer.CLIENTS;
import static com.blysin.demo.netty.framework.base.NettyServer.GROUP;
import static com.blysin.demo.netty.framework.constant.RespEnum.UNAUTHORIZED;

/**
 * 消息处理器
 *
 * @author daishaokun
 * @date 2019-11-29
 */
@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 车场id
     */
    private String parkId;
    /**
     * 过期时间，如果连接成功后车场没有认证，超过这个时间关闭连接
     */
    private long expireDate;

    private NettyService nettyService;

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

        if (parkId == null && System.currentTimeMillis() > expireDate) {
            log.info(NettyConstant.NO_AUTH);
            sendMsgAndCloseClient(context, NettyConstant.NO_AUTH);
            return;
        }

        if (StringUtils.isNotEmpty(msg)) {
            nettyService = getNettyService();
            StpApiBaseReq req = JSON.parseObject(msg, StpApiBaseReq.class);
            if (NettyConstant.AUTH.equals(req.getCmd())) {
                // 鉴权
                handleAuthRequest(context, msg, channel, nettyService, req);
            } else {
                log.info("netty消息处理-ID:{}，指令：{}", req.getReqId(), req.getCmd());
                handleBusinessMsg(context, nettyService, req);
            }
        }
    }

    /**
     * 处理鉴权请求
     *
     * @param context
     * @param msg
     * @param channel
     * @param nettyService
     * @param req
     */
    private void handleAuthRequest(ChannelHandlerContext context, String msg, Channel channel, NettyService nettyService, StpApiBaseReq req) {
        boolean result = auth(channel, req, nettyService);
        if (!result) {
            log.info("鉴权失败-ID:{}，消息体：{}", req.getReqId(), msg);
            sendMsgAndCloseClient(context, NettyConstant.AUTH_FAIL);
        } else {
            StpApiBaseResp resp = StpApiBaseResp.success(req.getReqId());
            resp.setMsg("鉴权成功");
            context.writeAndFlush(ProtocolData.buildMsg(resp));
        }
    }

    /**
     * 处理普通的业务请求
     *
     * @param context
     * @param nettyService
     * @param req
     */
    private void handleBusinessMsg(ChannelHandlerContext context, NettyService nettyService, StpApiBaseReq req) {
        req.setLotCode(parkId);
        handleMsg(context, nettyService, req);
    }


    /**
     * 直接处理上报消息
     *
     * @param context
     * @param service
     * @param req
     */
    private void handleMsg(ChannelHandlerContext context, NettyService service, StpApiBaseReq req) {
        StpApiBaseResp resp = service.handleMessage(req);
        if (resp != null) {
            context.writeAndFlush(ProtocolData.buildMsg(resp));
        }
    }

    /**
     * 响应数据给客户端并关闭连接
     *
     * @param context
     * @param msg
     */
    private void sendMsgAndCloseClient(ChannelHandlerContext context, String msg) {
        StpApiBaseResp resp = StpApiBaseResp.fail(UNAUTHORIZED, msg);
        context.writeAndFlush(ProtocolData.buildMsg(resp));
        context.flush();
        context.close();
    }

    /**
     * 鉴权
     *
     * @param channel
     * @param req
     * @param service
     */
    private boolean auth(Channel channel, StpApiBaseReq req, NettyService service) {
        StpApiBaseResp authResp = service.auth(req);
        if (authResp.isSuccess()) {
            log.info("车场：{}，认证成功,当前连接数：{}", req.getLotCode(), GROUP.size());
            Channel preChannel = CLIENTS.get(req.getLotCode());
            if (preChannel != null) {
                // 如果原先的连接还在，先关掉
                GROUP.remove(preChannel);
                preChannel.close();
            }
            CLIENTS.put(req.getLotCode(), channel);
            this.parkId = req.getLotCode();
            return true;
        }
        return false;
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
            //log.info("客户端接入[" + channel.remoteAddress() + "] ,当前连接数：{}", GROUP.size());
            parkId = null;
            // 过期时间设置为10秒后
            expireDate = System.currentTimeMillis() + 10_000;
        }
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
            if (parkId != null) {
                CLIENTS.remove(parkId);
                NettyService service = getNettyService();
                service.offline(parkId);
                log.info("客户端离线 [" + channel.remoteAddress() + "]，车场：{} ,当前连接数：{}", parkId, GROUP.size());
            } else {
                //log.info("客户端离线 [" + channel.remoteAddress() + "]，当前连接数：{}", GROUP.size());
            }
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
        log.info("[{}] 掉线", channel.remoteAddress());
        ctx.close().sync();
        if (!(e instanceof IOException)) {
            e.printStackTrace();
        }
    }

    /**
     * 时间触发器，关闭没有心跳的连接
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
                sendMsgAndCloseClient(ctx, parkId == null ? NettyConstant.NO_AUTH : NettyConstant.NO_HEARTBEAT);
            }
        }

    }

    private NettyService getNettyService() {
        if (nettyService == null) {
            synchronized (this) {
                if (nettyService == null) {
                    nettyService = SpringBootBeanUtils.getBean(NettyService.class);
                }
            }
        }
        return nettyService;
    }

}