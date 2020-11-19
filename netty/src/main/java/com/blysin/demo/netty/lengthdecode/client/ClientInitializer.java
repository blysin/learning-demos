package com.blysin.demo.netty.lengthdecode.client;


import com.blysin.demo.netty.lengthdecode.coder.ByteToProtoBufDecoder;
import com.blysin.demo.netty.lengthdecode.coder.ProtocolEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import static com.blysin.demo.netty.lengthdecode.coder.ByteToProtoBufDecoder.*;

/**
 * descripiton: 客户端处理初始化
 *
 * @author: www.iknowba.cn
 * @date: 2018/3/23
 * @time: 16:55
 * @modifier:
 * @since:
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //管道注册handler
        socketChannel.pipeline()
                // 长度解析器
                .addLast(new LengthFieldBasedFrameDecoder(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH, LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP))
                // 自己定义解析器
                .addLast(new ByteToProtoBufDecoder())
                // 自定义编码器
                .addLast(new ProtocolEncoder())
                // 最终的消息处理
                .addLast(new ClientHandler());
    }
}