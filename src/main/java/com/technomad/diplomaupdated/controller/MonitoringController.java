package com.technomad.diplomaupdated.controller;

import com.technomad.diplomaupdated.additional.StopsComparator;
import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.model.state.RouteState;
import com.technomad.diplomaupdated.model.Stop;
import com.technomad.diplomaupdated.model.state.StopState;
import com.technomad.diplomaupdated.repository.RouteRepository;
import com.technomad.diplomaupdated.repository.StopRepository;
import com.technomad.diplomaupdated.service.MonitoringService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/monitoring")
@AllArgsConstructor
public class MonitoringController {

    private final RouteRepository routeRepository;
    private final MonitoringService monitoringService;
    private final StopRepository stopRepository;
    private final StopsComparator stopsComparator;

    @PostMapping(path = "state/next")
    public ResponseEntity<?> changeRouteStateToNext(@AuthenticationPrincipal AppUser appUser, @RequestParam Long routeId) {

        Route route = routeRepository.getReferenceById(routeId);
        List<Stop> stops = route.getRouteStations();

        Boolean notPassedExists = monitoringService.isNotPassedExists(route);

        if (!route.getRouteState().equals(RouteState.ACTIVE)) {
            throw new IllegalStateException("Route isn't active!");
        }

        if (!notPassedExists) {
            route.setRouteState(RouteState.NON_ACTIVE);
            routeRepository.save(route);
            return ResponseEntity.status(HttpStatus.CREATED).body("Route is done");
        }

        Integer firstNotPassedStopId = monitoringService.getFirstNotPassedStateId(route);
        Boolean isOnStayStateExists = monitoringService.isOnStayStateExists(route);
        Stop onStayStateStop = monitoringService.getOnStayState(route);

        if (!isOnStayStateExists) {
            Stop currentStop = stops.get(firstNotPassedStopId);
            currentStop.setState(StopState.STAY);
            stopRepository.save(currentStop);
        }
        else {
            onStayStateStop.setState(StopState.PASSED);
            stopRepository.save(onStayStateStop);
        }

        stops.sort(stopsComparator);
        return  ResponseEntity.status(HttpStatus.CREATED).body(stops.get(firstNotPassedStopId));
    }

    @PostMapping(path = "launch")
    public ResponseEntity<?> launch(@AuthenticationPrincipal AppUser appUser, @RequestParam Long routeId) {

        Route route = routeRepository.getReferenceById(routeId);
        route.setRouteState(RouteState.ACTIVE);
        routeRepository.save(route);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Route launched succesfully!");
    }
}
