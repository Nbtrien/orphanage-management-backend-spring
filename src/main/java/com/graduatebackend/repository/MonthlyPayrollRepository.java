package com.graduatebackend.repository;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.graduatebackend.entity.MonthlyPayroll;

public interface MonthlyPayrollRepository extends JpaRepository<MonthlyPayroll, Integer> {

	/**
	 * find all monthly payrolls
	 *
	 * @param pageable
	 * @return
	 */
	Page<MonthlyPayroll> findByIsDeleteIsFalse(Pageable pageable);

	/**
	 * find all
	 * 
	 * @param pageable
	 * @param status
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	@Query("SELECT p FROM MonthlyPayroll p WHERE (:status IS NULL OR p.payrollStatus = :status) AND (:fromDate IS NULL OR p.startDate >= :fromDate) AND (:toDate IS NULL OR p.startDate <= :toDate) AND p.isDelete IS FALSE")
	Page<MonthlyPayroll> findAll(Pageable pageable, @Param("status") Integer status, @Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate);

	/**
	 * find monthly payroll by id
	 *
	 * @param id
	 * @return
	 */
	Optional<MonthlyPayroll> findByMonthlyPayrollIdAndIsDeleteIsFalse(Integer id);

	/**
	 * find by month
	 *
	 * @param month
	 * @return
	 */
	Optional<MonthlyPayroll> findByMonthAndYearAndIsDeleteIsFalse(int month, int year);
}
