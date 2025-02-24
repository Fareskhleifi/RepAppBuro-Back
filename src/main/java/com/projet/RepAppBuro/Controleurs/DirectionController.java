package com.projet.RepAppBuro.Controleurs;

import com.projet.RepAppBuro.DTO.ReqRes;
import com.projet.RepAppBuro.DTO.StatistiqueDirectionDTO;
import com.projet.RepAppBuro.Entity.DemandeConge;
import com.projet.RepAppBuro.Entity.PieceDemandee;
import com.projet.RepAppBuro.Entity.RSC;
import com.projet.RepAppBuro.Entity.Technicien;
import com.projet.RepAppBuro.Repository.RSCRepository;
import com.projet.RepAppBuro.Repository.TechnicienRepository;
import com.projet.RepAppBuro.Services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/direction")
@RequiredArgsConstructor
public class DirectionController {

    private final ClientService clientService;
    private final PieceDemandeeService pieceDemandeeService;
    private final AuthService authService;
    private final RSCRepository rscRepository;
    private final TechnicienRepository technicienRepository;
    private final StatistiqueService statistiqueService;
    private final DemandeCongeService demandeCongeService;

    @DeleteMapping("/deleteClient")
    public ResponseEntity<String> deleteClient(@RequestParam Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok("client est supprimée avec succès.");
    }

    @GetMapping("/getAllPieceDemandee")
    public List<PieceDemandee> getAll() {
        return pieceDemandeeService.getAll();
    }

    @PutMapping("/accepterDemandePiece/{id}")
    public ResponseEntity<PieceDemandee> accepterDemande(@PathVariable Long id) {
        PieceDemandee demande = pieceDemandeeService.accepterDemande(id);
        return ResponseEntity.ok(demande);
    }

    @PutMapping("/refuserDemandePiece/{id}")
    public ResponseEntity<PieceDemandee> refuserDemande(@PathVariable Long id) {
        PieceDemandee demande = pieceDemandeeService.refuserDemande(id);
        return ResponseEntity.ok(demande);
    }

    @PostMapping("/signupTechnicien")
    public ResponseEntity<ReqRes> signUpTechnicien(@RequestBody ReqRes signUpRequest){
        return ResponseEntity.ok(authService.signUpTechnicien(signUpRequest));
    }

    @PutMapping("/updateTechnicien")
    public ResponseEntity<Technicien> updateTechnicien(@RequestBody Technicien tech) {
        Technicien technicien = technicienRepository.findById(tech.getId())
                .orElseThrow(() -> new RuntimeException("technicien introuvable"));
        technicien.setNom(tech.getNom());
        technicien.setPrenom(tech.getPrenom());
        technicien.setEmail(tech.getEmail());
        technicien.setExperience(tech.getExperience());
        technicien.setTelephone(tech.getTelephone());
        technicien.setSpecialisation(tech.getSpecialisation());
        return ResponseEntity.ok(technicienRepository.save(technicien));
    }

    @PostMapping("/signupRsc")
    public ResponseEntity<ReqRes> signUpRsc(@RequestBody ReqRes signUpRequest) {
        return ResponseEntity.ok(authService.signUpRsc(signUpRequest));
    }

    @PutMapping("/updateRsc")
    public ResponseEntity<RSC> updateRsc(@RequestBody RSC rsc) {
        RSC oldRsc = rscRepository.findById(rsc.getId())
                .orElseThrow(() -> new RuntimeException("responsable introuvable"));
        oldRsc.setNom(rsc.getNom());
        oldRsc.setPrenom(rsc.getPrenom());
        oldRsc.setEmail(rsc.getEmail());
        oldRsc.setExperience(rsc.getExperience());
        oldRsc.setTelephone(rsc.getTelephone());
        oldRsc.setFonction(rsc.getFonction());
        return ResponseEntity.ok(rscRepository.save(oldRsc));
    }

    @DeleteMapping("/DeleteRsc")
    public ResponseEntity<Void> deleteRsc(@RequestParam Long id) {
        rscRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/DeleteTechnicien")
    public ResponseEntity<Void> deleteTechnicien(@RequestParam Long id) {
        technicienRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllRscs")
    public ResponseEntity<List<RSC>> getAllRscs() {
        return ResponseEntity.ok(rscRepository.findAll());
    }

    @GetMapping("/getAllTechnicien")
    public ResponseEntity<List<Technicien>> getAllTechnicien() {
        return ResponseEntity.ok(technicienRepository.findAll());
    }

    @GetMapping("/statistiques")
    public StatistiqueDirectionDTO getStatistiques() {
        return statistiqueService.getStatistiques();
    }

    @GetMapping("/getAllDemandeDeConge")
    public List<DemandeConge> getAllDemandeDeConge() {
        return demandeCongeService.getAllDemandeDeConge();
    }
    @PutMapping("/accepterConge/{id}")
    public DemandeConge accepterConge(@PathVariable Long id) {
        return demandeCongeService.accepterConge(id);
    }

    @PutMapping("/refuserConge/{id}")
    public DemandeConge refuserConge(@PathVariable Long id) {
        return demandeCongeService.refuserConge(id);
    }

}
