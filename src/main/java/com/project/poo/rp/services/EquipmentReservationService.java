package com.project.poo.rp.services;

import com.project.poo.rp.entities.Equipment;
import com.project.poo.rp.entities.EquipmentReservation;
import com.project.poo.rp.enumerations.DbOperationStatus;
import com.project.poo.rp.enumerations.EquipmentReturnStatus;
import com.project.poo.rp.enumerations.EquipmentStatus;
import com.project.poo.rp.repositories.EquipmentRepository;
import com.project.poo.rp.repositories.EquipmentReservationRepository;
import com.project.poo.rp.status.DatabaseOperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EquipmentReservationService {
    @Autowired
    EquipmentReservationRepository equipmentReservationRepository;
    @Autowired
    EquipmentRepository equipmentRepository;

    public DatabaseOperationStatus updateReservation(Long idReservation, EquipmentReturnStatus returnStatus) {

        Optional<EquipmentReservation> equipmentReservation = equipmentReservationRepository.findById(idReservation);
        if (equipmentReservation.isEmpty()) {
            return new DatabaseOperationStatus(DbOperationStatus.NOT_FOUND,null,"Equipment reservation not found");
        }
        EquipmentReservation equipmentReservationEntity = equipmentReservation.get();
        equipmentReservationEntity.setEtatRetour(returnStatus);
        equipmentReservationEntity.setDateRetour(LocalDateTime.now());
        Equipment equipment = equipmentReservationEntity.getEquipment();
        switch (equipmentReservationEntity.getEtatRetour()) {
            case CASSE -> equipment.setEtat(EquipmentStatus.EN_REPARATION);
            case NON_RETOURNE -> equipment.setEtat(EquipmentStatus.INCONNU);
            case RETOURNE -> equipment.setEtat(EquipmentStatus.DISPONIBLE);
        }
        equipmentRepository.save(equipment);
        return new DatabaseOperationStatus(DbOperationStatus.SUCCESS,
                equipmentReservationRepository.save(equipmentReservationEntity),
                "Equipment Reservation updated Successfully");

    }
}
