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

    public List<Ticket> findAllByTicketOwner(AppUser appUser);

    public Optional<Ticket> findTicketByUuidAndForRoute_Id(UUID uuid, Long routeId);
    public List<Ticket> findAllByUuidAndForRoute_IdOrderById(UUID uuid, Long routeId);
}

