package com.blysin.demo.netty.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * 事件处理器
 *
 * @author daishaokun
 * @date 2019-09-10
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private int counter;


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("通道激活>>>>>>>");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String body;
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            body = new String(req, StandardCharsets.UTF_8);
        } else if (msg instanceof String) {
            body = (String) msg;
        } else {
            body = null;
        }
        if (!StringUtils.isEmpty(body)) {
            System.out.println("receive client info: " + body);

            System.out.println("线程:" + Thread.currentThread() + " 收到客户端消息 : " + body + ". the counter is : " + ++counter);

            String sendContent = "hello client ,im server, this is u say:" + body + "\n";
            ByteBuf seneMsg = Unpooled.buffer(sendContent.length());
            seneMsg.writeBytes(sendContent.getBytes());

            ctx.writeAndFlush(seneMsg);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}