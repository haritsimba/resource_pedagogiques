package com.project.poo.rp.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ConsommableReservationDto {
    Long id;
    Long idConsommable;
    String typeConsommable;
    Integer quantity;
}
