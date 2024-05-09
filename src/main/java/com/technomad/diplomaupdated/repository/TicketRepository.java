package com.technomad.diplomaupdated.repository;

import com.technomad.diplomaupdated.appuser.AppUser;
import com.technomad.diplomaupdated.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByTicketOwner(AppUser appUser);
    Optional<Ticket> findTicketByUuidAndForRoute_Id(UUID uuid, Long routeId);
    Optional<Ticket> findTicketByTicketOwnerAndId(AppUser appUser, Long id);
    List<Ticket> findAllByUuidAndForRoute_IdOrderById(UUID uuid, Long routeId);
}

