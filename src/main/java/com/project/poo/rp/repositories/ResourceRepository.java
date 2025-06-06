package com.project.poo.rp.repositories;

import com.project.poo.rp.entities.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource,Long> {
}
