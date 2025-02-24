package com.projet.RepAppBuro.Repository;

import com.projet.RepAppBuro.Entity.DemandeReparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeReparationRepository extends JpaRepository<DemandeReparation, Long> {

    List<DemandeReparation> findByClientId(Long clientId);

    List<DemandeReparation> findByEtat(String etat);

    @Query("SELECT COUNT(d) FROM DemandeReparation d WHERE d.client.id = :clientId AND d.etat = 'En cours' AND d.isArchived = false")
    Long countDemandesEnCoursByClientId(@Param("clientId") Long clientId);

    List<DemandeReparation> findByIsArchived(boolean b);

    @Query("SELECT COUNT(d) FROM DemandeReparation d WHERE d.etat = 'En attente'")
    int countAppareilsEnAttenteReparation();

    @Query("SELECT COUNT(d) FROM DemandeReparation d WHERE d.etat = 'En cours'")
    int countReparationsEnCours();

    @Query("SELECT COUNT(d) FROM DemandeReparation d WHERE MONTH(d.dateDepot) = MONTH(CURRENT_DATE) AND YEAR(d.dateDepot) = YEAR(CURRENT_DATE)")
    int countDemandesReçuesCeMois();

    @Query("SELECT COUNT(d) FROM DemandeReparation d WHERE d.etat = 'En cours' AND d.isArchived = false")
    int countDemandesEnCours();

    @Query("SELECT COUNT(d) FROM DemandeReparation d WHERE d.etat = 'Terminé' AND d.isArchived = false")
    int countDemandesCloturees();


}
