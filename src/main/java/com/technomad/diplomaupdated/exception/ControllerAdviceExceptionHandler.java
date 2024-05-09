package com.technomad.diplomaupdated.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Глобальный обработчик исключений, возникший во время обработки запроса в контроллере.
 */
@Slf4j
@ControllerAdvice(basePackages = {
        "com.technomad.diplomaupdated.controller",
        "com.technomad.diplomaupdated.registration.signup"
})
@Component
@RequiredArgsConstructor
public class ControllerAdviceExceptionHandler {

    @Order(1000)
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(
            Exception ex, WebRequest request) {
        val errorMsg = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(
                errorMsg, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Order(100)
    @ExceptionHandler({IllegalRequestException.class})
    public ResponseEntity<Object> handleIllegalRequestException(
            IllegalRequestException ex, WebRequest request) {
        val errorMsg = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(
                errorMsg, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
