package com.project.poo.rp.controllers;

import com.project.poo.rp.dtos.EquipmentAddDto;
import com.project.poo.rp.dtos.EquipmentUpdateDto;
import com.project.poo.rp.enumerations.DbOperationStatus;
import com.project.poo.rp.services.EquipmentService;
import com.project.poo.rp.status.DatabaseOperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/equipments")
@CrossOrigin(origins = "http://localhost:5173")
public class EquipmentController {
    @Autowired
    EquipmentService equipmentService;
    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(equipmentService.findAll());
    }

    @PostMapping("")
    public ResponseEntity<?> createEquipment(@RequestBody EquipmentAddDto dto){
        DatabaseOperationStatus dboStatus = equipmentService.addEquipment(dto);
        if(dboStatus.getStatus() == DbOperationStatus.NOT_FOUND){
            return ResponseEntity.status(400).body(dboStatus);
        }
        return ResponseEntity.ok(dboStatus.getData());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEquipmentStatus(@PathVariable(value = "id")Long id,@RequestBody EquipmentUpdateDto dto){
        return ResponseEntity.ok(equipmentService.updateStatus(id,dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id")Long id){
        return ResponseEntity.ok(equipmentService.findById(id));
    }
}
