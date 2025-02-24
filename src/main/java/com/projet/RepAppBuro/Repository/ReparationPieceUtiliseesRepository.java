package com.projet.RepAppBuro.Repository;

import com.projet.RepAppBuro.Entity.Piece;
import com.projet.RepAppBuro.Entity.ReparationPieceUtilisees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReparationPieceUtiliseesRepository  extends JpaRepository<ReparationPieceUtilisees, Long> {

    @Query("SELECT rp.piece FROM ReparationPieceUtilisees rp WHERE rp.reparation.id = :reparationId")
    List<Piece> findPiecesByReparationId(@Param("reparationId") Long reparationId);
}
