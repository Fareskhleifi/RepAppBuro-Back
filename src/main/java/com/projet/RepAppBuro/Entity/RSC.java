package com.projet.RepAppBuro.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class RSC extends Utilisateur {
    private String fonction;
    private String experience;
    @Enumerated(EnumType.STRING)
    private StatusTravailleur status;

}

