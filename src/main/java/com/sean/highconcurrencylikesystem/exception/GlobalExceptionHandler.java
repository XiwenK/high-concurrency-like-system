package com.sean.highconcurrencylikesystem.exception;

import com.sean.highconcurrencylikesystem.common.BaseResponse;
import com.sean.highconcurrencylikesystem.common.ErrorCode;
import com.sean.highconcurrencylikesystem.common.ResultUtils;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global Exception Handler - Based on Spring MVC exception handling (Not AOP)
 */
@RestControllerAdvice
@Slf4j
@Hidden // Hidden in openAPI doc
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error(e.getMessage(), e);
        return ResultUtils.error(ErrorCode.OPERATION_ERROR, e.getMessage());
    }
}
