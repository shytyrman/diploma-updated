package com.technomad.diplomaupdated.model.request;

public record ReservePlacesByStopNamesRequest(
        Long route,
        String start,
        String finish,
        Integer place
) {
}
