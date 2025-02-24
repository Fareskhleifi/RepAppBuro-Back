package com.projet.RepAppBuro.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatistiquesRSCDTO {

    private int totalDemandesRecuesCeMois;
    private int totalDemandesEnCours;
    private int totalDemandesCloturees;
    private int totalClients;
    private int nouveauxClientsCeMois;
    private double montantTotalFactureCeMois;
}
