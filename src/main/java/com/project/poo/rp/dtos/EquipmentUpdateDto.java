package com.project.poo.rp.dtos;

import com.project.poo.rp.enumerations.EquipmentStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EquipmentUpdateDto {
    EquipmentStatus status;
    String localisation;
}
