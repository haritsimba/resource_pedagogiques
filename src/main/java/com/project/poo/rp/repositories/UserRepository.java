package com.project.poo.rp.repositories;

import com.project.poo.rp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username,String password);
    User findByEmailAndPassword(String email,String password);
}
