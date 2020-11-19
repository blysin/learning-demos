package com.blysin.demo.netty.spring.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应结果
 *
 * @author daishaokun
 * @date 2019-09-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StpApiBaseResp {
    /**
     * 响应代码 200=成功，其他失败，详见返回码枚举
     */
    private String code;
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 响应内容，json字符串
     */
    private String data;
}