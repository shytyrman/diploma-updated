package com.technomad.diplomaupdated.controller.admin;

import com.technomad.diplomaupdated.model.Station;
import com.technomad.diplomaupdated.repository.StationRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/common/station")
@AllArgsConstructor
public class CommonController {

    private final StationRepository stationRepository;
    @PostMapping(path = "create")
    public ResponseEntity<?> createRoute(@RequestParam String name) {

        Station result = new Station(StringUtils.capitalize(name.toLowerCase()));
        stationRepository.save(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping(path = "list")
    public ResponseEntity<?> getRoutes() {

        List<Station> result = stationRepository.findAll();
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}