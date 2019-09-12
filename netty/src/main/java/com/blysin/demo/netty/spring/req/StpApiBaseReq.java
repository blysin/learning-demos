package com.blysin.demo.netty.spring.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求参数
 *
 * @author daishaokun
 * @date 2019-09-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StpApiBaseReq {
    private String cmd;
    private Object data;
}