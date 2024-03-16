package com.technomad.diplomaupdated.service;

import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.model.Stop;
import com.technomad.diplomaupdated.repository.RouteRepository;
import com.technomad.diplomaupdated.repository.StationRepository;
import com.technomad.diplomaupdated.request.CreateRouteRequest;
import com.technomad.diplomaupdated.request.CreateRouteStopRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

@Service
@AllArgsConstructor
@Getter
@Setter
public class RouteService {

    private final RouteRepository repository;
    private final StationRepository stationRepository;

    public void addRoute(CreateRouteRequest request) {

        ArrayList<CreateRouteStopRequest> stopRequests = request.getStops();
        ArrayList<Stop> stops = new ArrayList<>();
        Iterator<CreateRouteStopRequest> iterator = stopRequests.iterator();
        Route route = new Route();

        while (iterator.hasNext()) {
            CreateRouteStopRequest element = iterator.next();
            Stop currentStop = new Stop();
            currentStop.setArrivalTime(element.getArrivalTime());
            currentStop.setDepartureTime(element.getDepartureTime());
            currentStop.setCost(element.getCost());
            currentStop.setStation(stationRepository.getByName(element.getStation()));
            currentStop.setMasterRoute(route);
            stops.add(currentStop);
        }

        route.setRouteStations(stops);
        route.setDescription(request.getDescription());
        repository.save(route);
    }

    public ArrayList<Route> getRoutes() {
        ArrayList<Route> result = repository.getUsersRoutes();
        return result;
    }
}
