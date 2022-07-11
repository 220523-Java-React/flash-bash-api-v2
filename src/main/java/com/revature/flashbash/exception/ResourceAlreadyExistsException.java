package com.revature.flashbash.exception;


import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends ApiException {

    public ResourceAlreadyExistsException(Class<?> clazz, String property, Object value) {
        super(
                HttpStatus.CONFLICT,
                String.format("%s already exists with %s: %s", clazz.getSimpleName(), property, value.toString())
        );
    }
}
