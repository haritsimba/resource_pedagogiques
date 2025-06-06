package com.project.poo.rp.services;

import com.project.poo.rp.dtos.ConsommableDto;
import com.project.poo.rp.entities.Consommable;
import com.project.poo.rp.repositories.ConsommableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConsommableService {
    @Autowired
    ConsommableRepository consommableRepository;

    public Consommable updateStock(Long consommableId,int stock){
        Optional<Consommable> consommable = consommableRepository.findById(consommableId);
        if (consommable.isEmpty()){
            return null;
        }
        Consommable consommableEntity = consommable.get();
        consommableEntity.setStockActuel(stock);
        return consommableRepository.save(consommableEntity);
    }

    public List<ConsommableDto> findAll(){
        List<Consommable> consommableList = consommableRepository.findAll();
        ArrayList<ConsommableDto> dtos = new ArrayList<>();
        for(Consommable consommable:consommableList){
            ConsommableDto dto = new ConsommableDto();
            dto.setStock(consommable.getStockActuel());
            dto.setType(consommable.getType().getNom());
            dto.setId(consommable.getId());
            dtos.add(dto);
        }
        return dtos;
    }

    public Consommable findById(Long id) {
        Optional<Consommable> consommable = consommableRepository.findById(id);
        return consommable.orElse(null);
    }
}
