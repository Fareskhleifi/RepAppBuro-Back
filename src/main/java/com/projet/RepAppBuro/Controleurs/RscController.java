package com.projet.RepAppBuro.Controleurs;

import com.projet.RepAppBuro.DTO.*;
import com.projet.RepAppBuro.Entity.*;
import com.projet.RepAppBuro.Services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rsc")
@RequiredArgsConstructor
public class RscController {

    private final ClientService clientService;
    private final AppareilService appareilService;
    private final ReparationService reparationService;
    private final DemandeReparationService demandeReparationService;
    private final ReparationPieceUtiliseesService reparationPieceUtiliseesService;
    private final FactureService factureService;
    private final StatistiqueService statistiqueService;
    private final DemandeCongeService demandeCongeService;

    @GetMapping("/getAllClients")
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }


    @PostMapping("/addClient")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.saveOrUpdateClient( client));
    }

    @PutMapping("/UpdateClient")
    public ResponseEntity<Client> UpdateClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.updateClient( client));
    }

    @PostMapping(value = "/addAppareil")
    public ResponseEntity<Appareil> addAppareil(@RequestBody Appareil appareil) {
        Appareil savedAppareil = appareilService.saveOrUpdateAppareil(appareil);
        return ResponseEntity.ok(savedAppareil);
    }

    @GetMapping(value = "/getAllAppareils")
    public ResponseEntity<List<Appareil>>  getAllAppareils() {
        return ResponseEntity.ok(appareilService.getAllAppareils());
    }

    @GetMapping(value = "/getReparationByDemandeReparationId")
    public ResponseEntity<Reparation>  getReparationByDemandeReparationId(@RequestParam  Long id) {
        return ResponseEntity.ok(reparationService.getReparationByDemandeReparationId(id));
    }

    @GetMapping("/getPiecesByReparationId")
    public List<Piece> getPiecesByReparationId(@RequestParam Long id) {
        return reparationPieceUtiliseesService.getPiecesByReparationId(id);
    }

    @PostMapping("/addDemandeReparation")
    public ResponseEntity<String> addDemandeReparation(@RequestBody DemandeReparation demandeReparation) {
        DemandeReparation savedDemande = demandeReparationService.saveDemande(demandeReparation);
        return ResponseEntity.ok("Demande de réparation ajoutée avec succès.");
    }

    @GetMapping("/getAllDemandes")
    public ResponseEntity<List<DemandeReparation>> getAllDemandes() {
        return ResponseEntity.ok(demandeReparationService.getAllDemandes());
    }

    @PutMapping("/updateDemandeReparation")
    public ResponseEntity<String> updateDemandeReparation(@RequestBody DemandeReparation demandeReparation) {
        DemandeReparation savedDemande = demandeReparationService.UpdateDemande(demandeReparation);
        return ResponseEntity.ok("Demande de réparation modifiée avec succès.");
    }

    @PutMapping("/archiverDemande")
    public ResponseEntity<String> archiverDemande(@RequestParam  Long id) {
        demandeReparationService.archiverDemande(id);
        return ResponseEntity.ok("Demande de réparation est archivée avec succès.");
    }

    @PutMapping("/desarchiverDemande")
    public ResponseEntity<String> desarchiverDemande(@RequestParam  Long id) {
        demandeReparationService.desarchiverDemande(id);
        return ResponseEntity.ok("Demande de réparation est desarchivée avec succès.");
    }

    @PutMapping("/deleteDemande")
    public ResponseEntity<String> deleteDemande(@RequestParam  Long id) {
        demandeReparationService.deleteDemande(id);
        return ResponseEntity.ok("Demande de réparation est supprimée avec succès.");
    }

    @PostMapping("/addFacture")
    public ResponseEntity<String> addFacture(@RequestBody FactureDto factureDto) {
        Facture savedFacture = factureService.saveFacture(factureDto);
        return ResponseEntity.ok("facture ajoutée avec succès.");
    }

    @GetMapping(value = "/getAllFactures")
    public ResponseEntity<List<FactureDto>>  getAllFactures() {
        return ResponseEntity.ok(factureService.getAllFactures());
    }

    @PutMapping("/archiverFacture")
    public ResponseEntity<String> archiverFacture(@RequestParam  Long id) {
        factureService.archiverFacture(id);
        return ResponseEntity.ok("facture de réparation est archivée avec succès.");
    }

    @PutMapping("/desarchiverFacture")
    public ResponseEntity<String> desarchiverFacture(@RequestParam  Long id) {
        factureService.desarchiverFacture(id);
        return ResponseEntity.ok("facture de réparation est desarchivée avec succès.");
    }

    @GetMapping(value = "/getFacturesArchivees")
    public ResponseEntity<List<FactureDto>>  getFacturesArchivees() {
        return ResponseEntity.ok(factureService.getFacturesArchivees());
    }

    @GetMapping(value = "/getFacturesActives")
    public ResponseEntity<List<FactureDto>>  getFacturesActives() {
        return ResponseEntity.ok(factureService.getFacturesActives());
    }

    @GetMapping(value = "/getDemandesArchivees")
    public ResponseEntity<List<DemandeReparation>>  getDemandesArchivees() {
        return ResponseEntity.ok(demandeReparationService.getDemandesArchivees());
    }

    @GetMapping(value = "/getDemandesActives")
    public ResponseEntity<List<DemandeReparation>>  getDemandesActives() {
        return ResponseEntity.ok(demandeReparationService.getDemandesActives());
    }

    @GetMapping("/demande/isPayee/{id}")
    public ResponseEntity<Boolean> isDemandePayee(@PathVariable Long id) {
        boolean isPayee = demandeReparationService.isDemandePayee(id);
        return ResponseEntity.ok(isPayee);
    }

    @GetMapping("/DetailsFacture")
    public ResponseEntity<DetailsFactureDto> getDetailsFacture(@RequestParam Long id) {
        DetailsFactureDto detailsFacture = reparationService.calculerDetailsFacture(id);
        return ResponseEntity.ok(detailsFacture);
    }

    @GetMapping("/{reparationId}/demande")
    public DemandeReparation getDemandeReparationByReparationId(@PathVariable Long reparationId) {
        return reparationService.getDemandeReparationByReparationId(reparationId);
    }

    @GetMapping("/statistiques")
    public StatistiquesRSCDTO getStatistiques() {
        return statistiqueService.getStatistiquesRSC();
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
