package com.projet.RepAppBuro.Repository;

import com.projet.RepAppBuro.Entity.DemandeConge;
import com.projet.RepAppBuro.Entity.StatutDemande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DemandeCongeRepository extends JpaRepository<DemandeConge, Long> {

    List<DemandeConge> findByStatut(StatutDemande statut);
    List<DemandeConge> findAllByUtilisateurId(Long UtilisateurId);
    List<DemandeConge> findByDateFinBeforeAndStatut(LocalDate date, StatutDemande statut);
}
