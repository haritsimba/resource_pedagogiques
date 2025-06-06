package com.project.poo.rp.dtos;

import com.project.poo.rp.enumerations.EquipmentReturnStatus;
import com.project.poo.rp.enumerations.EquipmentStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EquipmentReservationDto {
    Long id;
    String equipmentCode;
    Long equipmentId;
    EquipmentStatus equipmentStatus;
    EquipmentReturnStatus returnStatus;
    String equipmentType;
}
