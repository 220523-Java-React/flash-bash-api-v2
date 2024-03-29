package com.revature.flashbash.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionResponse extends ResponseEntity<ApiException> {
    public ExceptionResponse(ApiException exception) {
        super(exception, HttpStatus.valueOf(exception.getStatus()));
    }
}
