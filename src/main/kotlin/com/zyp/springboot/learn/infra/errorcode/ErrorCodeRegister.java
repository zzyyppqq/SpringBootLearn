package com.zyp.springboot.learn.infra.errorcode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ErrorCodeRegister {
    public static void init() {
        // 注册系统错误码，并分配错误码范围
        register(SystemErrorCode.class, new CodeRange(10000, 20000));
        // 以后其它的错误码都要在这里注册
    }

    static final int MIN_ERROR_CODE = 10000; // 错误码的最小值

    static final Map<Class<? extends ErrorCode>, CodeRange> ERROR_CODE_RANGES = new ConcurrentHashMap<>();

    static void register(Class<? extends ErrorCode> clazz, CodeRange range) {
        if (!clazz.isEnum()) {
            throw new IllegalArgumentException(
                    String.format("[RegisterErrorCodeError]%s is not Enum class", clazz.getSimpleName()));
        }

        if (ERROR_CODE_RANGES.containsKey(clazz)) {
            throw new IllegalArgumentException(
                    String.format("[RegisterErrorCodeError]Enum %s already exist", clazz.getSimpleName()));
        }
        var errMsg = assertRangeValidity(clazz, range);
        if (errMsg != null) {
            throw new IllegalArgumentException(
                    String.format("[RegisterErrorCodeError]assert range validity error:%s", errMsg));
        }
        ERROR_CODE_RANGES.put(clazz, range);
    }


    private static String assertRangeValidity(Class<? extends ErrorCode> clazz, CodeRange range) {
        if (range.start() > range.end()) {
            return String.format("%s's start(%d) of range must be less than the end(%d)",
                    clazz.getSimpleName(), range.start(), range.end());
        }

        if (range.start() < MIN_ERROR_CODE) {
            return String.format("%s's start(%d) cannot be less than %s",
                    clazz.getSimpleName(), range.start(), MIN_ERROR_CODE);
        }
        for (Class<? extends ErrorCode> existErrorCode : ERROR_CODE_RANGES.keySet()) {
            var existCodeRange = ERROR_CODE_RANGES.get(existErrorCode);
            if (existCodeRange.overlap(range)) {
                return String.format("%s's Range(%s) has overlapped with %s(%s)",
                        clazz.getSimpleName(), range, existErrorCode.getSimpleName(), existCodeRange);
            }
        }
        // 检查错误码是否在给定区间内
        List<Integer> codeList = new LinkedList<>();
        for (ErrorCode codeEnum : clazz.getEnumConstants()) {
            int code = codeEnum.getCode();
            if (!range.inRange(code)) {
                return String.format("Code(%d) in %s is out of range(%s)",
                        code, clazz.getSimpleName(), range);
            }
            codeList.add(code);
        }
        // 检查是否有重复的错误码
        var set = new HashSet<Integer>();
        var duplicatedCodes = new LinkedList<Integer>();
        for (Integer code : codeList) {
            if (set.contains(code)) {
                duplicatedCodes.add(code);
            } else {
                set.add(code);
            }
        }
        if (duplicatedCodes.size() > 0) {
            return String.format("[RegisterErrorCode] error: %s code %s is repeat",
                    clazz.getSimpleName(), duplicatedCodes);
        }
        return null;
    }
}