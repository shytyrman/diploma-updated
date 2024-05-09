package com.technomad.diplomaupdated.model.request;

import java.util.List;

public record ReserveSeveralPlacesRequest(
        Long route,
        Long start,
        Long finish,
        List<Integer> places
) {
}
