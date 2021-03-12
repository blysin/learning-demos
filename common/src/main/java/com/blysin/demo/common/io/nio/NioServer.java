package com.blysin.demo.common.io.nio;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.ThreadUtil;
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
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 非阻塞
 *
 * @author daishaokun
 * @date 2020/12/7
 */
@Slf4j
public class NioServer implements Runnable {
    private static Selector selector;
    static{
        //生成selector调度器
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 23004;
        new Thread(new NioServer(port)).start();
        while (true) {

        }
    }

    /**
     * selector调度器,这个才是nio的主体，负责管理所有链路跟事件
     * 处理所有的消息事件，包括链接创建，消息接收等。selector负责调度所有的链路，每条链接都需要注册到selector中管理
     * channel链路，服务器自身需要注册一条链路，监听ACCEPT事件
     * 当有客户端申请链接时，selector监听到事件，创建一个物理链接（channel），并且注册到调度器中，监听READ事件
     */
    public NioServer(int port) {
        //channel链接
        try {
            ServerSocketChannel channel = ServerSocketChannel.open();
            //默认是阻塞式的，需要配置成非阻塞的
            channel.configureBlocking(false);
            //绑定ip，最大连接数1024
            channel.socket().bind(new InetSocketAddress(port), 1024);
            //配置调度器，声明监听的事件是accept，即客户端链接成功
            channel.register(selector, SelectionKey.OP_ACCEPT);

            log.info("服务端初始化成功，监听端口：{}", port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static final AtomicInteger atomicInteger = new AtomicInteger();
    /**
     * 线程池，maximumPoolSize就是最大的并发处理数量，超过等待队列就会直接抛异常，
     * 线程的创建逻辑：1、core，2、队列，3、max
     */
    private static ExecutorService pool = new ThreadPoolExecutor(
            //核心，也就是最小线程数
            1,
            //最大线程数，如果超过核心，且队列已满，才会新增线程，最大只能到这个值
            3,
            //非核心线程的存活时间，超过时间就会被回收
            0L, TimeUnit.MILLISECONDS,
            //线程达到超过核心线程后的等待队列，
            new ArrayBlockingQueue<>(10),
            //线程工厂
            r -> new Thread(r, "nio-executor-" + atomicInteger.getAndIncrement()),
            //拒绝策略，有当前线程执行策略，拒绝策略，抛弃策略（没有异常），抛弃旧任务策略
            new ThreadPoolExecutor.AbortPolicy());


    @Override
    public void run() {
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
                            handler(event);
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

    private AtomicInteger count = new AtomicInteger();

    /**
     * 处理事件（实际上是扫描通道状态）
     * 状态类型1：ServerSocketChannel的accept状态，标识有客户端发起链接请求
     * 状态类型2：SocketChannel的readable状态，标识客户端有消息过来
     *
     * @param event
     */
    private void handler(SelectionKey event) {
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
            //这里会阻塞，所以需要另外启动线程处理
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

                    if (StrUtil.isNotEmpty(body)) {
                        // 只处理消息不为空的请求
                        try {
                            pool.submit(() -> handle(channel, body));
                        } catch (RejectedExecutionException e) {
                            //被拒绝了，就返回错误消息给客户端
                            write(channel, "服务繁忙，请稍后再试");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handle(SocketChannel channel, String body) {
        log.info("收到客户端来的消息：{}，处理线程：{}", body, Thread.currentThread().getName());

        //阻塞一下，判断是不是单线程处理的
        //结果，这里确实是单线程的！！！！必须另外起线程去处理业务
        if (count.getAndIncrement() % 3 == 0) {
            log.info("休眠");
            ThreadUtil.safeSleep(5000);
        }

        if ("exit".equals(body)) {
            write(channel, "再见");
            IoUtil.close(channel);
        } else {
            String info = "你好：" + body;
            write(channel, info);
        }
    }

    private void write(SocketChannel channel, String body) {
        if (channel != null && StrUtil.isNotEmpty(body) && channel.isConnected()) {
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
