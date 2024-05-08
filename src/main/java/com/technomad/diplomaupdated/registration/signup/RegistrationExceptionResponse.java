package com.technomad.diplomaupdated.registration.signup;

import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

public record RegistrationExceptionResponse(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
