package com.blysin.demo.netty.framework.base;


import com.blysin.demo.netty.framework.base.coder.ByteToProtoBufDecoder;
import com.blysin.demo.netty.framework.base.coder.ProtocolEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import static com.blysin.demo.netty.framework.base.coder.ByteToProtoBufDecoder.*;


/**
 * 注册消息管道
 *
 * @author daishaokun
 * @date 2019-11-29
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //管道注册handler
        socketChannel.pipeline()
                // 超时处理
                .addLast(new IdleStateHandler(80, 0, 0))
                // 长度解析器
                .addLast(new LengthFieldBasedFrameDecoder(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH, LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP))
                // 自己定义解析器
                .addLast(new ByteToProtoBufDecoder())
                // 自定义编码器
                .addLast(new ProtocolEncoder())
                // 最终的消息处理
                .addLast(new ServerHandler());
    }
}
