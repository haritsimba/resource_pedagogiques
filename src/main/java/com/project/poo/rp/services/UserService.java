package com.project.poo.rp.services;

import com.project.poo.rp.dtos.UserCreateDto;
import com.project.poo.rp.dtos.UserFindDto;
import com.project.poo.rp.dtos.UserLoginDto;
import com.project.poo.rp.entities.User;
import com.project.poo.rp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    public User createUser(UserCreateDto dto){
        User user =  new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        return userRepository.save(user);
    }

    public User login(UserLoginDto dto){
        return userRepository.findByEmailAndPassword(dto.getEmail(),dto.getPassword());
    }

    public List<UserFindDto> findAll(){
        List<User> userList = userRepository.findAll();
        ArrayList<UserFindDto> dtos = new ArrayList<>();
        for (User u:userList){
            UserFindDto dto = new UserFindDto();
            dto.setId(u.getId());
            dto.setUsername(u.getUsername());
            dtos.add(dto);
        }
        return dtos;

    }
}
