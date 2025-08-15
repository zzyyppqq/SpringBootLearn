package com.zyp.springboot.learn.infra.global;



import com.zyp.springboot.learn.dto.RespDTO;
import com.zyp.springboot.learn.infra.errorcode.BusinessException;
import com.zyp.springboot.learn.infra.errorcode.SystemErrorCode;
import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @RestControllerAdvice只能捕获控制层抛出的异常，对Filter层就无能为力了。实际上，我们在前面的访问日志AccessFilter类中已经处理好了。
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({TypeMismatchException.class, BindException.class, IllegalArgumentException.class})
    public ResponseEntity<RespDTO<?>> paramError(HttpServletRequest request, Throwable e) {
        log.error("param error", e);
        if (e instanceof BindException) {
            if (e instanceof MethodArgumentNotValidException) {
                List<FieldError> fieldErrors = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
                List<String> msgList = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
                return ResponseEntity.ok(RespDTO.errorMsg(SystemErrorCode.PARAM_ERROR, String.join(",", msgList)));
            }

            List<FieldError> fieldErrors = ((BindException) e).getFieldErrors();
            List<String> error = fieldErrors.stream().map(field ->
                    field.getField() + ":" + field.getDefaultMessage()).toList();
            String errorMsg = SystemErrorCode.PARAM_ERROR.getMsg() + ":" + error;
            return ResponseEntity.ok(RespDTO.errorMsg(SystemErrorCode.PARAM_ERROR, errorMsg));
        }
        return ResponseEntity.ok(RespDTO.paramError());
    }

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<RespDTO<?>> unknownError(HttpServletRequest request, Throwable e) {
        log.error("unknown error", e);
        return ResponseEntity.ok(RespDTO.systemError());
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<RespDTO<?>> businessError(HttpServletRequest request, BusinessException ex) {
        log.error("business error: " + ex.getErrorCode(), ex);
        if (ex.getErrorCode() != null) {
            return ResponseEntity.ok(RespDTO.error(ex.getErrorCode()));
        } else {
            return ResponseEntity.ok(RespDTO.systemError());
        }
    }

//    @ExceptionHandler(value = TokenExpiredException.class)
//    public ResponseEntity<RespDTO<?>> tokenExpire(HttpServletRequest request, Throwable e) {
//        log.error("tokenExpired", e);
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(RespDTO.error(SystemErrorCode.INVALID_TOKEN));
//    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<RespDTO<?>> authenticationError(HttpServletRequest request, Throwable e) {
        log.error("invalid token", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(RespDTO.error(SystemErrorCode.INVALID_TOKEN));
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<RespDTO<?>> accessDeniedError(HttpServletRequest request, Throwable e) {
        log.error("no permission", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(RespDTO.error(SystemErrorCode.NO_PERMISSION));
    }

//    @ExceptionHandler(value = JWTDecodeException.class)
//    public ResponseEntity<RespDTO<?>> jwtDecodeError(HttpServletRequest request, Throwable e) {
//        log.error("invalid token", e);
//        return ResponseEntity
//                .status(HttpStatus.FORBIDDEN)
//                .body(RespDTO.errorMsg(SystemErrorCode.NO_PERMISSION, "无效的Token"));
//    }


}
