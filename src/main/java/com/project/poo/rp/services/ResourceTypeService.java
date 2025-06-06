package com.project.poo.rp.services;

import com.project.poo.rp.dtos.ResourceTypeDto;
import com.project.poo.rp.entities.Consommable;
import com.project.poo.rp.entities.Resource;
import com.project.poo.rp.entities.ResourceType;
import com.project.poo.rp.repositories.ConsommableRepository;
import com.project.poo.rp.repositories.ResourceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceTypeService {
    @Autowired
    ResourceTypeRepository resourceTypeRepository;
    @Autowired
    ConsommableRepository consommableRepository;

    public ResourceType createResourceType(ResourceTypeDto dto){
        ResourceType resourceType = new ResourceType();
        resourceType.setNom(dto.getTitle());
        resourceType.setConsommable(dto.isConsommable());
        resourceType.setCodePrefix(dto.getCodePrefix());
        ResourceType rt = resourceTypeRepository.save(resourceType);
        if(resourceType.isConsommable()){
            Consommable consommable = new Consommable();
            consommable.setStockActuel(0);
            consommable.setType(resourceType);
            consommableRepository.save(consommable);
        }
        return rt;
    }

    public String generateCodePrefix(String name){
        String code = name.substring(0,1).toUpperCase();
        return  code;
    }

    public ArrayList<ResourceTypeDto> findAll(){
        List<ResourceType> resourceTypeList = resourceTypeRepository.findAll();
        ArrayList<ResourceTypeDto> dtos = new ArrayList<>();
        for(ResourceType resourceType:resourceTypeList){
            ResourceTypeDto dto = new ResourceTypeDto();
            dto.setTitle(resourceType.getNom());
            dto.setCodePrefix(resourceType.getCodePrefix());
            dto.setConsommable(dto.isConsommable());
            dto.setId(resourceType.getId());
            dtos.add(dto);
        }
        return dtos;
    }

}
