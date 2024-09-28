package com.azry.dmtp.fiintegration.config.handler;

import com.azry.dmtp.fiintegration.config.handler.exception.BadRequestException;
import com.azry.dmtp.fiintegration.config.handler.exception.MethodArgumentNotValidException;
import com.azry.dmtp.fiintegration.config.handler.exception.NotFoundException;
import com.azry.dmtp.fiintegration.config.handler.exception.ServiceUnableException;
import com.azry.dmtp.fiintegration.config.handler.exception.TransactionNotCreatedException;
import com.azry.dmtp.fiintegration.model.api.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerConfig {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException bre) {
        log.error("BadRequestException: {}", bre.getMessage(), bre);
        ErrorResponse errorResponse = ErrorResponse.errorResponse(bre.getStatusCode(), bre.getFiIntegrationMessage(), bre.getBankServiceMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(bre.getStatusCode()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        log.error("NotFoundException: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.errorResponse(ex.getStatusCode(), ex.getBankServiceMessage(), ex.getFiIntegrationMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler(ServiceUnableException.class)
    public ResponseEntity<ErrorResponse> handleServiceUnavailableException(ServiceUnableException sue) {
        log.error("ServiceUnableException: {}", sue.getMessage(), sue);
        ErrorResponse errorResponse = ErrorResponse.errorResponse(sue.getStatusCode(), sue.getFiIntegrationMessage(), sue.getBankServiceMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(sue.getStatusCode()));
    }

    @ExceptionHandler(TransactionNotCreatedException.class)
    public ResponseEntity<ErrorResponse> handleTransactionNotCreated(TransactionNotCreatedException tnce) {
        log.error("TransactionNotCreatedException: {}", tnce.getMessage(), tnce);
        ErrorResponse errorResponse = ErrorResponse.errorResponse(tnce.getStatusCode(), tnce.getFiIntegrationMessage(), tnce.getBankServiceMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(tnce.getStatusCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException manve) {
        log.error("MethodArgumentNotValidException: {}", manve.getMessage(), manve);
        ErrorResponse errorResponse = ErrorResponse.errorResponse(manve.getStatusCode(), manve.getFiIntegrationMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(manve.getStatusCode()));
    }

    @ExceptionHandler(RedisConnectionFailureException.class)
    public ResponseEntity<ErrorResponse> handleRedisConnectionFailureException(RedisConnectionFailureException ercfe) {
        log.error("RedisConnectionFailureException: {}", ercfe.getMessage(), ercfe);
        ErrorResponse errorResponse = ErrorResponse.errorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), "Unable to connect to Redis.", ercfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Exception: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unknown error occurred", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
