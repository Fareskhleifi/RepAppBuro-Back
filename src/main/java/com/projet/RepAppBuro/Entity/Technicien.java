package com.projet.RepAppBuro.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Technicien extends Utilisateur {
    private String specialisation;
    private String experience;
    @Enumerated(EnumType.STRING)
    private StatusTravailleur status;
    @OneToMany(mappedBy = "technicien", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Reparation> reparations;

}
