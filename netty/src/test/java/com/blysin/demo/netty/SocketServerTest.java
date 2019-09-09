package com.blysin.demo.netty;
import com.blysin.demo.netty.socket.NettyServer;
import com.blysin.demo.netty.socket.ServerHandler;
import org.junit.Test;

/**
 * @author daishaokun
 * @date 2019-09-10
 */
public class SocketServerTest {
    @Test
    public void start() {
        System.out.println("服务端启动！");
        new NettyServer().bind(8988);
    }

}