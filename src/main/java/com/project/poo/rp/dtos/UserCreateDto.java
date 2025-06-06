package com.project.poo.rp.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserCreateDto {
    String username;
    String password;
    String email;
}
