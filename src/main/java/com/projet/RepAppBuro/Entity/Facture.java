package com.projet.RepAppBuro.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reparation_id")
    @JsonIgnore
    private Reparation reparation;

    private Double montantTotal;
    private LocalDate dateFacture;

    private boolean isArchived = false;
}

