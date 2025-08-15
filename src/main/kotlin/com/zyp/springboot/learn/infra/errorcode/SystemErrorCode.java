package com.zyp.springboot.learn.infra.errorcode;

import lombok.Getter;

@Getter
public enum SystemErrorCode implements ErrorCode {

    // 成功为10000，不是默认值零，零容易误操作，特别是JSON序列号和反序列化，还是显式设置保险点
    SUCCESS(10000, "Success"),
    SYSTEM_ERROR(10001, "系统异常，请稍后重试"),
    PARAM_ERROR(10002, "参数错误"),
    ALREADY_EXIST(10003, "数据已存在"),
    DATA_NOT_EXIST(10004, "数据不存在"),
    NO_PERMISSION(10005, "没有权限访问"),
    INVALID_TOKEN(10006, "您还未登录或登录失效，请重新登录！"),
    USER_STATUS_ERROR(10007, "用户状态异常"),
    FORM_REPEAT_SUBMIT(10008, "请勿重复提交");

    private final int code;

    private final String msg;

    private final String category;

    SystemErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.category = "SYSTEM";
    }
}