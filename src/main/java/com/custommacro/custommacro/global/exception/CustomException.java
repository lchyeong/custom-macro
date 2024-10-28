package com.custommacro.custommacro.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(CustomException.class);

    public CustomException(final ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        logger.error("CustomException occurred: {}", errorMessage.getMessage());
    }

    public CustomException(final ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage.getMessage(), cause);
        logger.error("CustomException occurred: {}, Cause: {}", errorMessage.getMessage(), cause.getMessage());
    }
}
