package com.technomad.diplomaupdated.controller.admin;

import com.technomad.diplomaupdated.additional.StopsComparatorByOrder;
import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.appuser.AppUserDto;
import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.model.Stop;
import com.technomad.diplomaupdated.model.request.ReservePlacesRequest;
import com.technomad.diplomaupdated.model.state.StopState;
import com.technomad.diplomaupdated.repository.RoutePieceRepository;
import com.technomad.diplomaupdated.repository.RouteRepository;
import com.technomad.diplomaupdated.repository.StopRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/")
@AllArgsConstructor
public class BaseController {

    private final StopRepository stopRepository;
    private final RouteRepository routeRepository;
    private final RoutePieceRepository routePieceRepository;
    private final StopsComparatorByOrder stopsComparatorByOrder;

    @GetMapping
    public ResponseEntity<?> base(@AuthenticationPrincipal AppUser appUser) {
//        List<Stop> result = stopRepository.findAllByDepartureTime_Date(LocalDateTime.now().toLocalDate());
        AppUserDto appUserDto = AppUserDto.appUserToAppUserDto(appUser);
        return ResponseEntity.status(HttpStatus.OK).body(appUserDto);
    }

    @GetMapping(path = "test/getStopByName", params = "stopName")
    public ResponseEntity<?> getStopByName(@RequestParam String stopName) {
//        List<Stop> result = stopRepository.findAllByDepartureTime_Date(LocalDateTime.now().toLocalDate());
        List<Stop> result = stopRepository.findAllByStationName(stopName);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping(path = "test/createRoutePiece")
    public ResponseEntity<?> createRoutePiece(@RequestBody ReservePlacesRequest request) {
//        List<Stop> result = stopRepository.findAllByDepartureTime_Date(LocalDateTime.now().toLocalDate());
        Route route = routeRepository.findById(request.route()).get();
//        Stop start = stopRepository.findById(request.getStart()).get();
//        Stop finish = stopRepository.findById(request.getFinish()).get();
//
//        RoutePiece routePiece = new RoutePiece();
//        routePiece.setMasterRoute(route);
//        routePiece.setStartPoint(start);
//        routePiece.setEndPoint(finish);
//
//        routePieceRepository.save(routePiece);
        route.getRouteStations().sort(stopsComparatorByOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(route.getRouteStations());
    }

    @GetMapping(path = "test/getStopByState")
    public ResponseEntity<?> getStopByState(@RequestParam String state) {

        List<Stop> result;

        if (state.equals("passed")) {
            result = stopRepository.findAllByState(StopState.PASSED);
        }
        else if (state.equals("not-passed")) {
            result = stopRepository.findAllByState(StopState.NOTPASSED);
        }
        else {
            result = null;
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(path = "test/getStopByStationNameAndState")
    public ResponseEntity<?> getStopByStationNameAndState(@RequestParam String stopName, @RequestParam String stateName) {
//        List<Stop> result = stopRepository.findAllByDepartureTime_Date(LocalDateTime.now().toLocalDate());
        List<Stop> result;

        if (stateName.equals("passed")) {
            result = stopRepository.findAllByStationNameAndState(stopName, StopState.PASSED);
        }
        else if (stateName.equals("not-passed")) {
            result = stopRepository.findAllByStationNameAndState(stopName,StopState.NOTPASSED);
        }
        else {
            result = null;
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
