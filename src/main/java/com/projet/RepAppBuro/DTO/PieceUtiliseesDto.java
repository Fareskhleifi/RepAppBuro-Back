package com.projet.RepAppBuro.DTO;

import com.projet.RepAppBuro.Entity.Piece;
import com.projet.RepAppBuro.Entity.Reparation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PieceUtiliseesDto {

    private int qte;
    private Reparation reparation;
    private Piece piece;
}
