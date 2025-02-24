package com.projet.RepAppBuro.Repository;

import com.projet.RepAppBuro.Entity.RSC;
import com.projet.RepAppBuro.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RSCRepository extends JpaRepository<RSC, Long> {

    Optional<RSC> findByEmail(String email);

}
