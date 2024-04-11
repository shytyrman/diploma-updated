package com.technomad.diplomaupdated.model.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ReservePlacesRequest {

    private final Long route;
    private final Long start;
    private final Long finish;
    private final Integer place;
}
