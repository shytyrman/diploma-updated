package com.technomad.diplomaupdated.repository;

import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    //ArrayList<Route> getUsersRoutes();
//    public Optional<Route> findByUsername(String username);
//    public Optional<ArrayList<Route>> findRoutesByDriver(String driver);

    public List<Route> findByDriverId(Long id);
}
