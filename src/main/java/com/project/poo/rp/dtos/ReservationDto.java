package com.project.poo.rp.dtos;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class ReservationDto {
    Long id;
    String username;
    Long userId;
    List<EquipmentReservationDto> ERDto;
    List<ConsommableReservationDto> CRDto;
    LocalDateTime startDate;
}
