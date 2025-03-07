package com.alibaba.ride.advice;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(CustomBadRequest.class)
    public ResponseEntity<CustomBadRequestResponse> customBadRequestResponseEntity(CustomBadRequest request) {
        return new ResponseEntity<>(new CustomBadRequestResponse(request.getMessage(), request.getErrorCode()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    return fieldName + ": " + message;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    public record CustomBadRequestResponse(String message, String errorCode) {

    }

    @Getter
    public enum SystemErrors {
        UNCOMPLETED_RIDE("You cannot place ride before complete current ride.", 1000),
        PAYMENT_METHOD_UNSELECTED("Please select payment method", 1100),
        RIDER_NOT_EXISTS("The rider not exists", 1110),
        RIDER_ALREADY_EXISTS("The rider already exists", 1110),
        USER_ALREADY_EXISTS("Username already exists", 1120),
        TRANSACTION_NOT_FOUND("Ride not found", 1130),
        CANNOT_CANCEL_THIS_RIDE("You cannot cancel ride with the following situations (Already canceled ride, payment failed,or completed ride)", 1140),
        TOKEN_NOT_VALID("Token not valid", 1150),
        YOU_CANNOT_ACCEPT_THIS_RIDE("You cannot accept this ride, please wait another ride to accepted.", 1160),
        GENERAL_ERROR("Unknown general error occurred, please contact with support team", 100_000);
        private final static String ERROR_PREFIX = "R_";
        private final String message;
        private final String code;

        SystemErrors(String message, int code) {
            this.message = message;
            this.code = ERROR_PREFIX + code;
        }
    }

    @Getter
    public static class CustomBadRequest extends RuntimeException {
        private final String errorCode;

        public CustomBadRequest(ErrorHandler.SystemErrors error) {
            super(error.getMessage());
            this.errorCode = error.getCode();
        }
    }
}
