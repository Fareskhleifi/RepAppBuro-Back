package com.projet.RepAppBuro.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Piece {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String nom;
    private String categorie; //
    private Double tempsReparation; //
    private Double prixAchat;
    private Double prixVenteHT;
    private Double prixVenteTTC;
    private int quantiteStock;

//    @ManyToMany(mappedBy = "piecesUtilisees")
//    @JsonIgnore
//    private List<Reparation> reparations;

    @ManyToOne
    @JoinColumn(name = "type_piece_id")
    private TypePiece typePiece;

    @OneToMany(mappedBy = "piece")
    private List<ReparationPieceUtilisees> reparationPieces;

}

