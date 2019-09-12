package com.blysin.demo.netty;

import com.blysin.demo.netty.socket.NettyClient;
import com.blysin.demo.netty.spring.heartbeat.HeartBeatsClient;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * @author daishaokun
 * @date 2019-09-10
 */
public class SocketClientTest {
    private String host = "127.0.0.1";
    private int port = 8988;
    @Test
    public void nettyClient() {
        NettyClient client = new NettyClient();
        client.connect(host, port);
    }

    @Test
    public void send2() throws IOException {
        send("hello world ","hello world ","hello world ","hello world ");
    }

    public void send(String... message){
        Socket socket = null;
        try {
            socket =  new Socket(host, port);

            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            int i = 0;
            for (String s : message) {
                out.println(s + i++);
                out.flush();

                System.out.println("server:" + in.readLine());
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    public void heartbeatClientTest() {
        for (int i = 0; i < 300; i++) {
            HeartBeatsClient client = new HeartBeatsClient();
            new Thread(() -> {
                try {
                    client.connect("10.1.10.30", 8899);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        while (true) {

        }
    }

}