package com.projet.RepAppBuro.Services;

import com.projet.RepAppBuro.Entity.Appareil;
import com.projet.RepAppBuro.Entity.DemandeReparation;
import com.projet.RepAppBuro.Entity.Reparation;
import com.projet.RepAppBuro.Repository.AppareilRepository;
import com.projet.RepAppBuro.Repository.DemandeReparationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DemandeReparationService {

    private final DemandeReparationRepository demandeReparationRepository;
    private final AppareilRepository appareilRepository;
    private final ReparationService reparationService;

    public List<DemandeReparation> getAllDemandes() {
        return demandeReparationRepository.findAll();
    }

    public DemandeReparation getDemandeById(Long id) {
        return demandeReparationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande de réparation introuvable"));
    }
    public List<DemandeReparation> getDemandesByClientId(Long clientId) {
        return demandeReparationRepository.findByClientId(clientId);
    }

    @Transactional
    public DemandeReparation saveDemande(DemandeReparation demandeReparation) {
        Reparation reparation = new Reparation();
        reparation.setDemandeReparation(demandeReparation);
        reparationService.saveOrUpdateReparation(reparation);
        return demandeReparationRepository.save(demandeReparation);
    }

    public DemandeReparation UpdateDemande(DemandeReparation demandeReparation) {
        Appareil app = appareilRepository.save(demandeReparation.getAppareil());
        return demandeReparationRepository.save(demandeReparation);
    }

    public void deleteDemande(Long id) {
        demandeReparationRepository.deleteById(id);
    }

    public List<DemandeReparation> getDemandesByEtat(String etat) {
        return demandeReparationRepository.findByEtat(etat);
    }

    public void archiverDemande(Long id) {
        DemandeReparation demande = demandeReparationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande introuvable"));
        demande.setArchived(true); // Marquer comme archivée
        demandeReparationRepository.save(demande);
    }

    public void desarchiverDemande(Long id) {
        DemandeReparation demande = demandeReparationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande introuvable"));
        demande.setArchived(false); // Marquer comme non archivée
        demandeReparationRepository.save(demande);
    }

    public List<DemandeReparation> getDemandesArchivees() {
        return demandeReparationRepository.findByIsArchived(true);
    }

    public List<DemandeReparation> getDemandesActives() {
        return demandeReparationRepository.findByIsArchived(false);
    }

    public boolean isDemandePayee(Long demandeId) {
        Optional<DemandeReparation> demandeReparationOptional = demandeReparationRepository.findById(demandeId);

        if (demandeReparationOptional.isPresent()) {
            DemandeReparation demandeReparation = demandeReparationOptional.get();
            Reparation reparation = demandeReparation.getReparation();
            if (reparation != null) {
                return reparation.getFacture() != null;
            }
        }
        return false;
    }

    public void commanceReparation(Long id) {
        DemandeReparation demande = demandeReparationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Demande non trouvée avec l'ID : " + id));
        demande.setEtat("En cours");
        demandeReparationRepository.save(demande);
    }

}
