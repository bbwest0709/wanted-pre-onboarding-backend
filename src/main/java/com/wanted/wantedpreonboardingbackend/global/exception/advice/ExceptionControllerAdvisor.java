package com.wanted.wantedpreonboardingbackend.global.exception.advice;

import com.wanted.wantedpreonboardingbackend.global.exception.JobFlatformException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvisor {

    @ExceptionHandler(JobFlatformException.class)
    public ResponseEntity<Object> handleJobFlatformException(JobFlatformException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
