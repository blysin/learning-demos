package com.keytop.demo.client.netty.coder;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息体
 *
 * @author daishaokun
 * @date 2019-09-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolData {
    /**
     * 心跳消息
     */
    public final static ProtocolData HEARTBEAT_PROTOCOL = new ProtocolData(ByteToProtoBufDecoder.HEARTBEAT, "");

    /**
     * 信息标志: 0xA 业务信息包  0xB 表示心跳包
     */
    private byte flag;

    /**
     * 内容
     */
    private String content;

    /**
     * 生成消息体，发送字符串消息
     *
     * @param msg
     * @return
     */
    public static ProtocolData buildMsg(String msg) {
        return new ProtocolData(ByteToProtoBufDecoder.WORK, msg);
    }

    /**
     * 生成消息体，发送json格式消息
     *
     * @param apiBaseReq
     * @return
     */
    public static ProtocolData buildMsg(Object apiBaseReq) {
        return new ProtocolData(ByteToProtoBufDecoder.WORK, JSON.toJSONString(apiBaseReq));
    }
}