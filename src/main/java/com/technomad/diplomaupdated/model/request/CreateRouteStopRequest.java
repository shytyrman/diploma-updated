package com.technomad.diplomaupdated.model.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//@Getter
//@ToString
//@EqualsAndHashCode
//@AllArgsConstructor
public record CreateRouteStopRequest(
        String station,
        LocalDateTime arrivalTime,
        LocalDateTime departureTime,
        BigDecimal cost) {}
