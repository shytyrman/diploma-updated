package com.technomad.diplomaupdated.service;

import com.technomad.diplomaupdated.additional.StopsComparatorByOrder;
import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.model.RouteState;
import com.technomad.diplomaupdated.model.Stop;
import com.technomad.diplomaupdated.model.StopState;
import com.technomad.diplomaupdated.repository.StopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RouteSearchService {

    private final StopRepository stopRepository;
    public List<Route> search(String startStop, String finishStop) {

        List<Stop> foundStartStops = stopRepository.findAllByStationNameAndStateAndMasterRouteRouteState(startStop, StopState.NOTPASSED, RouteState.AVAILABLE);
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
