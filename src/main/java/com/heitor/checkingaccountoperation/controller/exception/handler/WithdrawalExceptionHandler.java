package com.heitor.checkingaccountoperation.controller.exception.handler;

import com.heitor.checkingaccountoperation.controller.exception.WithdrawalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class WithdrawalExceptionHandler {

    @ExceptionHandler(WithdrawalException.class)
    public ResponseEntity<ApiError> handleWithdrawalException(WithdrawalException e) {
        ApiError error = new ApiError(
                "withdrawal_error",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.unprocessableEntity()
                .body(error);
    }
}