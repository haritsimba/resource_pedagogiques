package com.project.poo.rp.entities;

import com.project.poo.rp.enumerations.EquipmentReturnStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateRetour;
    private EquipmentReturnStatus etatRetour;
    @CreationTimestamp
    private Timestamp creationTimeStamp;

    @ManyToOne
    private Equipment equipment;

    @ManyToOne
    private Reservation reservation;
}
