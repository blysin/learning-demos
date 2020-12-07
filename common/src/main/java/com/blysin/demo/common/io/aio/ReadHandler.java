package com.blysin.demo.common.io.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

/**
 * 消息处理器
 *
 * @author daishaokun
 * @date 2020/12/7
 */
public class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel clientChannel;

    public ReadHandler(AsynchronousSocketChannel clientChannel) {
        this.clientChannel = clientChannel;
    }

    @Override
    public void completed(Integer result, ByteBuffer buffer) {
        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        String body;
        body = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("客户端收到结果:" + body);
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        System.err.println("数据读取失败...");
        try {
            clientChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
