package com.project.poo.rp.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserLoginDto {
    String email;
    String password;
}
