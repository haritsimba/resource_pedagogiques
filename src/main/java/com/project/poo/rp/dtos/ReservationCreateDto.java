package com.project.poo.rp.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReservationCreateDto {
    Long userId;
    Long[] equipmentIds;
    String localisation;
    List<ConsommableReservationCreateDto> consommables = new ArrayList<>();
}
