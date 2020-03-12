package com.keytop.demo.client.domain;

import com.keytop.demo.client.enums.RespEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 响应结果
 *
 * @author daishaokun
 * @date 2019-09-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StpApiBaseResp extends StpResult {
    /**
     * 请求id，用于异步回调
     */
    private String reqId;


    public static StpApiBaseResp success(String reqId) {
        StpApiBaseResp resp = new StpApiBaseResp(reqId);
        resp.setCode(RespEnum.SUCCESS.getCode());
        resp.setMsg(RespEnum.SUCCESS.getDesc());
        resp.setData("");
        return resp;
    }

    public static StpApiBaseResp fail(String reqId, RespEnum code, String msg) {
        StpApiBaseResp resp = new StpApiBaseResp(reqId);
        resp.setCode(code.getCode());
        resp.setMsg(msg);
        return resp;
    }

    public static StpApiBaseResp fail(RespEnum code, String msg) {
        return fail(StringUtils.EMPTY, code, msg);
    }

    public static StpApiBaseResp fail(RespEnum error) {
        return fail(error, error.getDesc());
    }

    public boolean isSuccess(){
        return RespEnum.SUCCESS.getCode().equals(this.getCode());
    }
}