package com.blysin.demo.netty;
import com.blysin.demo.netty.socket.ChatNettyServer;
import org.junit.Test;

/**
 * @author daishaokun
 * @date 2019-09-10
 */
public class SocketServerTest {
    @Test
    public void start() {
        System.out.println("服务端启动！");
        new ChatNettyServer().bind(8988);
    }

}