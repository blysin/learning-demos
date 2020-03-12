package com.blysin.demo.netty.framework.constant;

/**
 * 返回值枚举
 *
 * @author zengwl
 * @date 2018/12/1
 */
public enum RespEnum {

    /**
     * http 状态码
     */
    SUCCESS("200", "操作成功"),
    UNAUTHORIZED("401", "未授权"),
    ERROR("500", "服务异常"),

    /**
     * 鉴权信息
     */
    INVALID_APPID("1001", "无效的APPID"),

    AUTH_FAIL("1101", "认证失败"),
    PARK_UNAUTHORIZED("1102", "车场未授权"),

    PARAM_MISSION("1501", "参数不完整"),
    INVALID_PARAM("1502", "参数不符合规定值"),

    /**
     * 业务信息
     */
    SERVICE_ERROR("2001", "业务异常"),
    VERSION_ERROR("2002", "客户端版本过低"),

    /**
     * 第三方云平台枚举说明
     */
    STP_NO_PARKING("1000", "车场不存在"),
    STP_NO_CMD("1001", "指令不能为空"),
    STP_CHECK_CODE_ERROR("1002", "校验码错误"),
    STP_UPLOAD_FAILURE("1003", "上报失败"),
    STP_DATA_ALREADY_EXISTS("1004", "数据已经存在"),
    STP_NO_LATEST_CMD("1005", "无最新指令"),
    STP_NO_ONLINE("1006", "车场已离线"),
    STP_PARK_NO_DATA("1007", "车场无返回结果"),
    STP_CMD_NOT_FOUND("1008", "未找到指令"),
    STP_DATA_NOT_EXISTS("1014", "数据不存在"),
    STP_PARK_ERROR("1107", "场端处理异常"),


    STP_CAR_NO_PAY("1200", "月租车等无需缴费"),


    STP_SYS_ERROR("2000", "系统异常"),


    STP_MEDIA_TYPE_NOT_SUPPORTED("2001", "Http媒体类型不支持例外"),
    STP_REQUEST_METHOD_NOT_SUPPORTED("2002", "Http请求方法不支持"),
    STP_NO_HANDLER_FOUND("2003", "没有处理程序发现异常"),

    PROTOCOL_ERROR("2004", "数据协议错误"),;

    private String code;
    private String desc;

    RespEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
