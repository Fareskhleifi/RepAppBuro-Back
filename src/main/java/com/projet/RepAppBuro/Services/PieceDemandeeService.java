package com.projet.RepAppBuro.Services;

import com.projet.RepAppBuro.Entity.EtatDemmandePiece;
import com.projet.RepAppBuro.Entity.Piece;
import com.projet.RepAppBuro.Entity.PieceDemandee;
import com.projet.RepAppBuro.Repository.PieceDemandeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PieceDemandeeService {
    private final PieceDemandeeRepository pieceDemandeeRepository;

    public List<PieceDemandee> getAll() {
        return pieceDemandeeRepository.findAll();
    }

    public PieceDemandee saveOrUpdate(PieceDemandee pieceDemandee) {
        pieceDemandee.setEtat(EtatDemmandePiece.EN_ATTENTE);
        return pieceDemandeeRepository.save(pieceDemandee);
    }

    public void deleteDemande(Long id) {
        pieceDemandeeRepository.deleteById(id);
    }

    public PieceDemandee accepterDemande(Long id) {
        PieceDemandee demande = pieceDemandeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Demande non trouvée"));
        demande.setEtat(EtatDemmandePiece.CONFIRME);
        Piece piece = demande.getPiece();
        piece.setQuantiteStock(piece.getQuantiteStock() + demande.getQuantiteCommandee());
        pieceDemandeeRepository.save(demande);
        return pieceDemandeeRepository.save(demande);
    }

    public PieceDemandee refuserDemande(Long id) {
        PieceDemandee demande = pieceDemandeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Demande non trouvée"));
        demande.setEtat(EtatDemmandePiece.REFUSE);
        return pieceDemandeeRepository.save(demande);
    }
}
