package com.myblog.myblog.constant;

public enum ServiceExceptionEnum {

    SUCCESS(0, "Success"),
    SYS_ERROR(1, "Server Error"),
    MISSING_REQUEST_PARAM_ERROR(2, "Missing Parms"),
    ID_NOT_FOUND(3, "ID not found"),;
    private final int code;
    private final String message;

    ServiceExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
