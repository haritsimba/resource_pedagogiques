package com.project.poo.rp.controllers;

import com.project.poo.rp.dtos.UserCreateDto;
import com.project.poo.rp.dtos.UserLoginDto;
import com.project.poo.rp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDto dto){
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto dto){
        return ResponseEntity.ok(userService.login(dto));
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(userService.findAll());
    }
}
