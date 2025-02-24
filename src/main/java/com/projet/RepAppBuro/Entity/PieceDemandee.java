package com.projet.RepAppBuro.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class PieceDemandee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "piece_id")
    private Piece piece;

    private LocalDate dateCommande;


    @Enumerated(EnumType.STRING)
    private EtatDemmandePiece etat;

    private int quantiteCommandee;

}
