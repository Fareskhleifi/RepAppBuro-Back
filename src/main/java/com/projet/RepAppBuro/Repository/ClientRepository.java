package com.projet.RepAppBuro.Repository;

import com.projet.RepAppBuro.Entity.Client;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByNom(String nom);

    @Query("SELECT COUNT(c) FROM Client c")
    int countClientsTotaux();

    @Query("SELECT COUNT(c) FROM Client c " +
            "JOIN c.demandeReparations dr " +
            "WHERE MONTH(dr.dateDepot) = MONTH(CURRENT_DATE) " +
            "AND YEAR(dr.dateDepot) = YEAR(CURRENT_DATE)")
    int countNouveauxClientsCeMois();



}
