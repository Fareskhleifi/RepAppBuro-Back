package com.projet.RepAppBuro.Services;

import com.projet.RepAppBuro.Entity.Piece;
import com.projet.RepAppBuro.Repository.PieceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PieceService {

    private final PieceRepository pieceRepository;

    public List<Piece> getAllPieces() {
        return pieceRepository.findAll();
    }

    public Piece getPieceById(Long id) {
        return pieceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pièce introuvable"));
    }

    public Piece saveOrUpdatePiece(Piece piece) {
        return pieceRepository.save(piece);
    }

    public void deletePiece(Long id) {
        pieceRepository.deleteById(id);
    }
}
