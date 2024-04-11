package com.technomad.diplomaupdated.repository;

import com.technomad.diplomaupdated.model.TicketCodeHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketCodeHolderRepository extends JpaRepository<TicketCodeHolder, Long> {
}
