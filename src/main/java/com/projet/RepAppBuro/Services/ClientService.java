package com.projet.RepAppBuro.Services;

import com.projet.RepAppBuro.Entity.Client;
import com.projet.RepAppBuro.Entity.DemandeReparation;
import com.projet.RepAppBuro.Repository.ClientRepository;
import com.projet.RepAppBuro.Repository.DemandeReparationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final DemandeReparationRepository demandeReparationRepository;

    public List<Client> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            Long demandesEnCours = demandeReparationRepository.countDemandesEnCoursByClientId(client.getId());
            client.setDemandesEnCours(demandesEnCours);
        }
        return clients;
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client introuvable"));
    }

    public Client saveOrUpdateClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}