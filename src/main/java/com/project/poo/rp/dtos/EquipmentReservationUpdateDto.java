package com.project.poo.rp.dtos;

import com.project.poo.rp.enumerations.EquipmentReturnStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EquipmentReservationUpdateDto {
    Long ERId;
    EquipmentReturnStatus equipmentStatus;
}
