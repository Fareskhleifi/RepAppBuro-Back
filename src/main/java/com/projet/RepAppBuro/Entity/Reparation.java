package com.projet.RepAppBuro.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Reparation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private DemandeReparation demandeReparation;

    @ManyToOne
    private Technicien technicien;

    private LocalDate dateFinReparation;
    private Double dureeMainOeuvre;
    @Getter
    private static Double tarifMainOeuvre=15.0; // hathi tarif 15dt/heure
    private String description;

    @OneToMany(mappedBy = "reparation")
    private List<ReparationPieceUtilisees> reparationPieces;


    @OneToOne
    @JoinColumn(name = "facture_id")
    private Facture facture;


}



