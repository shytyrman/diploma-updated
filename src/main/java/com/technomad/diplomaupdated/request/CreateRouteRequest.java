package com.technomad.diplomaupdated.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateRouteRequest {

    private final String description;
    private final ArrayList<CreateRouteStopRequest> stops;
    
}
