package com.custommacro.custommacro.global.exception;

public enum ErrorMessage {
    ROBOT_INIT_FAILURE("Failed to initialize Robot instance."),
    INTERVAL_NOT_NEGATIVE("Interval must be positive"),
    CAPTURE_SAVE_FAILURE("Failed capture save");


    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
