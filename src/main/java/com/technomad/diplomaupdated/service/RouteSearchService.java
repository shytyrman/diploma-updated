package com.technomad.diplomaupdated.service;

import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.model.state.RouteState;
import com.technomad.diplomaupdated.model.Stop;
import com.technomad.diplomaupdated.model.state.StopState;
import com.technomad.diplomaupdated.repository.StopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RouteSearchService {

    private final StopRepository stopRepository;
    public List<Route> search(String startStop, String finishStop) {

        List<Stop> foundStartStops = stopRepository.findAllByStationNameAndStateAndMasterRouteRouteState(startStop, StopState.NOTPASSED, RouteState.AVAILABLE);
        return getCompatibleRoutes(finishStop, foundStartStops);
    }

    public List<Route> search(String startStop, String finishStop, LocalDateTime localDateTime) {

        List<Stop> foundStartStops = stopRepository.findAllByStationNameAndStateAndMasterRouteRouteStateAndDepartureTime(startStop, StopState.NOTPASSED, RouteState.AVAILABLE, localDateTime);
        return getCompatibleRoutes(finishStop, foundStartStops);
    }

    public List<Route> search(String startStop, String finishStop, LocalDate localDate) {

        List<Stop> foundStartStops = stopRepository.findAllByStationNameAndStateAndMasterRouteRouteState(startStop, StopState.NOTPASSED, RouteState.AVAILABLE);
        List<Stop> foundStartStopsFilteredByDate = new ArrayList<>();

        for (Stop stop : foundStartStops
             ) {
            if (stop.getDepartureTime().toLocalDate().equals(localDate)) {
                foundStartStopsFilteredByDate.add(stop);
            }
        }

//        foundStartStopsFilteredByDate.sort();

        return getCompatibleRoutes(finishStop, foundStartStopsFilteredByDate);
    }

    private List<Route> getCompatibleRoutes(String finishStop, List<Stop> foundStartStops) {
        List<Route> result = new ArrayList<>();

        for (Stop firstStop : foundStartStops) {
            Route tempRoute = firstStop.getMasterRoute();
            List<Stop> tempStops = tempRoute.getRouteStations();
            for (Stop secondStop : tempStops) {
                if (secondStop.getStation().getName().equals(finishStop)) {
                    if (firstStop.getOrderInList() < secondStop.getOrderInList()) {
                        result.add(tempRoute);
                    }
                }
            }
        }
        return result;
    }
}
