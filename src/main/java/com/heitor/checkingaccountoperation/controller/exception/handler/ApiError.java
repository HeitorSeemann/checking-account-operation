package com.heitor.checkingaccountoperation.controller.exception.handler;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiError {
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ApiError(String error, String message, LocalDateTime timestamp) {
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ApiError() {
    }

}