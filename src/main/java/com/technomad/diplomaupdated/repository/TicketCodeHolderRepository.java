package com.technomad.diplomaupdated.repository;

import com.technomad.diplomaupdated.model.Route;
import com.technomad.diplomaupdated.model.TicketCodeHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketCodeHolderRepository extends JpaRepository<TicketCodeHolder, Long> {

    Boolean existsTicketCodeHolderByUuidAndMasterRoutePiece_MasterRoute_Id(UUID uuid, Long routeId);
//    public List<TicketCodeHolder> findAllByUuidAndMasterRoutePiece_MasterRoute_IdOrderByMasterRoutePieceId(UUID uuid, Long routeId);
}
