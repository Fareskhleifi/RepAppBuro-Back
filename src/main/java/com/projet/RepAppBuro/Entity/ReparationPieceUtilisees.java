package com.projet.RepAppBuro.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ReparationPieceUtilisees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int qte;

    @ManyToOne
    @JoinColumn(name = "reparation_id")
    @JsonIgnore
    private Reparation reparation;

    @ManyToOne
    @JoinColumn(name = "piece_id")
    @JsonIgnore
    private Piece piece;
}
