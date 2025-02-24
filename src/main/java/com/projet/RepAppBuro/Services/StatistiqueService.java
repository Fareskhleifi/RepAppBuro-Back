package com.projet.RepAppBuro.Services;

import com.projet.RepAppBuro.DTO.StatistiqueDirectionDTO;
import com.projet.RepAppBuro.DTO.StatistiqueTechDTO;
import com.projet.RepAppBuro.DTO.StatistiquesRSCDTO;
import com.projet.RepAppBuro.Entity.StatusTravailleur;
import com.projet.RepAppBuro.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatistiqueService {

    private final TechnicienRepository technicienRepository;
    private final ClientRepository clientRepository;
    private final ReparationRepository reparationRepository;
    private final FactureRepository factureRepository;
    private final DemandeReparationRepository demandeReparationRepository;

    public StatistiqueDirectionDTO getStatistiques() {
        int techniciensActifs = technicienRepository.countByStatus(StatusTravailleur.ACTIF);
        int nombreClients = (int) clientRepository.count();
        int reparationsEnCours = reparationRepository.countByDateFinReparationIsNull();
        int reparationsTerminees = reparationRepository.countByDateFinReparationIsNotNull();

        double beneficesCeMois = factureRepository.calculateTotalBeneficesThisMonth();
        double depensesCeMois = reparationRepository.calculateTotalDepensesThisMonth();
        double facturesAEncaisser = factureRepository.calculateFacturesAEncaisser();

        double margeBeneficiaireNette = (beneficesCeMois - depensesCeMois) / beneficesCeMois * 100;

        return new StatistiqueDirectionDTO(
                techniciensActifs,
                nombreClients,
                reparationsEnCours,
                reparationsTerminees,
                beneficesCeMois,
                depensesCeMois,
                facturesAEncaisser,
                margeBeneficiaireNette
        );
    }

    public StatistiqueTechDTO getStatistiquesTechniciens() {
        int reparationsEnCours = demandeReparationRepository.countReparationsEnCours();
        int reparationsTerminees = reparationRepository.countReparationsTermineesCeMois();
        int reparationsEnAttente = demandeReparationRepository.countAppareilsEnAttenteReparation();
        return new StatistiqueTechDTO(reparationsEnAttente, reparationsEnCours,reparationsTerminees);
    }

    public StatistiquesRSCDTO getStatistiquesRSC() {
        StatistiquesRSCDTO statistiquesDTO = new StatistiquesRSCDTO();

        statistiquesDTO.setTotalDemandesRecuesCeMois(demandeReparationRepository.countDemandesReçuesCeMois());
        statistiquesDTO.setTotalDemandesEnCours(demandeReparationRepository.countDemandesEnCours());
        statistiquesDTO.setTotalDemandesCloturees(demandeReparationRepository.countDemandesCloturees());
        statistiquesDTO.setTotalClients(clientRepository.countClientsTotaux());
        statistiquesDTO.setNouveauxClientsCeMois(clientRepository.countNouveauxClientsCeMois());
        statistiquesDTO.setMontantTotalFactureCeMois(factureRepository.montantTotalFactureCeMois());

        return statistiquesDTO;
    }
}
