package com.projet.RepAppBuro.Repository;

import com.projet.RepAppBuro.Entity.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceRepository extends JpaRepository<Piece, Long> {

}
