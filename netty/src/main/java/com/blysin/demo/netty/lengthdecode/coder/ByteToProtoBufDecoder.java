package com.blysin.demo.netty.lengthdecode.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 自定义解析器
 *
 * @author daishaokun
 * @date 2019-09-17
 */
@Slf4j
public class ByteToProtoBufDecoder extends SimpleChannelInboundHandler<ByteBuf> {
    private final int HEADER_SIZE = 5;

    /**
     * 最大长度 1MB
     */
    public static final int MAX_FRAME_LENGTH = 1024 * 1024;
    /**
     * 长度字段所占的字节数
     */
    public static final int LENGTH_FIELD_LENGTH = 4;
    /**
     * 长度偏移
     */
    public static final int LENGTH_FIELD_OFFSET = 1;
    public static final int LENGTH_ADJUSTMENT = 0;
    public static final int INITIAL_BYTES_TO_STRIP = 0;

    public static final byte WORK = 0xA;

    public static final byte HEARTBEAT = 0xB;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {
        if (msg.readableBytes() < HEADER_SIZE) {
            throw new Exception("字节数不足");
        }
        //读取flag字段 a表示正常业务数据，b表示心跳包
        byte flag = msg.readByte();
        //读取length字段
        int length = msg.readInt();

        if (msg.readableBytes() != length) {
            log.info("标记的长度不符合实际长度");
        } else if (HEARTBEAT == flag) {
            log.info("心跳不做处理");
        } else {
            //读取body
            byte[] bytes = new byte[msg.readableBytes()];
            msg.readBytes(bytes);
            log.debug("接收的数据：{}", bytes);
            // 将消息传递给下一管道
            channelHandlerContext.fireChannelRead(new String(bytes, StandardCharsets.UTF_8));
        }
    }
}