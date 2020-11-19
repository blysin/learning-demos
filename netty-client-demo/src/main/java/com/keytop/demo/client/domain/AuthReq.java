package com.keytop.demo.client.domain;

import lombok.Data;

/**
 * 认证请求
 *
 * @author daishaokun
 * @date 2019-09-12
 */
@Data
public class AuthReq {
    /**
     * 车场编号
     */
    private String lotCode;
    /**
     * 时间戳
     */
    private Long ts;
    /**
     * 车场编号+时间戳+车场token的MD5
     */
    private String auth;
}