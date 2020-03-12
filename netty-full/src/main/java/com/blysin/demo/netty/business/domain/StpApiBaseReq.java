package com.blysin.demo.netty.business.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

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
    private String reqId;
    private String lotCode;
    private Object data;

    public StpApiBaseReq(String lotCode, String cmd, Object data) {
        this.lotCode = lotCode;
        this.cmd = cmd;
        this.data = data;
        this.reqId = RandomStringUtils.randomAlphabetic(32);
    }
}