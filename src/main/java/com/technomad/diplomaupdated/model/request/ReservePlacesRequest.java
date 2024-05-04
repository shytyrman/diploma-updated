package com.technomad.diplomaupdated.model.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

//@Getter
//@ToString
//@EqualsAndHashCode
//@AllArgsConstructor
public record ReservePlacesRequest(
        Long route,
        Long start,
        Long finish,
        Integer place) {}
