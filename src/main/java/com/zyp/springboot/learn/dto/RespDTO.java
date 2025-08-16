package com.zyp.springboot.learn.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zyp.springboot.learn.infra.errorcode.ErrorCode;
import com.zyp.springboot.learn.infra.errorcode.SystemErrorCode;
import com.zyp.springboot.learn.util.StrUtils;
import lombok.Data;

@Data
public class RespDTO<T> {

    private Integer code;
    private String msg;
    private T data;

    public RespDTO(ErrorCode errorCode, String msg, T data) {
        this.code = errorCode.getCode();
        if (StrUtils.hasLength(msg)) {
            this.msg = msg;
        } else {
            this.msg = errorCode.getMsg();
        }
        this.data = data;
    }

    private RespDTO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public static <T> RespDTO<T> ok() {
        return okMsg("");
    }

    public static <T> RespDTO<T> ok(T data) {
        return ok(data, "");
    }

    public static <T> RespDTO<T> ok(T data, String msg) {
        return new RespDTO<>(SystemErrorCode.SUCCESS, msg, data);
    }


    public static <T> RespDTO<T> okMsg(String msg) {
        return new RespDTO<>(SystemErrorCode.SUCCESS, msg, null);
    }

    public static <T> RespDTO<T> systemError(String msg) {
        return new RespDTO<>(SystemErrorCode.SYSTEM_ERROR, msg, null);
    }

    public static <T> RespDTO<T> systemError() {
        return systemError("");
    }

    public static <T> RespDTO<T> paramError(String msg) {
        return new RespDTO<>(SystemErrorCode.PARAM_ERROR, msg, null);
    }

    public static <T> RespDTO<T> paramError() {
        return paramError("");
    }

    public static <T> RespDTO<T> error(ErrorCode errorCode) {
        return errorMsg(errorCode, "");
    }

    public static <T> RespDTO<T> errorMsg(ErrorCode errorCode, String msg) {
        return new RespDTO<>(errorCode, msg, null);
    }

    @JsonIgnore
    public boolean isOk() {
        return this.code == SystemErrorCode.SUCCESS.getCode();
    }

    public boolean notOk() {
        return !this.isOk();
    }

    public RespDTO copyError() {
        return new RespDTO(this.code, this.msg);
    }
}
