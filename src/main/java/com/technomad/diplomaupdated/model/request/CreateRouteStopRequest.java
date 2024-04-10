package com.technomad.diplomaupdated.model.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateRouteStopRequest {

    private final String station;
    private final LocalDateTime arrivalTime;
    private final LocalDateTime departureTime;
    private final BigDecimal cost;
}
