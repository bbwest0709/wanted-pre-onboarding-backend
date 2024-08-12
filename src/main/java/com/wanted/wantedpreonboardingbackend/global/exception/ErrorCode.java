package com.wanted.wantedpreonboardingbackend.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    COMPANY_NOT_FOUNT("존재하지 않는 회사입니다.", HttpStatus.CONFLICT),
    JOB_POST_NOT_FOUNT("존재하지 않는 채용 공고입니다.", HttpStatus.CONFLICT),
    INVALID_COMPANY_ACCESS("해당 채용공고를 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    ALREADY_APPLIED("이미 지원한 공고입니다.", HttpStatus.CONFLICT),
    USER_NOT_FOUND("존재하지 않는 회원입니다.", HttpStatus.CONFLICT);

    private String message;
    private HttpStatus httpStatus;

    ErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
