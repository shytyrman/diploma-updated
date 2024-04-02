package com.technomad.diplomaupdated.controller;

import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.model.Stop;
import com.technomad.diplomaupdated.model.StopState;
import com.technomad.diplomaupdated.repository.RouteRepository;
import com.technomad.diplomaupdated.service.MonitoringService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/monitoring")
@AllArgsConstructor
public class MonitoringController {

    private final RouteRepository routeRepository;
    private final MonitoringService monitoringService;

    @PostMapping(path = "state/next")
    public ResponseEntity<?> changeRouteStateToNext(@AuthenticationPrincipal AppUser appUser, @RequestParam Long routeId) {

        Route route = routeRepository.getReferenceById(routeId);
        List<Stop> stops = route.getRouteStations();

        Integer onStayStateStopId = monitoringService.getOnStayStateId(route);
        Integer firstNotPassedStopId = monitoringService.getFirstNotPassedStateId(route);
        Stop onStayStateStop = monitoringService.getOnStayState(route);

        if (onStayStateStop.equals(null)) {
            stops.get(firstNotPassedStopId).setState(StopState.STAY);
        }
        else {
            onStayStateStop.setState(StopState.PASSED);
        }

        return  ResponseEntity.status(HttpStatus.CREATED).body("Changed succesfully!");
    }
}
