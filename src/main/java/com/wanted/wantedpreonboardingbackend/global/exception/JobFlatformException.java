package com.wanted.wantedpreonboardingbackend.global.exception;

import org.springframework.http.HttpStatus;

public class JobFlatformException extends RuntimeException{

    private final ErrorCode errorCode;

    public JobFlatformException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }
}
