package com.projet.RepAppBuro.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemandeConge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    private Utilisateur utilisateur;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private TypeConge typeConge;

    @Enumerated(EnumType.STRING)
    private StatutDemande statut;

    private String commentaire;

    public DemandeConge(Utilisateur utilisateur, LocalDate dateDebut, LocalDate dateFin, TypeConge typeConge, StatutDemande statut, String commentaire) {
        this.utilisateur = utilisateur;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.typeConge = typeConge;
        this.statut = statut;
        this.commentaire = commentaire;
    }
}