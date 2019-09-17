package com.blysin.demo.netty.lengthdecode.coder;

import com.blysin.demo.netty.lengthdecode.domain.Protocol;
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
public class ProtocolEncoder extends MessageToByteEncoder<Protocol> {

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
    protected void encode(ChannelHandlerContext channelHandlerContext, Protocol protocol, ByteBuf byteBuf) throws Exception {
        if (protocol != null) {
            byteBuf.writeByte(protocol.getFlag());
            byte[] data = protocol.getContent().getBytes(StandardCharsets.UTF_8);
            byteBuf.writeInt(data.length);
            //log.debug("发送的数据：{}", data);
            byteBuf.writeBytes(protocol.getContent().getBytes(StandardCharsets.UTF_8));
        }
    }
}