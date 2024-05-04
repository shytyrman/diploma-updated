package com.technomad.diplomaupdated.model.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;

//@Getter
//@ToString
//@EqualsAndHashCode
//@AllArgsConstructor
public record CreateRouteRequest(
        String description,
        ArrayList<CreateRouteStopRequest> stops) {}
