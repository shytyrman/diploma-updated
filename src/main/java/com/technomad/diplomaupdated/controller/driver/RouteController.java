package com.technomad.diplomaupdated.controller.driver;

import com.technomad.diplomaupdated.additional.StopsComparatorByOrder;
import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.repository.RouteRepository;
import com.technomad.diplomaupdated.model.request.CreateRouteRequest;
import com.technomad.diplomaupdated.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/driver/route")
@AllArgsConstructor
public class RouteController {

    private final RouteService routeService;
    private final RouteRepository routeRepository;
    private final StopsComparatorByOrder stopsComparatorByOrder;

    @GetMapping
    public ResponseEntity<?> getRoutes(@AuthenticationPrincipal AppUser appUser) {

        List<Route> result = routeRepository.findByDriverId(appUser.getId());

        for (Route route : result
             ) {
            route.getRouteStations().sort(stopsComparatorByOrder);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping(params = {"routeId"})
    public ResponseEntity<?> getRouteById(@AuthenticationPrincipal AppUser appUser, @RequestParam Long routeId) {

        Optional<Route> result = routeRepository.findById(routeId);

        return ResponseEntity.status(HttpStatus.FOUND).body(result.get());
    }

    @PostMapping(path = "create")
    public ResponseEntity<?> createRoute(@AuthenticationPrincipal AppUser appUser, @RequestBody CreateRouteRequest request) {

        Route route = routeService.addRoute(request, appUser);
        routeService.addPiecesToRoute(route);
        return ResponseEntity.status(HttpStatus.CREATED).body(route);
    }

//    @PostMapping(path = "start")
//    public ResponseEntity<?> startRoute(@AuthenticationPrincipal AppUser appUser, @RequestParam Long routeId) {
//
//        Optional<Route> optionalRoute = routeRepository.findById(routeId);
//        Route route = optionalRoute.get();
//
//        route.setIsActive(false);
//        return ResponseEntity.status(HttpStatus.OK).body("Route is started!");
//    }
}
