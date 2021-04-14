package com.blysin.demo.common.io.aio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * https://github.com/winterzhcq/Java-IO/blob/master/aio/client/WriteHandler.java
 *
 * @author daishaokun
 * @date 2020/12/7
 */
@Slf4j
public class AioServer implements Runnable {
    public CountDownLatch latch;
    public AsynchronousServerSocketChannel channel;

    public AioServer(int port) {
        try {
            channel = AsynchronousServerSocketChannel.open();
            channel.bind(new InetSocketAddress(port));

            log.info("服务端初始化成功，监听端口：{}", port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        latch = new CountDownLatch(1);
        channel.accept();

        try {
            //CountDownLatch的等待任务关闭，实际上用while(true)一样
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
