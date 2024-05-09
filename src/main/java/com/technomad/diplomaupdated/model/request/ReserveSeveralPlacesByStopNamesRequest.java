package com.technomad.diplomaupdated.model.request;

import java.util.List;

public record ReserveSeveralPlacesByStopNamesRequest(
        Long route,
        String start,
        String finish,
        List<Integer> places
) {
}
