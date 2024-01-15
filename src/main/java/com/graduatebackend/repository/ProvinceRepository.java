package com.graduatebackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graduatebackend.entity.Province;

public interface ProvinceRepository extends JpaRepository<Province, String> {
	/**
	 * find province entity by id
	 *
	 * @param id
	 * @return
	 */
	Optional<Province> findByProvinceIdAndIsDeleteIsFalse(String id);
}
