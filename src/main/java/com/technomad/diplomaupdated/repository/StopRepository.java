package com.technomad.diplomaupdated.repository;

import com.technomad.diplomaupdated.model.RouteState;
import com.technomad.diplomaupdated.model.Stop;
import com.technomad.diplomaupdated.model.StopState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {

//    public List<Stop> findAllByDepartureTime_Date(LocalDate departureTime_date);
    public List<Stop> findAllByStationName(String name);
    public List<Stop> findAllByState(StopState state);
    public List<Stop> findAllByStationNameAndState(String name, StopState state);
    public List<Stop> findAllByStationNameAndStateAndMasterRouteRouteState(String stopName, StopState stopState, RouteState routeState);
    public List<Stop> findAllByStationNameAndStateAndMasterRouteRouteStateAndDepartureTime(String stopName, StopState state, RouteState routeState, LocalDateTime localDateTime);
}
