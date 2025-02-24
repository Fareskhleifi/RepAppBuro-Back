package com.projet.RepAppBuro.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Appareil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marque;
    private String modele;
    @Column(unique = true)
    private String numeroSerie;
    private String designation;

    @OneToMany(mappedBy = "appareil", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DemandeReparation> demandeReparations;

}
