package com.projet.RepAppBuro.Services;

import com.projet.RepAppBuro.DTO.FactureDto;
import com.projet.RepAppBuro.Entity.Facture;
import com.projet.RepAppBuro.Entity.Reparation;
import com.projet.RepAppBuro.Repository.FactureRepository;
import com.projet.RepAppBuro.Repository.ReparationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FactureService {

    private final FactureRepository factureRepository;

    private final ReparationService reparationService;

    private final ReparationRepository reparationRepository;

    public List<FactureDto> getAllFactures() {
        List<Facture> factures = factureRepository.findAll();
        return factures.stream().map(facture -> {
            FactureDto factureDto = new FactureDto();
            factureDto.setIdFacture(facture.getId());
            factureDto.setDateFacture(facture.getDateFacture());
            factureDto.setMontantTotal(facture.getMontantTotal());
            factureDto.setArchived(facture.isArchived());
            if (facture.getReparation() != null) {
                factureDto.setIdReparation(facture.getReparation().getId());
                if (facture.getReparation().getDemandeReparation().getClient() != null) {
                    factureDto.setNomClient(facture.getReparation().getDemandeReparation().getClient() .getNom());
                }
            }
            return factureDto;
        }).collect(Collectors.toList());
    }

    public Facture getFactureById(Long id) {
        return factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));
    }

    public Facture saveFacture(FactureDto factureDto) {
        Reparation reparation = reparationRepository.findById(factureDto.getIdReparation())
                .orElseThrow(() -> new RuntimeException("Reparation not found with id: " + factureDto.getIdReparation()));
        Facture facture = new Facture();
        facture.setMontantTotal(factureDto.getMontantTotal());
        facture.setDateFacture(factureDto.getDateFacture());
        facture.setReparation(reparation);
        facture.setArchived(false);
        reparation.setFacture(facture);
        Facture savedFacture = factureRepository.save(facture);
        reparationRepository.save(reparation);
        return savedFacture;
    }



    public void deleteFacture(Long id) {
        factureRepository.deleteById(id);
    }

    public void archiverFacture(Long id) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));
        facture.setArchived(true); // Marquer comme archivée
        factureRepository.save(facture);
    }

    public void desarchiverFacture(Long id) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));
        facture.setArchived(false); // Marquer comme non archivée
        factureRepository.save(facture);
    }

    public List<FactureDto> getFacturesArchivees() {
        List<Facture> factures = factureRepository.findByIsArchived(true);
        return factures.stream().map(facture -> {
            FactureDto factureDto = new FactureDto();
            factureDto.setIdFacture(facture.getId());
            factureDto.setDateFacture(facture.getDateFacture());
            factureDto.setMontantTotal(facture.getMontantTotal());
            factureDto.setArchived(facture.isArchived());
            if (facture.getReparation() != null) {
                factureDto.setIdReparation(facture.getReparation().getId());
                if (facture.getReparation().getDemandeReparation().getClient() != null) {
                    factureDto.setNomClient(facture.getReparation().getDemandeReparation().getClient() .getNom());
                }
            }
            return factureDto;
        }).collect(Collectors.toList());
    }

    public List<FactureDto> getFacturesActives() {
        List<Facture> factures = factureRepository.findByIsArchived(false);
        return factures.stream().map(facture -> {
            FactureDto factureDto = new FactureDto();
            factureDto.setIdFacture(facture.getId());
            factureDto.setDateFacture(facture.getDateFacture());
            factureDto.setMontantTotal(facture.getMontantTotal());
            factureDto.setArchived(facture.isArchived());
            if (facture.getReparation() != null) {
                factureDto.setIdReparation(facture.getReparation().getId());
                if (facture.getReparation().getDemandeReparation().getClient() != null) {
                    factureDto.setNomClient(facture.getReparation().getDemandeReparation().getClient() .getNom());
                }
            }
            return factureDto;
        }).collect(Collectors.toList());
    }
}
