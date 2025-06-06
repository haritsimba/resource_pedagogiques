package com.project.poo.rp.dtos;

import lombok.Data;

@Data
public class ResourceTypeDto {
    String title;
    String codePrefix;
    Long id;
    boolean consommable;
}
