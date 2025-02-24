package com.projet.RepAppBuro.DTO;

import com.projet.RepAppBuro.Entity.Piece;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FicheDto {

    private List<Piece> pieces;
    private List<TechnicienDto> techniciens;
}
