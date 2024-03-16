package com.technomad.diplomaupdated.repository;

import com.technomad.diplomaupdated.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    Station getByName(String station);
}
