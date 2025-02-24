package com.projet.RepAppBuro.Services;

import com.projet.RepAppBuro.DTO.*;
import com.projet.RepAppBuro.Entity.*;
import com.projet.RepAppBuro.Repository.FactureRepository;
import com.projet.RepAppBuro.Repository.ReparationPieceUtiliseesRepository;
import com.projet.RepAppBuro.Repository.ReparationRepository;
import com.projet.RepAppBuro.Repository.TechnicienRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReparationService {

    private final ReparationRepository reparationRepository;
    private final ReparationPieceUtiliseesService reparationPieceUtiliseesService;
    private final FactureRepository factureRepository;
    private final PieceService pieceService;
    private final TechnicienRepository technicienRepository;
    private final ReparationPieceUtiliseesRepository reparationPieceUtiliseesRepository;
    private final EmailService emailService;

    public List<Reparation> getAllReparations() {
        return reparationRepository.findAll();
    }

    public Reparation getReparationById(Long id) {
        return reparationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réparation introuvable"));
    }

    public Reparation getReparationByDemandeReparationId(Long id) {
        return reparationRepository.findByDemandeReparationId(id);
    }

    public List<Reparation> getReparationsNonTerminees() {
        return reparationRepository.findReparationsNonTerminees();
    }

    public List<Reparation> getReparationsTerminees() {
        return reparationRepository.findReparationsTerminees();
    }

    @Transactional
    public Reparation saveOrUpdateReparation(Reparation reparation) {
        return reparationRepository.save(reparation);
    }

    public void deleteReparation(Long id) {
        reparationRepository.deleteById(id);
    }

    public DetailsFactureDto calculerDetailsFacture(Long idDemande) {

        Reparation reparation = getReparationByDemandeReparationId(idDemande);
        List<ReparationPieceUtilisees> reparationPieces = reparation.getReparationPieces();
        List<Piece> picesesUtilisees = reparationPieceUtiliseesService.getPiecesByReparationId(reparation.getId());

        double DureeMainOeuvre = reparation.getDureeMainOeuvre();
        double tarifMainOeuvre = Reparation.getTarifMainOeuvre();
        double TotalReparation = DureeMainOeuvre == 0 ? tarifMainOeuvre : DureeMainOeuvre * tarifMainOeuvre;
        double TotalHT = TotalReparation;
        double TotalTTC = TotalReparation;

        if (reparationPieces != null) {
            for (ReparationPieceUtilisees pieceUtilise : reparationPieces) {
                TotalTTC += (pieceUtilise.getPiece().getPrixVenteTTC()* pieceUtilise.getQte())
                        + (pieceUtilise.getPiece().getTypePiece().getTarifH() * pieceUtilise.getQte());
                TotalHT += (pieceUtilise.getPiece().getPrixVenteHT() * pieceUtilise.getQte())
                        + (pieceUtilise.getPiece().getTypePiece().getTarifH() * pieceUtilise.getQte());
            }
        }
        DetailsFactureDto detailsFactureDto = new DetailsFactureDto();
        detailsFactureDto.setTotalReparation(TotalReparation);
        detailsFactureDto.setTotalHT(TotalHT);
        detailsFactureDto.setTotalTTC(TotalTTC);
        detailsFactureDto.setIdReparation(reparation.getId());
        detailsFactureDto.setPicesesUtilisees(picesesUtilisees);
        detailsFactureDto.setDescription(reparation.getDescription());
        detailsFactureDto.setDureeMainOeuvre(DureeMainOeuvre);
        detailsFactureDto.setTarifMainOeuvre(tarifMainOeuvre);
        detailsFactureDto.setIdOfLastFacture(getNextFactureId());
        return detailsFactureDto;
    }

    public Long getNextFactureId() {
        Optional<Facture> lastFacture = factureRepository.findTopByOrderByIdDesc();
        if (lastFacture.isPresent()) {
            return lastFacture.get().getId() + 1;
        }
        return 1L;
    }

    public DemandeReparation getDemandeReparationByReparationId(Long reparationId) {
        return reparationRepository.findDemandeReparationByReparationId(reparationId);
    }

    public FicheDto getPiecesWithTechnicians() {
        List<Piece> pieces = pieceService.getAllPieces();
        List<Technicien> techniciens = technicienRepository.findAll();
        List<TechnicienDto> technicienDtos = techniciens.stream()
                .map(technicien -> new TechnicienDto(technicien.getId(), technicien.getNom() + " " + technicien.getPrenom()))
                .collect(Collectors.toList());
        return new FicheDto(pieces, technicienDtos);
    }

    public void finaliserReparation(ResReparationDto resReparationDto) {
        Reparation reparation = reparationRepository.findById(resReparationDto.getIdReparation())
                .orElseThrow(() -> new RuntimeException("Reparation not found"));

        Technicien technicien = technicienRepository.findById(resReparationDto.getIdTechnicien())
                .orElseThrow(() -> new RuntimeException("Technicien not found"));

        reparation.setDateFinReparation(resReparationDto.getDateFinReparation());
        reparation.setDureeMainOeuvre(resReparationDto.getDureeMainOeuvre());
        reparation.setTechnicien(technicien);
        reparation.setDescription(resReparationDto.getDescription());

        DemandeReparation demandeReparation = reparation.getDemandeReparation();
        if (demandeReparation != null) {
            demandeReparation.setEtat("Terminé");
        }

        for (PieceUtiliseesDto reparationPieceUtilisee : resReparationDto.getPiecesUtilisees()) {
            Piece piece = pieceService.getPieceById(reparationPieceUtilisee.getPiece().getId());
            piece.setQuantiteStock(piece.getQuantiteStock() - reparationPieceUtilisee.getQte() );
            ReparationPieceUtilisees rep = new ReparationPieceUtilisees();
            rep.setReparation((reparation));
            rep.setPiece(piece);
            rep.setQte(reparationPieceUtilisee.getQte());
            pieceService.saveOrUpdatePiece(piece);
            reparationPieceUtiliseesRepository.save(rep);
        }
        String clientEmail = demandeReparation.getClient().getEmail();
        String clientName = demandeReparation.getClient().getNom();
        String deviceDetails = demandeReparation.getAppareil().getMarque();
        emailService.sendRepairCompletionEmail(clientEmail, clientName, deviceDetails);
        reparationRepository.save(reparation);
    }


    public void irreparableAppareil(Long id) {
        Reparation reparation = reparationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reparation not found"));

        reparation.setDateFinReparation(LocalDate.now());
        reparation.setDureeMainOeuvre(0.0);
        reparation.setDescription("L'appreil est irréparable");
        List<Technicien> listTechs = technicienRepository.findAll();
        reparation.setTechnicien(listTechs.getFirst());
        DemandeReparation demandeReparation = reparation.getDemandeReparation();
        if (demandeReparation != null) {
            demandeReparation.setEtat("Irréparable");
        }
        String clientEmail = demandeReparation.getClient().getEmail();
        String clientName = demandeReparation.getClient().getNom();
        String deviceDetails = demandeReparation.getAppareil().getMarque();
        emailService.sendRepairIrreparableEmail(clientEmail, clientName, deviceDetails);
        reparationRepository.save(reparation);
    }
}
