package com.technomad.diplomaupdated.controller;

import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.repository.RouteRepository;
import com.technomad.diplomaupdated.request.CreateRouteRequest;
import com.technomad.diplomaupdated.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/driver/route")
@AllArgsConstructor
public class RouteController {

    private final RouteService routeService;
    private final RouteRepository routeRepository;

    @GetMapping
    public ResponseEntity<?> getRoutes(@AuthenticationPrincipal AppUser appUser) {

        List<Route> result = routeRepository.findByDriverId(appUser.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

//    @GetMapping
//    public ResponseEntity<?> getRouteById(@AuthenticationPrincipal AppUser appUser, @RequestParam Long routeId) {
//
//        Optional<Route> result = routeRepository.findById(routeId);
//
//        return ResponseEntity.status(HttpStatus.FOUND).body(result.isPresent());
//    }

    @PostMapping(path = "create")
    public ResponseEntity<?> createRoute(@AuthenticationPrincipal AppUser appUser, @RequestBody CreateRouteRequest request) {

        routeService.addRoute(request, appUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created succesfully!");
    }
}
