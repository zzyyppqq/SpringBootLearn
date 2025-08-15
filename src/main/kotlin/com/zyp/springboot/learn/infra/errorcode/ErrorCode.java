package com.zyp.springboot.learn.infra.errorcode;

public interface ErrorCode {
    int getCode(); // 错误码
    String getMsg(); // 错误信息
}
