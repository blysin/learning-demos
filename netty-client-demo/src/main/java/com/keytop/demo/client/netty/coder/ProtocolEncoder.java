package com.keytop.demo.client.netty.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 自定义编码器
 *
 * @author daishaokun
 * @date 2019-09-17
 */
@Slf4j
public class ProtocolEncoder extends MessageToByteEncoder<ProtocolData> {

    /**
     * 数据传输格式：请求头（5字节）+请求体
     * 请求头第一位表示消息类型：心跳或业务数据
     * 请求头后四位表示请求体长度，长度采用大端序
     *
     * @param channelHandlerContext
     * @param protocol
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ProtocolData protocol, ByteBuf byteBuf) throws Exception {
        if (protocol != null) {
            byte[] data = protocol.getContent().getBytes(StandardCharsets.UTF_8);
            //写入首字节，消息类型
            byteBuf.writeByte(protocol.getFlag());
            //写入消息长度
            byteBuf.writeInt(data.length);
            //写入消息体
            byteBuf.writeBytes(protocol.getContent().getBytes(StandardCharsets.UTF_8));
        }
    }
}