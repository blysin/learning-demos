package com.keytop.demo.client.netty.coder;

import com.keytop.demo.client.constant.NettyConstant;
import com.keytop.demo.client.domain.StpApiBaseResp;
import com.keytop.demo.client.enums.RespEnum;
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
     * 长度偏移，偏移一位用于头部区分心跳或业务请求
     */
    public static final int LENGTH_FIELD_OFFSET = 1;
    public static final int LENGTH_ADJUSTMENT = 0;
    public static final int INITIAL_BYTES_TO_STRIP = 0;

    static final byte WORK = 0xA;

    static final byte HEARTBEAT = 0xB;


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
            // 将消息传递给下一管道
            channelHandlerContext.fireChannelRead(NettyConstant.HEARTBEAT);
        } else if (WORK == flag) {
            //读取body
            byte[] bytes = new byte[msg.readableBytes()];
            msg.readBytes(bytes);
            // 将消息传递给下一管道
            channelHandlerContext.fireChannelRead(new String(bytes, StandardCharsets.UTF_8));
        } else {
            log.info("通讯协议错误");
            channelHandlerContext.writeAndFlush(ProtocolData.buildMsg(StpApiBaseResp.fail(RespEnum.PROTOCOL_ERROR)));
        }
    }
}