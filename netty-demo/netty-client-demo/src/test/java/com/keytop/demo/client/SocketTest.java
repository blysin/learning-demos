package com.keytop.demo.client;

import com.alibaba.fastjson.JSON;
import com.keytop.demo.client.domain.AuthReq;
import com.keytop.demo.client.domain.StpApiBaseReq;
import com.keytop.demo.client.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author daishaokun
 * @date 2019-11-25
 */
public class SocketTest {
    String IP_ADDR = "101.132.89.22";
    int PORT = 18899;

    @Test
    public void socket() {
        Socket socket = null;
        try {
            //创建一个流套接字并将其连接到指定主机上的指定端口号
            socket = new Socket(IP_ADDR, PORT);

            //读取服务器端数据
            DataInputStream input = new DataInputStream(socket.getInputStream());
            //向服务器端发送数据
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String lotCode = "2130";
            String token = "16863bff362d49768e5174c6e56f3277";

            AuthReq authReq = new AuthReq();
            authReq.setLotCode(lotCode);
            authReq.setTs(System.currentTimeMillis());

            String auth = MD5Util.convert(authReq.getLotCode() + authReq.getTs().toString() + token);

            authReq.setAuth(auth);

            StpApiBaseReq apiBaseReq = new StpApiBaseReq(lotCode, "auth", authReq);

            send(out, apiBaseReq);

            System.out.println("发送成功");

            String str = "{\"cmd\":\"auth\",\"data\":{\"auth\":\"57e79164aa128f1c3f5fb5c6c30af8ba\",\"lotCode\":226,\"ts\":1574676690598},\"reqId\":\"UgkKeLjfBU3m\"}";

            TimeUnit.SECONDS.sleep(1);

            apiBaseReq = new StpApiBaseReq(lotCode, "test", authReq);
            send(out, apiBaseReq);
            System.out.println("发送成功2");


            byte type = input.readByte();
            System.out.println(type);
            int length = input.readInt();
            System.out.println(length);
            byte[] data = new byte[length];

            int ret = input.read(data);
            System.out.println("服务端响应：" + new String(data));

            while (true) {
                if (1 == 2) {
                    break;
                }
            }

            out.close();
            input.close();
        } catch (Exception e) {
            System.out.println("客户端异常:" + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    socket = null;
                    System.out.println("客户端 finally 异常:" + e.getMessage());
                }
            }
        }

    }

    @Test
    public void heartbeatTest() {
        Socket socket = null;
        try {
            //创建一个流套接字并将其连接到指定主机上的指定端口号
            socket = new Socket(IP_ADDR, PORT);

            //读取服务器端数据
            DataInputStream input = new DataInputStream(socket.getInputStream());
            //向服务器端发送数据
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String lotCode = "226";
            String token = "8e67ac756b9220ce10ae698ea412289";

            //鉴权请求
            AuthReq authReq = new AuthReq();
            authReq.setLotCode(lotCode);
            authReq.setTs(System.currentTimeMillis());
            String auth = MD5Util.convert(authReq.getLotCode() + authReq.getTs() + token);
            authReq.setAuth(auth);
            StpApiBaseReq apiBaseReq = new StpApiBaseReq(lotCode, "auth", authReq);

            send(out, apiBaseReq);
            System.out.println("鉴权请求发送成功");


            TimeUnit.SECONDS.sleep(8);

            //心跳
            heartbeat(out);


            byte type = input.readByte();
            System.out.println(type);
            int length = input.readInt();
            System.out.println(length);
            byte[] data = new byte[length];

            int ret = input.read(data);
            System.out.println("服务端响应：" + new String(data));


            out.close();
            input.close();
        } catch (Exception e) {
            System.out.println("客户端异常:" + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    socket = null;
                    System.out.println("客户端 finally 异常:" + e.getMessage());
                }
            }
        }

    }

    private void heartbeat(DataOutputStream out) throws IOException {
        //写入消息类型
        out.writeByte(0xA);
        //写入消息长度
        out.writeInt(0);
    }

    private void send(DataOutputStream out, StpApiBaseReq apiBaseReq) throws IOException {
        String content = JSON.toJSONString(apiBaseReq);
        System.out.println("发送请求：" + content);


        //写入消息类型
        out.writeByte(0xA);
        //写入消息长度
        out.writeInt(content.length());
        //写入消息体
        out.write(content.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void int2Bytes() {
        String str = "hello worldhello worldhello worldhello worldhello worldhello worldhello worldhello worldhello worldhello worldhello worldhello world";
        str = StringUtils.EMPTY;
        System.out.println("字符长度：" + str.length());
        byte b = 0xB;
        System.out.println(b);
        System.out.println(Arrays.toString(ByteConvert.int2BytesBE(str.length())));
    }

    @Test
    public void bytes2Int() {
        byte[] bytes = new byte[]{0, 0, 0, -124};
        System.out.println(ByteConvert.bytes2IntBE(bytes));
    }

}