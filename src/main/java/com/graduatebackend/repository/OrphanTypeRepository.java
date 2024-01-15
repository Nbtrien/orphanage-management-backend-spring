package com.graduatebackend.repository;

import com.graduatebackend.entity.OrphanType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrphanTypeRepository extends JpaRepository<OrphanType, Integer> {
}
