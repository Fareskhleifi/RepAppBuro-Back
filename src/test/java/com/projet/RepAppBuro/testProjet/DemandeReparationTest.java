//package com.projet.RepAppBuro.testProjet;
//
//import com.projet.RepAppBuro.Entity.Client;
//import com.projet.RepAppBuro.Entity.DemandeReparation;
//import com.projet.RepAppBuro.Entity.Reparation;
//import com.projet.RepAppBuro.Repository.ClientRepository;
//import com.projet.RepAppBuro.Repository.DemandeReparationRepository;
//import com.projet.RepAppBuro.Repository.ReparationRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.time.LocalDate;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//public class DemandeReparationTest {
//
//    @Autowired
//    private DemandeReparationRepository demandeReparationRepository;
//
//    @Autowired
//    private ReparationRepository reparationRepository;
//
//    @Autowired
//    private ClientRepository clientRepository;
//
//    @Test
//    public void testSaveAndRetrieveDemandeReparation() {
//        // Créer un client
//        Client client = new Client();
//        client.setNom("John Doe");
//        client.setAdresse("123 Rue Exemple");
//        client.setEmail("john.doe@example.com");
//        client.setTelephone("123456789");
//        clientRepository.save(client);
//
//        // Créer une demande de réparation
//        DemandeReparation demande = new DemandeReparation();
//        demande.setClient(client);
//        demande.setSymptomesPanne("L'écran ne s'allume pas");
//        demande.setDateDepot(LocalDate.now());
//        demande.setDatePrevueRemise(LocalDate.now().plusDays(7));
//        demande.setEtat("En attente");
//        demande.setArchived(false);
//
//        // Sauvegarder et récupérer
//        DemandeReparation savedDemande = demandeReparationRepository.save(demande);
//        assertThat(savedDemande.getId()).isNotNull();
//        assertThat(savedDemande.getClient().getNom()).isEqualTo("John Doe");
//    }
//
//    @Test
//    public void testRelationDemandeReparationReparation() {
//        DemandeReparation demande = new DemandeReparation();
//        demande.setSymptomesPanne("La batterie ne charge pas");
//        demande.setDateDepot(LocalDate.now());
//        demande.setEtat("En cours");
//
//        Reparation reparation = new Reparation();
//        reparation.setDemandeReparation(demande);
//        reparation.setDescription("Remplacement de la batterie");
//        reparation.setDureeMainOeuvre(2.0);
//        reparation.setDateFinReparation(LocalDate.now().plusDays(2));
//
//        demandeReparationRepository.save(demande);
//        reparationRepository.save(reparation);
//
//        assertThat(reparation.getDemandeReparation().getSymptomesPanne()).isEqualTo("La batterie ne charge pas");
//    }
//
//}