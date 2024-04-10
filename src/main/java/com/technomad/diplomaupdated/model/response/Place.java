package com.technomad.diplomaupdated.model.response;

import com.technomad.diplomaupdated.model.state.PlaceState;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class Place {
    private final Integer place;
    private final PlaceState placeState;
}
