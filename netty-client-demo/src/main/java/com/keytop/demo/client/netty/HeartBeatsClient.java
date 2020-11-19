package com.keytop.demo.client.netty;


import com.alibaba.fastjson.JSON;
import com.keytop.demo.client.netty.coder.ByteToProtoBufDecoder;
import com.keytop.demo.client.netty.coder.ProtocolData;
import com.keytop.demo.client.netty.coder.ProtocolEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.keytop.demo.client.netty.coder.ByteToProtoBufDecoder.*;


/**
 * 创建一个netty客户端。链接建立成功后需要向服务器发送鉴权消息{@link HeartBeatClientHandler#channelActive}，否则服务端不接受任何请求，且可能主动关闭链接。
 * 每隔一段时间会发送一次心跳{@link HeartBeatClientHandler#userEventTriggered}，没有心跳会被服务端切断链接
 * 修改心跳时间在56行，IdleStateHandler的第二个构造参数中
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

    public void connect(String host, int port, String lotCode, String token) throws Exception {

        boot = new Bootstrap();
        boot.group(group).channel(NioSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO));

        connectionWatch = new ClientConnectionWatch(boot, timer, port, host, true) {

            @Override
            public ChannelHandler[] handlers() {
                return new ChannelHandler[]{this,
                        // 定时消息，每60秒写入一次心跳数据
                        new IdleStateHandler(0, 60, 0, TimeUnit.SECONDS),
                        // 长度解析器
                        new LengthFieldBasedFrameDecoder(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH, LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP),
                        // 自己定义解析器
                        new ByteToProtoBufDecoder(),
                        // 自定义编码器
                        new ProtocolEncoder(),
                        // 心跳连接，连接成功后会发送鉴权请求
                        new HeartBeatClientHandler(lotCode, token)};
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
    public boolean sendMsg(Object body) {
        if (body == null) {
            log.info("消息内容为空，不发送");
            return false;
        }
        if (!connectionWatch.getChannel().isOpen()) {
            log.info("服务器链接失败，消息无法发送");
            return false;
        }
        log.info("请求参数：\n{}", JSON.toJSONString(body, false));
        connectionWatch.getChannel().writeAndFlush(ProtocolData.buildMsg(body));
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
                //client.connect("192.168.56.99", 18899);
                //client.connect("10.1.1.30", 8899);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

}