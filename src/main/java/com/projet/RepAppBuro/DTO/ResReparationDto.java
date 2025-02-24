package com.projet.RepAppBuro.DTO;

import com.projet.RepAppBuro.Entity.Piece;
import com.projet.RepAppBuro.Entity.ReparationPieceUtilisees;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResReparationDto {

    private Long idTechnicien;
    private Long idReparation;
    private String description;
    private LocalDate dateFinReparation;
    private double dureeMainOeuvre;
    private List<PieceUtiliseesDto> piecesUtilisees;
}
