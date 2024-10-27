package com.custommacro.custommacro.exception;

public class CustomException extends RuntimeException {
    
    public CustomException(final ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
