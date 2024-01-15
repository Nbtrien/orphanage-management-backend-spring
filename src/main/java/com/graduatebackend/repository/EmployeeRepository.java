package com.graduatebackend.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	/**
	 * find all
	 *
	 * @param pageable
	 * @return
	 */
	Page<Employee> findByIsDeleteIsFalseAndEndDateIsNull(Pageable pageable);

	/**
	 * find all
	 * 
	 * @param pageable
	 * @param keyword
	 * @param positionId
	 * @param minDate
	 * @param maxDate
	 * @return
	 */
	@Query(value = "SELECT e FROM Employee e WHERE (:keyword IS NULL OR e.employeeFullName LIKE %:keyword%)"
			+ " AND (:positionId IS NULL OR e.position.employeePositionId = :positionId)"
			+ " AND (:minDate IS NULL OR e.hireDate >= :minDate) AND (:maxDate IS NULL OR e.hireDate <= :maxDate)"
			+ " AND e.endDate IS NULL AND e.isDelete IS FALSE")
	Page<Employee> findAll(Pageable pageable, @Param("keyword") String keyword, @Param("positionId") Integer positionId,
			@Param("minDate") Date minDate, @Param("maxDate") Date maxDate);

	/**
	 * get employee by id
	 *
	 * @param id
	 * @return
	 */
	Optional<Employee> findEmployeeByEmployeeIdAndIsDeleteIsFalse(Integer id);

	/**
	 * search employees By keyword
	 *
	 * @param pageable
	 * @param keyword
	 * @return
	 */
	@Query(value = "SELECT e FROM Employee e " + "WHERE ( (e.employeeFullName IS NOT NULL AND LOWER(e"
			+ ".employeeFullName) LIKE LOWER(CONCAT('%', :keyword, '%'))) OR (e.employeeMailAddress IS NOT NULL AND "
			+ "LOWER(e.employeeMailAddress) LIKE LOWER(CONCAT('%', :keyword, '%')))) AND e.isDelete IS " + "FALSE")
	Page<Employee> searchByKeyword(Pageable pageable, @Param("keyword") String keyword);

	/**
	 * Delete employees by ids
	 *
	 * @param ids
	 */
	@Modifying
	@Transactional
	@Query("UPDATE Employee e SET e.isDelete = TRUE WHERE e.employeeId in(?1)")
	void softDeleteByIds(List<Integer> ids);

	/**
	 * get all employees with hire date is less than next month
	 *
	 * @return
	 */
	@Query("SELECT e FROM Employee e WHERE YEAR(e.hireDate) <= YEAR(CURRENT_DATE) AND MONTH(e.hireDate) <= MONTH"
			+ "(CURRENT_DATE) AND e.isDelete IS FALSE")
	List<Employee> findByHireDateBeforeNextMonth();

	/**
	 * get all employees with hire date is less than month in year
	 *
	 * @return
	 */
	@Query("SELECT e FROM Employee e WHERE YEAR(e.hireDate) <= :year AND MONTH(e.hireDate) <= :month AND e.isDelete "
			+ "IS FALSE")
	List<Employee> findByHireDateBeforeMonth(@Param("year") int year, @Param("month") int month);

	/**
	 * count employee
	 *
	 * @return
	 */
	@Query("SELECT COUNT(e.employeeId) from Employee e WHERE e.endDate IS NULL AND e.isDelete IS FALSE")
	long count();
}
