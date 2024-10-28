package com.custommacro.custommacro.global.exception;

public enum ErrorMessage {
    ROBOT_INITIALIZATION_FAILED("Failed to initialize Robot instance."),
    INVALID_INTERVAL("Interval must be positive."),
    CAPTURE_SAVE_FAILED("Failed to save capture."),
    MACRO_EXECUTION_FAILED("Failed to execute macro."),
    MACRO_ALREADY_RUNNING("Macro is already running."),
    MACRO_STOP_FAILED("Error occurred while stopping macro."),
    MACRO_NOT_RUNNING("Macro is not currently running."),

    INVALID_MACRO_CONFIGURATION("Invalid macro configuration."),
    NULL_POINTER_ENCOUNTERED("Null pointer encountered."),
    RESOURCE_NOT_FOUND("Required resource not found."),
    INVALID_MACRO_PARAMETERS("Invalid macro parameters."),
    UNSUPPORTED_OPERATION("Operation not supported."),
    TIMEOUT_OCCURRED("Operation timed out."),
    SYSTEM_OVERLOAD("Operation failed due to system overload."),
    UNKNOWN_ERROR("An unknown error has occurred.");


    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
