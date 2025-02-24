package com.projet.RepAppBuro.Repository;

import com.projet.RepAppBuro.Entity.DemandeReparation;
import com.projet.RepAppBuro.Entity.Reparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReparationRepository extends JpaRepository<Reparation, Long> {
    List<Reparation> findByTechnicienId(Long technicienId);

    Reparation findByDemandeReparationId(Long id);

    @Query("SELECT r FROM Reparation r WHERE r.dateFinReparation IS NULL")
    List<Reparation> findReparationsNonTerminees();

    @Query("SELECT r FROM Reparation r WHERE r.dateFinReparation IS NOT NULL")
    List<Reparation> findReparationsTerminees();

    @Query("SELECT r.demandeReparation FROM Reparation r WHERE r.id = :reparationId")
    DemandeReparation findDemandeReparationByReparationId(@Param("reparationId") Long reparationId);

    int countByDateFinReparationIsNull();
    int countByDateFinReparationIsNotNull();

    @Query("SELECT COUNT(r) FROM Reparation r JOIN r.demandeReparation d WHERE d.etat = 'Terminé' AND MONTH(r.dateFinReparation) = MONTH(CURRENT_DATE) AND YEAR(r.dateFinReparation) = YEAR(CURRENT_DATE)")
    int countReparationsTermineesCeMois();

    @Query("""
    SELECT SUM(rpu.qte * rp.prixAchat) 
    FROM ReparationPieceUtilisees rpu 
    JOIN rpu.piece rp 
    JOIN rpu.reparation r 
    WHERE MONTH(r.dateFinReparation) = MONTH(CURRENT_DATE) 
      AND YEAR(r.dateFinReparation) = YEAR(CURRENT_DATE)
""")
    double calculateTotalDepensesThisMonth();

}
