package com.blysin.demo.common.io.nio;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * 非阻塞
 *
 * @author daishaokun
 * @date 2020/12/7
 */
@Slf4j
public class NioServer {
    private Selector selector;

    public static void main(String[] args) {
        NioServer nioServer = new NioServer();
        nioServer.start();
    }

    /**
     * selector调度器,这个才是nio的主体，负责管理所有链路跟事件
     * 处理所有的消息事件，包括链接创建，消息接收等。selector负责调度所有的链路，每条链接都需要注册到selector中管理
     * channel链路，服务器自身需要注册一条链路，监听ACCEPT事件
     * 当有客户端申请链接时，selector监听到事件，创建一个物理链接（channel），并且注册到调度器中，监听READ事件
     */
    public void start() {
        int port = 23004;
        //channel链接
        try (ServerSocketChannel channel = ServerSocketChannel.open();) {
            //生成selector调度器
            selector = Selector.open();

            //默认是阻塞式的，需要配置成非阻塞的
            channel.configureBlocking(false);
            //绑定ip，最大连接数1024
            channel.socket().bind(new InetSocketAddress(port), 1024);
            //配置调度器，声明监听的事件是accept，即客户端链接成功
            channel.register(selector, SelectionKey.OP_ACCEPT);

            log.info("服务端初始化成功，监听端口：{}", port);

            listen(channel, selector);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listen(ServerSocketChannel channel, Selector selector) {
        while (true) {
            try {
                //阻塞的监听事件，可以配置每次阻塞时间，默认永久监听，直到有客户端请求过来
                selector.select();

                //获取所有待处理事件，
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                SelectionKey event;
                while (iterator.hasNext()) {
                    event = iterator.next();
                    //为什么这里要remove
                    iterator.remove();

                    try {
                        if (event.isValid()) {
                            handler(event, selector);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        //如果发生了异常，就断开链接
                        if (event != null) {
                            IoUtil.close(event.channel());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handler(SelectionKey event, Selector selector) {

        //创建链接，服务端通道的acceptable事件
        if (event.isAcceptable()) {
            //获取创建这个事件的channel，实际上就是服务端链接
            ServerSocketChannel clientChannel = (ServerSocketChannel) event.channel();
            //accept成功后，意味着TCP三次握手成功，物理链接创建成功
            try {
                SocketChannel sc = clientChannel.accept();
                //非阻塞
                sc.configureBlocking(false);
                //将链接注册到调度器中
                sc.register(selector, SelectionKey.OP_READ);

                log.info("生成链接，客户端：{}，线程id：{}", sc.getRemoteAddress().toString(), Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //接受消息，客户端通道的read事件
        if (event.isReadable()) {
            //获取创建这个事件的channel，实际上就是客户端channel
            SocketChannel channel = (SocketChannel) event.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            try {
                int read = channel.read(buffer);
                if (read > 0) {
                    //nio是根据索引位置来获取数据的，flip标识初始化索引位置，也就是开始要读取了
                    buffer.flip();

                    byte[] data = new byte[buffer.remaining()];
                    //读取数据，读取的长度为buffer.remaining()，读取完后索引位置就是buffer.remaining()，继续调用get的话可以往下继续读取
                    buffer.get(data);
                    String body = StrUtil.trim(new String(data, StandardCharsets.UTF_8));

                    log.info("收到客户端来的消息：{}", body);
                    if ("exit".equals(body)) {
                        write(channel, "再见");
                        channel.close();
                    } else {
                        String info = "你好：" + body;
                        write(channel, info);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void write(SocketChannel channel, String body) {
        if (channel != null && StrUtil.isNotEmpty(body)) {
            body += "\n";
            byte[] data = body.getBytes(StandardCharsets.UTF_8);
            ByteBuffer buffer = ByteBuffer.allocate(data.length);
            buffer.put(data);
            buffer.flip();
            try {
                channel.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
