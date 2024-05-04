package com.technomad.diplomaupdated.controller.passenger;

import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.service.RouteSearchService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.ResponseCache;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
public class RouteSearchController {

    private final RouteSearchService routeSearchService;
    @GetMapping(path = "search")
    public ResponseEntity<?> searchForRoutes(@RequestParam String startStop, @RequestParam String finishStop) {

        List<Route> result = routeSearchService.search(startStop, finishStop);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @GetMapping(path = "search", params = {"startStop", "finishStop", "date"})
    public ResponseEntity<?> searchForRoutesByTime(@RequestParam String startStop, @RequestParam String finishStop, @RequestParam String date) {

        LocalDate localDate = LocalDate.parse(date);

        List<Route> result = routeSearchService.search(startStop, finishStop, localDate);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @GetMapping(path = "search", params = {"startStop", "finishStop", "date", "time"})
    public ResponseEntity<?> searchForRoutesByTime(@RequestParam String startStop, @RequestParam String finishStop, @RequestParam String date, @RequestParam String time) {

        LocalDateTime localDateTime = LocalDateTime.parse(date + "T" + time);

        List<Route> result = routeSearchService.search(startStop, finishStop, localDateTime);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

}
