package com.technomad.diplomaupdated.repository;

import com.technomad.diplomaupdated.model.RoutePiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutePieceRepository extends JpaRepository<RoutePiece, Long> {
}
