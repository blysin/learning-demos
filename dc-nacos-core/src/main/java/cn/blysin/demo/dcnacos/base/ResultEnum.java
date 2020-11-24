package cn.blysin.demo.dcnacos.base;

public enum ResultEnum {

    SUCCESS(200, "操作成功!"),
    ACCEPTED(202, "已接受请求!"),
    NOCONTENT(204, "成功处理请求!"),
    UNKNOWNERROR(500, "系统错误，请联系管理员！"),
    KNOWNERROR(510, "操作错误！请仔细阅读操作手册！"),
    PARAMERROR(400, "请求参数错误！"),
    LOGINERROR(401, "登录超时，请重新登录！"),
    AUTHERROR(403, "您无该操作权限，如需操作，请联系相关人员开通!"),
    EMPTYERROR(404, "获取不到数据"),
    NOAUTH(406, "无效令牌"),
    REQUESTTIMEOUT(408, "请求超时,请稍后再试！"),
    INVALIDLOTCODE(1203, "无效的车场id！");

    private Integer resultCode;

    private String resultMsg;

    ResultEnum(Integer resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
