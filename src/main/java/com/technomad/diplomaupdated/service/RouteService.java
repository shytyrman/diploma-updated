package com.technomad.diplomaupdated.service;

import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.model.RouteState;
import com.technomad.diplomaupdated.model.Stop;
import com.technomad.diplomaupdated.model.StopState;
import com.technomad.diplomaupdated.repository.RouteRepository;
import com.technomad.diplomaupdated.repository.StationRepository;
import com.technomad.diplomaupdated.repository.StopRepository;
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
    private final StopRepository stopRepository;

    public Route addRoute(CreateRouteRequest request, AppUser appUser) {

        ArrayList<CreateRouteStopRequest> stopRequests = request.getStops();
        ArrayList<Stop> stops = new ArrayList<>();
        Iterator<CreateRouteStopRequest> iterator = stopRequests.iterator();
        Route route = new Route();
        route.setDriver(appUser);
        int order = 0;
//
        repository.save(route);
        while (iterator.hasNext()) {
            CreateRouteStopRequest element = iterator.next();
            Stop currentStop = new Stop();
            
            if (stationRepository.getByName(element.getStation()) == null) {
                throw new IllegalStateException("Wrong stop name, try with correct station name!");
            }

            currentStop.setArrivalTime(element.getArrivalTime());
            currentStop.setDepartureTime(element.getDepartureTime());
            currentStop.setCost(element.getCost());
            currentStop.setStation(stationRepository.getByName(element.getStation()));
            currentStop.setMasterRoute(route);
            currentStop.setState(StopState.NOTPASSED);
            currentStop.setOrderInList(order++);
            stopRepository.save(currentStop);
        }
//
        route.setDescription(request.getDescription());
        route.setRouteState(RouteState.AVAILABLE);
        repository.save(route);
        return route;
    }

//    public ArrayList<Route> getRoutes(AppUser appUser) {
//        ArrayList<Route> result = repository.findRoutesByDriver(appUser.getUsername().toString()).get();
//        return result;
//    }
}
