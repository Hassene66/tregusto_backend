package fr.gopartner.tregusto.common.exception.controller;

import fr.gopartner.tregusto.common.exception.core.ApplicationException;
import fr.gopartner.tregusto.common.exception.core.CustomErrorCodes;
import fr.gopartner.tregusto.common.exception.dto.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiError> handleApplicationException(ApplicationException ex) {
        log.error("Error: {}", ex.toApiError());
        log.error("Error kind: {}", ex.getClass());
        return ResponseEntity.status(ex.getStatus()).body(ex.toApiError());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleApiValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getBindingResult().getAllErrors());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(ex.getBody().getDetail(), HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCodes.Common.SOMETHING_WENT_WRONG, OffsetDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCodes.Common.SOMETHING_WENT_WRONG, OffsetDateTime.now()));
    }

}
