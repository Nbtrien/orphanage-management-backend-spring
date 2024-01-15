package com.graduatebackend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.graduatebackend.entity.EmployeePosition;

public interface EmployeePositionRepository extends JpaRepository<EmployeePosition, Integer> {
	/**
	 * get all employee position
	 *
	 * @return
	 */
	List<EmployeePosition> findByIsDeleteIsFalse();

	/**
	 * get employee position pages
	 *
	 * @param pageable
	 * @return
	 */
	Page<EmployeePosition> findByIsDeleteIsFalse(Pageable pageable);

	/**
	 * find all
	 * 
	 * @param pageable
	 * @param keyword
	 * @return
	 */
	@Query("SELECT p FROM EmployeePosition p WHERE (:keyword IS NULL OR p.employeePositionTitle LIKE %:keyword%) AND p.isDelete IS FALSE")
	Page<EmployeePosition> findAll(Pageable pageable, @Param("keyword") String keyword);
}
