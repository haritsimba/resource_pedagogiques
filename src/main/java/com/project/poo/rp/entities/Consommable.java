package com.project.poo.rp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Consommable extends Resource {
    private int stockActuel;

    @OneToMany(mappedBy = "consommable")
    private List<ConsommableReservation> reservations = new ArrayList<>();
}