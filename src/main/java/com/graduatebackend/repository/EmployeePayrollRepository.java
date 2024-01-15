package com.graduatebackend.repository;

import com.graduatebackend.entity.EmployeePayroll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeePayrollRepository extends JpaRepository<EmployeePayroll, Integer> {
    /**
     * get payroll by month in year
     *
     * @param pageable
     * @param month
     * @param year
     * @return
     */
    @Query("SELECT p FROM EmployeePayroll p WHERE p.employeePayrollYear = :year AND p.employeePayrollMonth = :month " +
            "AND p.isDelete IS FALSE ")
    Page<EmployeePayroll> findByMonthInYear(Pageable pageable, @Param("month") int month, @Param("year") int year);

    /**
     * get payrolls by monthly payroll id
     *
     * @param pageable
     * @param monthlyPayrollId
     * @return
     */
    @Query("SELECT p FROM EmployeePayroll p WHERE p.monthlyPayroll.monthlyPayrollId = :id AND p.isDelete IS FALSE ")
    Page<EmployeePayroll> findByMonthlyPayrollId(Pageable pageable, @Param("id") Integer monthlyPayrollId);

    /**
     * find EmployeePayroll by id
     *
     * @param id
     * @return
     */
    Optional<EmployeePayroll> findByEmployeePayrollIdAndIsDeleteIsFalse(Integer id);
}
