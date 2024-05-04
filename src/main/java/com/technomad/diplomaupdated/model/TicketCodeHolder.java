package com.technomad.diplomaupdated.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class TicketCodeHolder {

    @Id
    @SequenceGenerator(
            name = "ticketHolder_sequence",
            sequenceName = "ticketHolder_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ticketHolder_sequence"
    )
    private Long id;

    @ManyToOne
    @JsonIgnore
    private RoutePiece masterRoutePiece;
    private Integer place;
    private UUID uuid;

    public TicketCodeHolder(RoutePiece masterRoutePiece, UUID uuid) {
        this.masterRoutePiece = masterRoutePiece;
        this.uuid = uuid;
    }

}
