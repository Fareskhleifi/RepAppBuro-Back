package com.projet.RepAppBuro.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class TypePiece {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private double tarifH;

    @OneToMany(mappedBy = "typePiece")
    @JsonIgnore
    private List<Piece> piecesRechange;
}

