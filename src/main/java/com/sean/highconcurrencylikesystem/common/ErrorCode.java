package com.sean.highconcurrencylikesystem.common;

import lombok.Getter;

/**
 * Error Code Enum
 */
@Getter
public enum ErrorCode {

    SUCCESS(200, "Request Success"),
    PARAMS_ERROR(40000, "Invalid Request Param"),
    NOT_LOGIN_ERROR(40100, "Unauthenticated User"),
    NO_AUTH_ERROR(40101, "Unauthorized Access"),
    NOT_FOUND_ERROR(40400, "Request Not Found"),
    FORBIDDEN_ERROR(40300, "Access Forbidden"),
    OPERATION_ERROR(50001, "Request Operation Failed"),
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
