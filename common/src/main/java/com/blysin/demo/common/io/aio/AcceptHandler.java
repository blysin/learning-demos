package com.blysin.demo.common.io.aio;


import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 链接处理器
 *
 * @author daishaokun
 * @date 2020/12/7
 */
public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AioServer> {

    @Override
    public void completed(AsynchronousSocketChannel channel, AioServer attachment) {
        // 继续接受其它客户端请求
        attachment.channel.accept();
        // 创建新的buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //异步读  第三个参数为接收消息回调的业务Handler
        channel.read(buffer, buffer, new ReadHandler(channel));
    }

    @Override
    public void failed(Throwable exc, AioServer attachment) {

    }
}
