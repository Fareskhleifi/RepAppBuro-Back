package com.projet.RepAppBuro.DTO;

import com.projet.RepAppBuro.Entity.Piece;
import com.projet.RepAppBuro.Entity.Reparation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailsFactureDto {

    private Long idReparation;
    private Long idOfLastFacture;
    private String Description;
    private double DureeMainOeuvre;
    private double tarifMainOeuvre;
    private double TotalReparation;
    private double TotalHT;
    private double TotalTTC;
    private List<Piece> picesesUtilisees;

}
