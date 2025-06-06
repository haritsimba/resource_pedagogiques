package com.project.poo.rp.controllers;

import com.project.poo.rp.dtos.ReservationCreateDto;
import com.project.poo.rp.dtos.ReservationUpdateDto;
import com.project.poo.rp.enumerations.DbOperationStatus;
import com.project.poo.rp.services.ReservationService;
import com.project.poo.rp.status.DatabaseOperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path = "/api/reservation")
@CrossOrigin(origins = "http://localhost:5173")
public class ReservationController {
    @Autowired
    ReservationService reservationService;
    @PostMapping(path = "")
    public ResponseEntity<?> create(@RequestBody ReservationCreateDto dto){
        DatabaseOperationStatus dboStatus = reservationService.createReservation(dto);
        if (Objects.requireNonNull(dboStatus.getStatus()) == DbOperationStatus.SUCCESS) {
            return ResponseEntity.ok(dboStatus.getData());
        }
        return ResponseEntity.status(400).body(dboStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable(value = "id") Long id, @RequestBody ReservationUpdateDto reservationUpdateDto){
        DatabaseOperationStatus dboStatus = reservationService.updateReservation(id, reservationUpdateDto);
        if (Objects.requireNonNull(dboStatus.getStatus()) == DbOperationStatus.SUCCESS) {
            return ResponseEntity.ok(dboStatus.getData());
        }
        return ResponseEntity.status(400).body(dboStatus);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(reservationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id")Long id){
        return ResponseEntity.ok(reservationService.findById(id));
    }
}
