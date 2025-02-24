package com.projet.RepAppBuro.Repository;

import com.projet.RepAppBuro.Entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {

    List<Facture> findByIsArchived(boolean b);

    Optional<Facture> findTopByOrderByIdDesc();

    @Query("SELECT SUM(f.montantTotal) FROM Facture f WHERE MONTH(f.dateFacture) = MONTH(CURRENT_DATE) AND YEAR(f.dateFacture) = YEAR(CURRENT_DATE)")
    double calculateTotalBeneficesThisMonth();

//    @Query("SELECT SUM(p.prixAchat * r.qte) FROM ReparationPieceUtilisees r JOIN r.piece p WHERE MONTH(r.reparation.facture.dateFacture) = MONTH(CURRENT_DATE) AND YEAR(r.reparation.facture.dateFacture) = YEAR(CURRENT_DATE)")
//    double calculateTotalDepensesThisMonth();

    @Query("SELECT SUM(f.montantTotal) FROM Facture f WHERE f.isArchived = false")
    double calculateFacturesAEncaisser();

    @Query("SELECT SUM(f.montantTotal) FROM Facture f WHERE MONTH(f.dateFacture) = MONTH(CURRENT_DATE) AND YEAR(f.dateFacture) = YEAR(CURRENT_DATE)")
    double montantTotalFactureCeMois();


}
