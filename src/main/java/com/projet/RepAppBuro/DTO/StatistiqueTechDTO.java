package com.projet.RepAppBuro.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatistiqueTechDTO {

    private int reparationsEnAttente;
    private int reparationsEnCours;
    private int reparationsTerminees;
}
