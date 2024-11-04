package com.zmm.reggie.common;

public enum ErrorCode {

    PARAMS_ERROR(20001, "参数有误！"),
    USERNAME_PWD_NOR_EXIST(20002, "用户名或密码不存在！"),
    PWD_NOR_EXIST(20004, "密码不存在！"),
    TOKEN_ERROR(80001, "token不合法！"),
    TOKEN_EMPTY(80002, "请求头传递的token为空！"),
    TOKEN_REDIS_NOR_EXIST(80003, "token在redis中不存在！"),
    TOKEN_PARSE_FAIL(80003, "token解析失败！"),
    NO_PERMISSION(70001, "无访问权限！"),
    SESSION_TIME_OUT(90001, "会话超时！"),
    NO_LOGIN(90002, "未登录！"),
    LOGIN_TYPE_ERROR(30001, "登录方式有误！"),
    USER_DISABLED(70002, "用户被禁用！"),
    USERNAME_PWD_EMPTY(20003, "请输入用户名或密码！");

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
