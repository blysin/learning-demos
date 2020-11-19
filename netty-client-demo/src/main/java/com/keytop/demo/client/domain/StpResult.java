package com.keytop.demo.client.domain;

import com.keytop.demo.client.enums.RespEnum;
import lombok.Data;

/**
 * @author: 程彬彬
 * @date: 2019.3.5
 * describe: 第三方返回值格式定义
 */
@Data
public class StpResult {

    private String code;
    private String msg;
    private Object data;


    public static StpResult success() {
        StpResult result = new StpResult();
        result.setCode("0");
        result.setMsg("操作成功");
        result.setData("");
        return result;
    }

    public static StpResult success(String msg) {
        StpResult result = new StpResult();
        result.setCode("0");
        result.setMsg(msg);
        result.setData("");
        return result;
    }


    public static StpResult success(Object object) {
        StpResult result = new StpResult();
        result.setCode("0");
        result.setMsg("");
        result.setData(object);
        return result;
    }

    public static StpResult error(RespEnum e) {
        StpResult result = new StpResult();
        result.setCode(e.getCode());
        result.setMsg(e.getDesc());
        result.setData(null);
        return result;
    }

    public static StpResult error(RespEnum e, String msg) {
        StpResult result = new StpResult();
        result.setCode(e.getCode());
        result.setMsg(e.getDesc() + ":" + msg);
        result.setData(null);
        return result;
    }

    public static StpResult error(String code, String msg) {
        StpResult result = new StpResult();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
}
