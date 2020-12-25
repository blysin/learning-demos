package com.blysin.demo.netty.framework.base.coder;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 消息体
 *
 * @author daishaokun
 * @date 2019-09-17
 */
@Data
public class ProtocolData {
    public final static ProtocolData HEARTBEAT_PROTOCOL = new ProtocolData(ByteToProtoBufDecoder.HEARTBEAT, "");

    /**
     * 信息标志: 0xA 业务信息包  0xB 表示心跳包
     */
    private byte flag;

    /**
     * 内容
     */
    private String content;


    private ProtocolData() {
    }

    private ProtocolData(byte flag, String content) {
        this.flag = flag;
        this.content = content;
    }

    public static ProtocolData buildMsg(String msg) {
        return new ProtocolData(ByteToProtoBufDecoder.WORK, msg);
    }

    public static ProtocolData buildMsg(Object apiBaseReq) {
        return new ProtocolData(ByteToProtoBufDecoder.WORK, JSON.toJSONString(apiBaseReq));
    }
}