package com.projet.RepAppBuro.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactureDto {
    private Long idFacture;
    private Long idReparation;
    private String nomClient;
    private LocalDate dateFacture;
    private double montantTotal;
    private boolean isArchived;



}
