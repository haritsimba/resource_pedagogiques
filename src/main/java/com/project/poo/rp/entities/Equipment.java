package com.project.poo.rp.entities;

import com.project.poo.rp.enumerations.EquipmentStatus;
import jakarta.persistence.*;
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
public class Equipment extends Resource {
    private String code;
    private String localisation = "Bureau";
    @Enumerated(value = EnumType.STRING)
    private EquipmentStatus etat = EquipmentStatus.DISPONIBLE;

    @OneToMany(mappedBy = "equipment")
    private List<EquipmentReservation> reservations = new ArrayList<>();
}
