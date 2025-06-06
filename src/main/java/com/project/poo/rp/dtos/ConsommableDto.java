package com.project.poo.rp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ConsommableDto {
    Long id;
    String type;
    Integer stock;
}
