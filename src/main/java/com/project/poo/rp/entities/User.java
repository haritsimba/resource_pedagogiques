package com.project.poo.rp.entities;

import com.project.poo.rp.interfaces.UserInterface;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements UserInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    Long id;
    String username;
    String password;
    @Email
    String email;
    @Override
    public String getUserRoles() {
        return "STUDENT";
    }
}
