package com.technomad.diplomaupdated.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode
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
    private RoutePiece masterRoutePiece;
    private Integer place;
    private UUID uuid;

    public TicketCodeHolder(RoutePiece masterRoutePiece, UUID uuid) {
        this.masterRoutePiece = masterRoutePiece;
        this.uuid = uuid;
    }

}
