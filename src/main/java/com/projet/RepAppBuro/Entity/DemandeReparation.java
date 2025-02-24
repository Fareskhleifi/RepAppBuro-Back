package com.projet.RepAppBuro.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class DemandeReparation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Appareil appareil;

    private String symptomesPanne;
    private LocalDate dateDepot;
    private LocalDate datePrevueRemise;
    private String etat;
    private boolean isArchived = false;

    @OneToOne( mappedBy = "demandeReparation", cascade = CascadeType.ALL)
    @JsonIgnore
    private Reparation reparation;

    @ManyToOne
    private RSC creePar;

    @ManyToOne
    private RSC modifiePar;
}
