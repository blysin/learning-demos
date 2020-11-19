package com.blysin.demo.netty.framework.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 消息内容格式处理
 *
 * @author daishaokun
 * @date 2019-09-12
 */
public class SocketMessageUtils {
    private SocketMessageUtils() {
    }

    private static final String EMPTY_SUFFIX = "\r\n";

    /**
     * 去掉字符串内部换行符，并在末尾加上换行
     *
     * @param msg
     * @return
     */
    public static String buildMsg(String msg) {
        msg = StringUtils.trimToEmpty(msg);
        return StringUtils.isEmpty(msg) ? EMPTY_SUFFIX : RegExUtils.replaceAll(msg, "\r|\n", "") + EMPTY_SUFFIX;
    }

    /**
     * 将对象转为json，并去掉字符串内部换行符，在末尾加上换行
     *
     * @param msg
     * @return
     */
    public static String buildMsg(Object msg) {
        return msg == null ? StringUtils.EMPTY : buildMsg(JSON.toJSONString(msg));
    }

}