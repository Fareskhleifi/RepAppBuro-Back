package com.projet.RepAppBuro.Repository;

import com.projet.RepAppBuro.Entity.PieceDemandee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceDemandeeRepository extends JpaRepository<PieceDemandee, Long> {
}
