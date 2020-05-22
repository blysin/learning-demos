package com.blysin.demo.netty.spring.heartbeat;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 重连检测狗，当发现当前的链路不稳定关闭之后，进行12次重连
 */
@Sharable
@Slf4j
public abstract class ClientConnectionWatch extends ChannelInboundHandlerAdapter implements TimerTask, ChannelHandlerHolder {

    final Object LOCK = new Object();

    private final Bootstrap bootstrap;
    private final Timer timer;
    private final int port;

    private final String host;

    private volatile boolean reconnect = true;
    private int attempts;

    private Channel channel;

    public ClientConnectionWatch(Bootstrap bootstrap, Timer timer, int port, String host, boolean reconnect) {
        this.bootstrap = bootstrap;
        this.timer = timer;
        this.port = port;
        this.host = host;
        this.reconnect = reconnect;
    }

    /**
     * channel链路每次active的时候，将其连接的次数重新☞ 0
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        attempts = 0;
        ctx.fireChannelActive();
    }

    /**
     * 断线重连，重连时间按2^n次方累加，最多2^12秒
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("链接关闭");
        if (reconnect) {
            if (attempts < 12) {
                attempts++;
                //重连的间隔时间会越来越长
                int timeout = 2 << attempts;
                log.info("进行重连，重连次数：{}，时间间隔：{}s", attempts, timeout);
                timer.newTimeout(this, timeout, TimeUnit.SECONDS);
            }
        } else {
            log.error("重连次数太多，不再重试");
        }
        ctx.fireChannelInactive();
    }


    @Override
    public void run(Timeout timeout) throws Exception {
        ChannelFuture future;
        //bootstrap已经初始化好了，只需要将handler填入就可以了
        synchronized (LOCK) {
            bootstrap.handler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {

                    ch.pipeline().addLast(handlers());
                }
            });
            future = bootstrap.connect(host, port);
        }
        //future对象
        future.addListener((ChannelFutureListener) f -> {
            boolean succeed = f.isSuccess();

            //如果重连失败，则调用ChannelInactive方法，再次出发重连事件，一直尝试12次，如果失败则不再重连
            if (!succeed) {
                log.error("重连失败");
                f.channel().pipeline().fireChannelInactive();
            } else {
                log.info("重连成功");
                this.channel = future.channel();
            }
        });

    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}