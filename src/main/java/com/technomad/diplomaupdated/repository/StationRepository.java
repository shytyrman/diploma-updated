package com.technomad.diplomaupdated.repository;

import com.technomad.diplomaupdated.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    Station getByName(String station);
    Optional<Station> findStationByName(String name);
    Boolean existsStationByName(String name);
}
