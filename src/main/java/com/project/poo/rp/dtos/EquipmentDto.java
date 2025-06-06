package com.project.poo.rp.dtos;

import com.project.poo.rp.enumerations.EquipmentStatus;
import lombok.Data;

@Data
public class EquipmentDto {
    Long id;
    String code;
    String type;
    String localisation;
    boolean available;
    EquipmentStatus status;
}
