package com.technomad.diplomaupdated.controller;

import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.service.RouteSearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.ResponseCache;
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
}
