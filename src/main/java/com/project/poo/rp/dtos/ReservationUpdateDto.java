package com.project.poo.rp.dtos;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ReservationUpdateDto {
    List<EquipmentReservationUpdateDto> ERUpdate;
}
