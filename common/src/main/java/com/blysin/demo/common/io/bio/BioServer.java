package com.blysin.demo.common.io.bio;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 阻塞bio实现socket
 *
 * @author daishaokun
 * @date 2020/12/7
 */
@Slf4j
public class BioServer {
    @Test
    public void start() {
        int port = 23003;
        try (ServerSocket server = new ServerSocket(port)) {
            log.info("服务启动，监听端口：{}", port);
            AtomicInteger atomicInteger = new AtomicInteger();
            ExecutorService pool = Executors.newCachedThreadPool(r -> new Thread(r, "bio-executor-" + atomicInteger.getAndIncrement()));

            while (true) {
                //启动服务器
                Socket client = server.accept();
                //创建线程来处理消息，也就是每个请求占用一条线程
                //如果不创建线程来处理，这个服务器就是单线程处理消息的
                pool.submit(new MessageHandler(client));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static class MessageHandler implements Runnable {
        private Socket client;

        public MessageHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            log.info("生成链接，客户端端口：{}，线程id：{}", client.getPort(), Thread.currentThread().getName());
            try (Scanner scanner = new Scanner(client.getInputStream()); PrintStream printStream = new PrintStream(client.getOutputStream(), true)) {
                scanner.useDelimiter("\n");
                printStream.println("你好:" + Thread.currentThread().getName());
                while (scanner.hasNext()) {
                    String msg = scanner.next();
                    msg = StringUtils.trimWhitespace(msg);

                    log.info("收到客户端来的消息：{}", msg);

                    if ("quit".equals(msg)) {
                        log.info("退出链接，客户端端口：{}，线程名称：{}", client.getPort(), Thread.currentThread().getName());
                        printStream.println("再见");
                        break;
                    } else {
                        printStream.println("你好：" + msg);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
