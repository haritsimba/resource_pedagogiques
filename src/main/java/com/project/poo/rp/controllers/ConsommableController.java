package com.project.poo.rp.controllers;

import com.project.poo.rp.dtos.ConsommableUpdateStockDto;
import com.project.poo.rp.services.ConsommableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/consommables")
@CrossOrigin(origins = "http://localhost:5173")
public class ConsommableController {
    @Autowired
    ConsommableService consommableService;
    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(consommableService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStock(@PathVariable(value = "id")Long id, @RequestBody ConsommableUpdateStockDto dto){
        return ResponseEntity.ok(consommableService.updateStock(id, dto.getStock()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(consommableService.findById(id));
    }

}
