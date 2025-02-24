package com.projet.RepAppBuro.Services;

import com.projet.RepAppBuro.Entity.Appareil;
import com.projet.RepAppBuro.Repository.AppareilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppareilService {

    private final AppareilRepository appareilRepository;

    public List<Appareil> getAllAppareils() {
        return appareilRepository.findAll();
    }

    public Appareil getAppareilById(Long id) {
        return appareilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appareil introuvable"));
    }

    public Appareil saveOrUpdateAppareil(Appareil appareil) {
        return appareilRepository.save(appareil);
    }

    public void deleteAppareil(Long id) {
        appareilRepository.deleteById(id);
    }
}
