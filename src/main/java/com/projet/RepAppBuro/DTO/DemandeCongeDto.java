package com.projet.RepAppBuro.DTO;

import com.projet.RepAppBuro.Entity.StatutDemande;
import com.projet.RepAppBuro.Entity.TypeConge;
import com.projet.RepAppBuro.Entity.Utilisateur;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemandeCongeDto {

    private Utilisateur utilisateur;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    @Enumerated(EnumType.STRING)
    private TypeConge typeConge;
    private String commentaire;
}
