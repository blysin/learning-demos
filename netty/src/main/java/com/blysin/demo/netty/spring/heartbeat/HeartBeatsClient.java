package com.blysin.demo.netty.spring.heartbeat;


import com.blysin.demo.netty.framework.util.SocketMessageUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 创建一个netty客户端。链接建立成功后需要向服务器发送鉴权消息{@link HeartBeatClientHandler#channelActive}，否则服务端不接受任何请求，且可能主动关闭链接。
 * 每10秒会发送一次心跳{@link HeartBeatClientHandler#userEventTriggered}，没有心跳会被服务端切断链接
 * 调用{@link #sendMsg}可以向服务端发送消息
 * 方法{@link HeartBeatClientHandler#channelRead0}用户处理服务器响应的数据
 *
 * @author daishaokun
 * @date 2019-09-12
 */
@Component
@Slf4j
public class HeartBeatsClient {

    private final HashedWheelTimer timer = new HashedWheelTimer();

    private final EventLoopGroup group = new NioEventLoopGroup();

    private ClientConnectionWatch connectionWatch;

    private Bootstrap boot;

    public void connect(String host, int port) throws Exception {

        boot = new Bootstrap();
        boot.group(group).channel(NioSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO));

        connectionWatch = new ClientConnectionWatch(boot, timer, port, host, true) {

            @Override
            public ChannelHandler[] handlers() {
                return new ChannelHandler[]{this, new IdleStateHandler(0, 8, 0, TimeUnit.SECONDS),
                        // 单次数据量不超过10KB
                        new LineBasedFrameDecoder(10240), new StringDecoder(), new StringEncoder(), new HeartBeatClientHandler()};
            }
        };

        ChannelFuture future;
        try {
            synchronized (connectionWatch.LOCK) {
                boot.handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(connectionWatch.handlers());
                    }
                });

                future = boot.connect(host, port);
                connectionWatch.setChannel(future.channel());
            }

            // 以下代码在synchronized同步块外面是安全的
            future.sync();
        } catch (Throwable t) {
            throw new Exception("connects to  fails", t);
        }
    }

    public void destroy() {
        if (null == connectionWatch.getChannel()) {
            return;
        }
        connectionWatch.getChannel().close();
        group.shutdownGracefully();
    }

    /**
     * 发送消息到服务端
     *
     * @param body
     */
    public boolean sendMsg(String body) {
        if (StringUtils.isBlank(body)) {
            log.info("消息内容为空，不发送");
            return false;
        }
        if (!connectionWatch.getChannel().isOpen()) {
            log.info("服务器链接失败，消息无法发送");
            return false;
        }
        connectionWatch.getChannel().writeAndFlush(SocketMessageUtils.buildMsg(body));
        return true;
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        HeartBeatsClient client = new HeartBeatsClient();
        new Thread(() -> {
            try {
                client.connect("127.0.0.1", 8899);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

}