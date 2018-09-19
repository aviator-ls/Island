package com.aviator.island.constants;

/**
 * Created by aviator_ls on 2018/7/20.
 */
public enum ResponseCode {

    SUCCESS("0000", "请求成功"),

    USER_NOT_EXIST("1001", "用户不存在！"),

    USER_USERNAME_OR_PASSWORD_ERROR("1002", "登录用户名或密码错误！"),

    USER_IS_LOCK("1003", "用户被锁定！"),

    USER_IS_EXIST("1004", "用户已存在！"),

    LOGIN_MUCH_ERROR("1005", "登录错误次数过多！"),

    USER_NOT_LOGIN("1006", "用户未登录！"),

    UNKNOWN_ERROR("1007", "登录未知错误！"),

    PARAMS_ERROR("2001", "参数错误!"),

    ADD_DATA_IS_EXIST("3001", "新增数据已存在！"),

    DELETE_DATA_NOT_EXIST("3002", "删除的数据不存在！"),

    UPDATE_DATA_NOT_EXIST("3003", "更新的数据不存在！"),

    BAD_REQUEST("9001", "非法请求！"),

    NOT_SUPPORT_MEDIA_TYPE("9002", "不支持的请求类型！"),

    METHOD_NOT_SUPPORTED("9003", "不支持的方法！"),

    ILLEGAL_PARAM("9004", "非法参数！"),

    METHOD_ARGUMENT_TYPE_MISMATCH("9005", "非法参数类型！"),

    PARAM_ID_NOT_EXIST("9006", "参数id不存在！"),

    SYS_ERROR("9999", "系统错误！");

    private String code;

    private String msg;

    ResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
