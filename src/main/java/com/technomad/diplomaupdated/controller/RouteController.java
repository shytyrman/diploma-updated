package com.technomad.diplomaupdated.controller;

import com.technomad.diplomaupdated.request.CreateRouteRequest;
import com.technomad.diplomaupdated.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/driver/route")
@AllArgsConstructor
public class RouteController {

    private final RouteService routeService;

//    @GetMapping
//    public ResponseEntity<?> getRoutes(@AuthenticationPrincipal AppUser appUser) {
//        ArrayList<Route> result = routeService.getRoutes(appUser);
//        return ResponseEntity.status(HttpStatus.CREATED).body(result);
//    }
    @PostMapping(path = "create")
    public ResponseEntity<?> createRoute(@RequestBody CreateRouteRequest request) {

        routeService.addRoute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created succesfully!");
    }
}
