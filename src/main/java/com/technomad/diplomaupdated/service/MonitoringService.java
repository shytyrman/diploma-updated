package com.technomad.diplomaupdated.service;

import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.model.Stop;
import com.technomad.diplomaupdated.model.StopState;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MonitoringService {

    public Stop getOnStayState(Route route) {

        ArrayList<Stop> stops = route.getRouteStations();

        for (Stop stop : stops
             ) {
            if (stop.getState().equals(StopState.STAY)) {
                return stop;
            }
        }
        return null;
    }

    public Integer getOnStayStateId(Route route) {

        ArrayList<Stop> stops = route.getRouteStations();

        for (int i = 0; i < stops.size(); i++) {
            if (stops.get(i).getState().equals(StopState.STAY)) {
                return i;
            }
        }

        return null;
    }

    public Integer getFirstNotPassedStateId(Route route) {

        ArrayList<Stop> stops = route.getRouteStations();

        for (int i = 0; i < stops.size(); i++) {
            if (stops.get(i).getState().equals(StopState.NOTPASSED)) {
                return i;
            }
        }
        return null;
    }
}
