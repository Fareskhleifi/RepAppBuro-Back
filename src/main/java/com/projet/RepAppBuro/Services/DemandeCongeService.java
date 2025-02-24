package com.projet.RepAppBuro.Services;

import com.projet.RepAppBuro.DTO.DemandeCongeDto;
import com.projet.RepAppBuro.Entity.*;
import com.projet.RepAppBuro.Repository.DemandeCongeRepository;
import com.projet.RepAppBuro.Repository.RSCRepository;
import com.projet.RepAppBuro.Repository.TechnicienRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DemandeCongeService {

    private final DemandeCongeRepository demandeCongeRepository;
    private final UtilisateurService utilisateurService;
    private final TechnicienRepository technicienRepository;
    private final RSCRepository rscRepository;

    public DemandeConge createDemandeConge(DemandeCongeDto demandeConge) {
        Utilisateur user = utilisateurService.getUtilisateurById(demandeConge.getUtilisateur().getId());
        DemandeConge demande = new DemandeConge(user,demandeConge.getDateDebut()
                ,demandeConge.getDateFin(),demandeConge.getTypeConge(),
                StatutDemande.EN_ATTENTE,demandeConge.getCommentaire());
        return demandeCongeRepository.save(demande);
    }

    public List<DemandeConge> getAllDemandeDeConge() {
        return demandeCongeRepository.findAll();
    }

    public void annulerConge(Long id) {
        DemandeConge demande = demandeCongeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande de congé non trouvée"));
        demandeCongeRepository.delete(demande);
    }

    @Transactional
    public DemandeConge accepterConge(Long id) {
        DemandeConge demande = demandeCongeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande de congé non trouvée avec l'ID : " + id));
        demande.setStatut(StatutDemande.ACCEPTEE);
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(demande.getUtilisateur().getId());
        if (utilisateur instanceof RSC) {
            ((RSC) utilisateur).setStatus(StatusTravailleur.INACTIF);
            rscRepository.save((RSC) utilisateur);
        } else if (utilisateur instanceof Technicien) {
            ((Technicien) utilisateur).setStatus(StatusTravailleur.INACTIF);
            technicienRepository.save((Technicien) utilisateur);
        }
        return demandeCongeRepository.save(demande);
    }


    public DemandeConge refuserConge(Long id) {
        DemandeConge demande = demandeCongeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande de congé non trouvée"));
        demande.setStatut(StatutDemande.REJETEE);
        return demandeCongeRepository.save(demande);
    }

    public List<DemandeConge> getAllDemandeDeCongeByTravailleurId(Long UtilisateurId) {
        List<DemandeConge> demandes = demandeCongeRepository.findAllByUtilisateurId(UtilisateurId);
        demandes.forEach(demande -> demande.setUtilisateur(null));
        return demandes;
    }

    public DemandeConge updateConge(DemandeConge demandeConge) {
        return demandeCongeRepository.save(demandeConge);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void mettreAJourStatutApresConge() {
        List<DemandeConge> congesTermines = demandeCongeRepository.findByDateFinBeforeAndStatut(
                LocalDate.now(), StatutDemande.ACCEPTEE);

        for (DemandeConge demande : congesTermines) {
            Utilisateur utilisateur = demande.getUtilisateur();
            if (utilisateur instanceof Technicien) {
                Technicien technicien = (Technicien) utilisateur;
                technicien.setStatus(StatusTravailleur.ACTIF);
                technicienRepository.save(technicien);
            } else if (utilisateur instanceof RSC) {
                RSC rsc = (RSC) utilisateur;
                rsc.setStatus(StatusTravailleur.ACTIF);
                rscRepository.save(rsc);
            }
        }
    }
}