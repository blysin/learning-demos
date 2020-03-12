package com.blysin.demo.netty.framework.constant;

/**
 * @author Blysin
 * @since 2019-09-12
 */
public interface NettyConstant {
    String NO_HEARTBEAT = "无心跳，连接中断！";
    String NO_AUTH = "超时未验证信息，连接中断！";
    String AUTH_FAIL = "验证失败，连接中断！";

    String AUTH = "auth";
    String HEARTBEAT = "heartbeat";

    String CMD_FILE = "file";
}
