package com.project.poo.rp.services;

import com.project.poo.rp.dtos.EquipmentAddDto;
import com.project.poo.rp.dtos.EquipmentDto;
import com.project.poo.rp.dtos.EquipmentUpdateDto;
import com.project.poo.rp.entities.Equipment;
import com.project.poo.rp.entities.EquipmentReservation;
import com.project.poo.rp.entities.ResourceType;
import com.project.poo.rp.enumerations.DbOperationStatus;
import com.project.poo.rp.enumerations.EquipmentReturnStatus;
import com.project.poo.rp.enumerations.EquipmentStatus;
import com.project.poo.rp.repositories.EquipmentRepository;
import com.project.poo.rp.repositories.EquipmentReservationRepository;
import com.project.poo.rp.repositories.ResourceTypeRepository;
import com.project.poo.rp.status.DatabaseOperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {
    @Autowired
    EquipmentRepository equipmentRepository;
    @Autowired
    ResourceTypeRepository resourceTypeRepository;
    @Autowired
    EquipmentReservationRepository equipmentReservationRepository;

    public DatabaseOperationStatus addEquipment(EquipmentAddDto dto) {
        Optional<ResourceType> resourceType = resourceTypeRepository.findById(dto.getTypeId());
        if (resourceType.isEmpty()) {
            return new DatabaseOperationStatus(DbOperationStatus.NOT_FOUND, null, "Resource type does not exist");
        }
        if(resourceType.get().isConsommable()){
            return new DatabaseOperationStatus(DbOperationStatus.ERROR,null,"Resource type should not be consommable");
        }
        Equipment equipment = new Equipment();
        equipment.setType(resourceType.get());
        equipment.setCode(dto.getCode());
        if (dto.getLocalisation() != null && !dto.getLocalisation().trim().isBlank()) {
            equipment.setLocalisation(dto.getLocalisation());
        }

        return new DatabaseOperationStatus(DbOperationStatus.SUCCESS, equipmentRepository.save(equipment), "Equipment added successfully");
    }

    public List<EquipmentDto> findAll() {
        List<Equipment> equipmentList = equipmentRepository.findAll();
        List<EquipmentDto> dtos = new ArrayList<>();
        for (Equipment equipment : equipmentList) {
            dtos.add(mapToDto(equipment));
        }

        return dtos;
    }

    public EquipmentReservation getLastReservation(Equipment equipment) {
        return equipment.getReservations().stream()
                .max(Comparator.comparing(EquipmentReservation::getCreationTimeStamp))
                .orElse(null);
    }

    public EquipmentDto mapToDto(Equipment equipment){
        EquipmentDto dto = new EquipmentDto();
        dto.setCode(equipment.getCode());
        dto.setType(equipment.getType().getNom());
        dto.setId(equipment.getId());
        dto.setStatus(equipment.getEtat());
        dto.setAvailable((!hasReservation(equipment) || getLastReservation(equipment).getEtatRetour() == EquipmentReturnStatus.RETOURNE) && equipment.getEtat()== EquipmentStatus.DISPONIBLE);
        dto.setLocalisation(equipment.getLocalisation());
        return dto;
    }


    public boolean hasReservation(Equipment equipment) {
        return !equipment.getReservations().isEmpty();
    }

    public EquipmentDto updateStatus(Long id, EquipmentUpdateDto dto) {
        Optional<Equipment> equipment = equipmentRepository.findById(id);
        if(equipment.isEmpty()){
            return null;
        }
        Equipment equipmentEntity = equipment.get();
        equipmentEntity.setEtat(dto.getStatus());
        if(dto.getLocalisation()!=null && !dto.getLocalisation().isBlank()){
            equipmentEntity.setLocalisation(dto.getLocalisation());
        }
        if(equipmentEntity.getEtat()==EquipmentStatus.DISPONIBLE){
            for (EquipmentReservation er:equipment.get().getReservations()){
                er.setEtatRetour(EquipmentReturnStatus.RETOURNE);
                equipmentReservationRepository.save(er);
            }
        }

        return mapToDto(equipmentRepository.save(equipmentEntity));
    }

    public EquipmentDto findById(Long id){
        Optional<Equipment> equipment = equipmentRepository.findById(id);
        return equipment.map(this::mapToDto).orElse(null);
    }
}
