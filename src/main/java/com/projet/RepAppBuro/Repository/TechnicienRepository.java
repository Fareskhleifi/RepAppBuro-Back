package com.projet.RepAppBuro.Repository;

import com.projet.RepAppBuro.Entity.StatusTravailleur;
import com.projet.RepAppBuro.Entity.Technicien;
import com.projet.RepAppBuro.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TechnicienRepository extends JpaRepository<Technicien, Long> {

    Optional<Technicien> findByEmail(String email);
    int countByStatus(StatusTravailleur status);


}
