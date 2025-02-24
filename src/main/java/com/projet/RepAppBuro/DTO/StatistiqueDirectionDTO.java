package com.projet.RepAppBuro.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatistiqueDirectionDTO {
    private int techniciensActifs;
    private int nombreClients;
    private int reparationsEnCours;
    private int reparationsTerminees;
    private double beneficesCeMois;
    private double depensesCeMois;
    private double facturesAEncaisser;
    private double margeBeneficiaireNette;
}
