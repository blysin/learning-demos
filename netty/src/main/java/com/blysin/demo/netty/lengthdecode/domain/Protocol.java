package com.blysin.demo.netty.lengthdecode.domain;

import com.blysin.demo.netty.lengthdecode.coder.ByteToProtoBufDecoder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author daishaokun
 * @date 2019-09-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Protocol {
    public final static Protocol HEARTBEAT_PROTOCOL = new Protocol(ByteToProtoBufDecoder.HEARTBEAT, "");

    /**
     * 信息标志: a 业务信息包  b 表示心跳包
     */
    private byte flag;

    /**
     * 内容
     */
    private String content;

    public static Protocol buildMsg(String msg) {
        return new Protocol(ByteToProtoBufDecoder.WORK, msg);
    }
}