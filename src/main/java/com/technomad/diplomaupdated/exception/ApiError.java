package com.technomad.diplomaupdated.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiError {

    private int statusCode;
    private String message;
    private LocalDateTime datetime;

    public ApiError(HttpStatus status, String message) {
        this.statusCode = status.value();
        this.message = message;
        this.datetime = LocalDateTime.now();
    }
}