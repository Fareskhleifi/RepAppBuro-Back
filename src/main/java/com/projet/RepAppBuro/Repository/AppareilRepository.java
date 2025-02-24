package com.projet.RepAppBuro.Repository;

import com.projet.RepAppBuro.Entity.Appareil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppareilRepository extends JpaRepository<Appareil, Long> {

}
