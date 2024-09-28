package com.azry.dmtp.z.config.handler;


import com.azry.dmtp.z.config.handler.exception.ClientAlreadyExistException;
import com.azry.dmtp.z.config.handler.exception.InactiveClientException;
import com.azry.dmtp.z.config.handler.exception.InsufficientBalanceException;
import com.azry.dmtp.z.config.handler.exception.NotFoundException;
import com.azry.dmtp.z.config.handler.exception.UnsupportedCurrencyException;
import com.azry.dmtp.z.model.api.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;


@Slf4j
@ControllerAdvice("com.azry.dmtp.z")
public class ExceptionHandlerConfig {

    @ExceptionHandler(ClientAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleClientAlreadyExistException(ClientAlreadyExistException ex) {
        String bodyOfResponse = ex.getMessage();
        log.error("Client already exists exception occurred: {}", bodyOfResponse);
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.CONFLICT.value(), bodyOfResponse), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InactiveClientException.class)
    public ResponseEntity<ErrorResponse> handleInactiveClientException(InactiveClientException ex) {
        String bodyOfResponse = ex.getMessage();
        log.error("Client is inactive: {}", bodyOfResponse);
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), bodyOfResponse), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedCurrencyException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedCurrencyException(UnsupportedCurrencyException ex) {
        String bodyOfResponse = ex.getMessage();
        log.error("Unsupported currency exception occurred: {}", bodyOfResponse);
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), bodyOfResponse), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        String bodyOfResponse = ex.getMessage();
        log.error("Not found exception occurred: {}", bodyOfResponse);
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.NOT_FOUND.value(), bodyOfResponse), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(InsufficientBalanceException ex) {
        String bodyOfResponse = ex.getMessage();
        log.error("Insufficient balance exception occurred: {}", bodyOfResponse);
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), bodyOfResponse), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        String bodyOfResponse = ex.getMessage();
        log.error("Illegal argument exception occurred: {}", bodyOfResponse);
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), bodyOfResponse), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        final List<String> errorMessages = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> "'" + fieldError.getField() + "' " + ((fieldError.getDefaultMessage() == null) ? "" : fieldError.getDefaultMessage()))
                .toList();
        log.error("Method argument not valid exception occurred: {}", errorMessages);
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), errorMessages.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred!"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
