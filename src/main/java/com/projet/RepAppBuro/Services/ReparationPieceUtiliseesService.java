package com.projet.RepAppBuro.Services;

import com.projet.RepAppBuro.Entity.Piece;
import com.projet.RepAppBuro.Repository.ReparationPieceUtiliseesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReparationPieceUtiliseesService {
    private final ReparationPieceUtiliseesRepository reparationPieceUtiliseesRepository;

    public List<Piece> getPiecesByReparationId(Long reparationId) {
        return reparationPieceUtiliseesRepository.findPiecesByReparationId(reparationId);
    }
}
