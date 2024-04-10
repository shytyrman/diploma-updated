package com.technomad.diplomaupdated.controller;

import com.technomad.diplomaupdated.model.Stop;
import com.technomad.diplomaupdated.model.state.StopState;
import com.technomad.diplomaupdated.repository.RouteRepository;
import com.technomad.diplomaupdated.repository.StopRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/")
@AllArgsConstructor
public class BaseController {

    private final StopRepository stopRepository;
    private final RouteRepository routeRepository;

    @GetMapping
    public ResponseEntity<?> base() {
//        List<Stop> result = stopRepository.findAllByDepartureTime_Date(LocalDateTime.now().toLocalDate());
        return ResponseEntity.status(HttpStatus.CREATED).body("Logged succesfully!");
    }

    @GetMapping(path = "test/getStopByName", params = "stopName")
    public ResponseEntity<?> getStopByName(@RequestParam String stopName) {
//        List<Stop> result = stopRepository.findAllByDepartureTime_Date(LocalDateTime.now().toLocalDate());
        List<Stop> result = stopRepository.findAllByStationName(stopName);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
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

        return ResponseEntity.status(HttpStatus.FOUND).body(result);
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
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }
}
