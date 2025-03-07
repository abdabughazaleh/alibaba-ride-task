package com.alibaba.drivermanagement.advice;

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
        DRIVER_NOT_EXISTS("Driver not exists", 1000),
        DRIVER_ALREADY_EXISTS("Driver already exists", 1100),
        TRANSACTION_NO_NOT_EXISTS("Transaction not found", 1200),
        TOKEN_NOT_VALID("Token not valid", 1150),
        NOTIFICATION_STATUS_NOT_FOUND("Notification status not found", 1160),
        SOMETHING_WRONG_IN_RIDE_ACCEPTED("Something wrong in ride accepted, may someone else accept this ride.", 1170),
        SOMETHING_WRONG_IN_RIDE_COMPLETED("Something wrong in ride completed, please try again after moment or call support team.", 1180),
        SOMETHING_WRONG_WHILE_GETTING_DRIVER_RIDES("Cannot fetch driver rides right now, please try again later.", 1190),
        GENERAL_ERROR("Unknown general error occurred, please contact with support team", 100_000);
        private final static String ERROR_PREFIX = "DM_";
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

        public CustomBadRequest(SystemErrors error) {
            super(error.getMessage());
            this.errorCode = error.getCode();
        }
    }
}
