package com.technomad.diplomaupdated.repository;

import com.technomad.diplomaupdated.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    ArrayList<Route> getUsersRoutes();
}
