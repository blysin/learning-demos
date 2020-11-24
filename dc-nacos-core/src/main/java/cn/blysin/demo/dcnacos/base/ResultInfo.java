package cn.blysin.demo.dcnacos.base;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultInfo<T> {
    public static final ResultInfo SUCCESS_RESULT = new ResultInfo(ResultEnum.SUCCESS);

    /**
     * 错误码
     */
    private Integer resultCode;

    /**
     * 提示信息
     */
    private String resultMsg;

    /**
     * 具体的内容
     */
    private T data;

    public final static ResultInfo EMPTYERROR = new ResultInfo(ResultEnum.EMPTYERROR);
    /**
     * 登录超时
     */
    public static final ResultInfo LOGINERROR = new ResultInfo(ResultEnum.LOGINERROR);
    /**
     * 您无该操作权限
     */
    public static final ResultInfo AUTHERROR = new ResultInfo(ResultEnum.AUTHERROR);
    /**
     * 请求超时
     */
    public static final ResultInfo REQUESTTIMEOUT = new ResultInfo(ResultEnum.REQUESTTIMEOUT);
    /**
     * 请求超时
     */
    public static final ResultInfo NOAUTH = new ResultInfo(ResultEnum.NOAUTH);
    /**
     * 无效车场
     */
    public static final ResultInfo INVALIDLOTCODE = new ResultInfo(ResultEnum.INVALIDLOTCODE);

    private ResultInfo(T data) {
        this.resultCode = ResultEnum.SUCCESS.getResultCode();
        this.resultMsg = ResultEnum.SUCCESS.getResultMsg();
        this.data = data;
    }

    private ResultInfo(String msg) {
        this.resultCode = ResultEnum.KNOWNERROR.getResultCode();
        this.resultMsg = msg;
    }

    private ResultInfo(ResultEnum resultEnum) {
        this.resultCode = resultEnum.getResultCode();
        this.resultMsg = resultEnum.getResultMsg();
    }

    private ResultInfo(ResultEnum resultEnum, T data) {
        this.resultCode = resultEnum.getResultCode();
        this.resultMsg = resultEnum.getResultMsg();
        this.data = data;
    }

    /**
     * 操作成功返回数据
     *
     * @param obj
     * @return
     */
    public static <T> ResultInfo<T> SUCCESS(T obj) {
        return new ResultInfo<T>(obj);
    }

    /**
     * 操作成功返回数据
     *
     * @param obj
     * @return
     */
    public static <T> ResultInfo<T> notNullResult(T obj) {
        return obj == null ? EMPTYERROR : ResultInfo.SUCCESS(obj);
    }

    /**
     * 操作成功返回提示
     *
     * @return
     */
    public static ResultInfo SUCCESS() {
        return SUCCESS_RESULT;
    }

    /**
     * 系统错误提示
     *
     * @return
     */
    public static ResultInfo ERROR() {
        return new ResultInfo(ResultEnum.UNKNOWNERROR);
    }

    /**
     * 系统错误提示
     *
     * @return
     */
    public static ResultInfo SYSERROR(String msg) {
        ResultInfo resultInfo = new ResultInfo(ResultEnum.UNKNOWNERROR);
        resultInfo.setResultMsg(msg);
        return resultInfo;
    }

    /**
     * 操作（可控错误）提示
     *
     * @param msg
     * @return
     */
    public static ResultInfo ERROR(String msg) {
        return new ResultInfo(msg);
    }

    /**
     * 请求参数错误提示
     *
     * @param msg
     * @return
     */
    public static ResultInfo PARAMERROR(String msg) {
        ResultInfo resultInfo = new ResultInfo(ResultEnum.PARAMERROR);
        resultInfo.setResultMsg(msg);

        return resultInfo;
    }

    /**
     * 登录超时
     *
     * @return
     */
    public static ResultInfo LOGINERROR() {
        return LOGINERROR;
    }

    /**
     * 没有token
     *
     * @return
     */
    public static ResultInfo NOAUTH() {
        return NOAUTH;
    }

    /**
     * 无权限
     *
     * @return
     */
    public static ResultInfo AUTHERROR() {
        return AUTHERROR;
    }

    /**
     * 请求超时
     *
     * @return
     */
    public static ResultInfo REQUESTTIMEOUT() {
        return REQUESTTIMEOUT;
    }

    public static ResultInfo SIMPLE(boolean result) {
        return result ? SUCCESS() : ERROR();
    }

    public static ResultInfo ACCEPTED() {
        return new ResultInfo(ResultEnum.ACCEPTED);
    }

    public static <T> ResultInfo<T> ACCEPTED(T obj) {
        return new ResultInfo(ResultEnum.ACCEPTED, obj);
    }

    public static ResultInfo NOCONTENT() {
        return new ResultInfo(ResultEnum.NOCONTENT);
    }


    /**
     * 判断是否成功
     *
     * @return
     */
    public boolean success() {
        return ResultEnum.SUCCESS.getResultCode().equals(this.resultCode);
    }

    /**
     * 判断是否失败
     *
     * @return
     */
    public boolean error() {
        return !success();
    }

    /**
     * @Title:
     * @Description:若状态码为200则返回结果集，否则抛异常
     * @return:
     * @throws:
     */
    public <T> T takeSuccessData() throws Exception {
        return (T) data;

    }


}
