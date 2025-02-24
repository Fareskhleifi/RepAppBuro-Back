package com.projet.RepAppBuro.Controleurs;

import com.projet.RepAppBuro.DTO.*;
import com.projet.RepAppBuro.Entity.*;
import com.projet.RepAppBuro.Services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tech")
@RequiredArgsConstructor
public class TechnicienController {

    private final PieceService pieceService;
    private final ReparationPieceUtiliseesService reparationPieceUtiliseesService;
    private final ReparationService reparationService;
    private final DemandeReparationService demandeReparationService;
    private final PieceDemandeeService pieceDemandeeService;
    private final StatistiqueService statistiqueService;
    private final DemandeCongeService demandeCongeService;

    @GetMapping("/getAllPieces")
    public ResponseEntity<List<Piece>> getAllPieces() {
        return ResponseEntity.ok(pieceService.getAllPieces());
    }

    @GetMapping("/getReparationsNonTerminees")
    public ResponseEntity<List<Reparation>> getReparationsNonTerminees() {
        List<Reparation> reparations = reparationService.getReparationsNonTerminees();
        return ResponseEntity.ok(reparations);
    }


    @GetMapping("/getReparationsTerminees")
    public ResponseEntity<List<Reparation>> getReparationsTerminees() {
        List<Reparation> reparations = reparationService.getReparationsTerminees();
        return ResponseEntity.ok(reparations);
    }

    @PutMapping("/commanceReparation/{id}")
    public ResponseEntity<String> changerEtat(@PathVariable Long id) {
        try {
            demandeReparationService.commanceReparation(id);
            return ResponseEntity.ok("L'état de la demande a été mis à jour avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/pieces-with-technicians")
    public FicheDto getPiecesWithTechnicians() {
        return reparationService.getPiecesWithTechnicians();
    }

    @PutMapping("/finaliserReparation")
    public ResponseEntity<String> finaliserReparation(@RequestBody ResReparationDto resReparationDto) {
        try {
            reparationService.finaliserReparation(resReparationDto);
            return ResponseEntity.ok("Réparation finalisée et état de la demande mis à jour en 'Terminé'");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la finalisation de la réparation : " + e.getMessage());
        }
    }

    @PutMapping("/irreparable/{id}")
    public ResponseEntity<String> irreparableAppareil(@PathVariable Long id) {
        try {
            reparationService.irreparableAppareil(id);
            return ResponseEntity.ok("Appareil marquer comme irréparable");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la mise a jour de la réparation comme irréparable : " + e.getMessage());
        }
    }

    @GetMapping("/getPiecesByReparationId")
    public List<Piece> getPiecesByReparationId(@RequestParam Long id) {
        return reparationPieceUtiliseesService.getPiecesByReparationId(id);
    }

    @GetMapping("/getAllPieceDemandee")
    public List<PieceDemandee> getAll() {
        return pieceDemandeeService.getAll();
    }

    @PostMapping("/addDemandePiece")
    public ResponseEntity<PieceDemandee> saveOrUpdate(@RequestBody PieceDemandee pieceDemandee) {
        PieceDemandee savedPieceDemandee = pieceDemandeeService.saveOrUpdate(pieceDemandee);
        return ResponseEntity.ok(savedPieceDemandee);
    }

    @DeleteMapping("/deleteDemandePiece")
    public ResponseEntity<Void> deleteDemande(@RequestParam Long id) {
        pieceDemandeeService.deleteDemande(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistiques")
    public StatistiqueTechDTO getStatistiques() {
        return statistiqueService.getStatistiquesTechniciens();
    }

    @PostMapping("/creerDemandeConge")
    public DemandeConge creerDemandeConge(@RequestBody DemandeCongeDto demandeConge) {
        return demandeCongeService.createDemandeConge(demandeConge);
    }

    @GetMapping("/getAllConge/{id}")
    public List<DemandeConge> getAllDemandeDeCongeByTravailleurId(@PathVariable Long id) {
        return demandeCongeService.getAllDemandeDeCongeByTravailleurId(id);
    }

    @DeleteMapping("/annulerConge/{id}")
    public ResponseEntity<Void> annulerConge(@PathVariable Long id) {
        demandeCongeService.annulerConge(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateConge")
    public ResponseEntity<String> updateConge(@RequestBody  DemandeConge demandeConge) {
        demandeCongeService.updateConge(demandeConge);
        return ResponseEntity.ok("congé est modifiée avec succès.");
    }

}
