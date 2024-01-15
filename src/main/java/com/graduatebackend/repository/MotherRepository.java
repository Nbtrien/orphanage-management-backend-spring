package com.graduatebackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graduatebackend.entity.Mother;

public interface MotherRepository extends JpaRepository<Mother, Integer> {
	/**
	 * find mother by id
	 *
	 * @param id
	 * @return
	 */
	Optional<Mother> findMotherByMotherIdAndIsDeleteIsFalse(Integer id);

	/**
	 * get all mother available
	 *
	 * @return
	 */
	List<Mother> findByIsDeleteIsFalseAndFamilyIsNull();

}
