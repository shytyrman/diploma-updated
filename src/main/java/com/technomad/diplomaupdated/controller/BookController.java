package com.technomad.diplomaupdated.controller;

import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.model.Stop;
import com.technomad.diplomaupdated.model.request.GetPlacesRequest;
import com.technomad.diplomaupdated.model.response.Place;
import com.technomad.diplomaupdated.repository.RouteRepository;
import com.technomad.diplomaupdated.repository.StopRepository;
import com.technomad.diplomaupdated.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("book")
@AllArgsConstructor
public class BookController {

    private BookService bookService;
    private RouteRepository routeRepository;
    private StopRepository stopRepository;


    @GetMapping(path = "places")
    public ResponseEntity<?> getPlaces(@RequestBody GetPlacesRequest getPlacesRequest) {

        Route route = routeRepository.getReferenceById(getPlacesRequest.getRoute());
        Stop start = stopRepository.getReferenceById(getPlacesRequest.getStart());
        Stop finish = stopRepository.getReferenceById(getPlacesRequest.getFinish());
        Long startId = getPlacesRequest.getStart();
        Long finishId = getPlacesRequest.getFinish();

        if (!route.hasStopId(startId) || !route.hasStopId(finishId)) {
            throw new IllegalStateException("These stop(s) do(es)n't belong to this route");
        }

        List<Place> result = bookService.getReservedPlaces(route, start, finish);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }
}
