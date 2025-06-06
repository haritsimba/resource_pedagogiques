package com.project.poo.rp.controllers;

import com.project.poo.rp.dtos.ResourceTypeDto;
import com.project.poo.rp.entities.ResourceType;
import com.project.poo.rp.services.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(path = "/api/resources/type")
public class ResourceTypeController {
    @Autowired
    ResourceTypeService resourceTypeService;
    @PostMapping("")
    public ResponseEntity<?> createResourceType(@RequestBody ResourceTypeDto resourceTypeDto){
        ResourceType resourceType = resourceTypeService.createResourceType(resourceTypeDto);
        return ResponseEntity.ok(resourceType);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(resourceTypeService.findAll());
    }
}
