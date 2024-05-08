package com.technomad.diplomaupdated.model.request;

public record GetPlacesByStopNamesRequest(
       Long route,
       String start,
       String finish
) {
}
