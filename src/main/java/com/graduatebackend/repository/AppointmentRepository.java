package com.graduatebackend.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.entity.Account;
import com.graduatebackend.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
	/**
	 * fetch all
	 *
	 * @param pageable
	 * @param keyword
	 * @param status
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	@Query("SELECT a FROM Appointment a WHERE"
			+ " (:keyword IS NULL OR a.account.applicant.applicantFullName LIKE %:keyword%)"
			+ " AND (:status IS NULL OR a.appointmentStatus = :status)"
			+ " AND (:fromDate IS NULL OR a.appointmentStartDateTime >= :fromDate)"
			+ " AND (:toDate IS NULL OR a.appointmentStartDateTime <= :toDate)" + " AND a.isDelete IS FALSE")
	Page<Appointment> findAll(Pageable pageable, @Param("keyword") String keyword, @Param("status") Integer status,
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

	/**
	 * find by account
	 *
	 * @param pageable
	 * @param account
	 * @return
	 */
	Page<Appointment> findByAccount(Pageable pageable, Account account);

	/**
	 * find by id
	 *
	 * @param id
	 * @return
	 */
	Optional<Appointment> findByAppointmentIdAndIsDeleteIsFalse(Integer id);

	/**
	 * find by ids
	 *
	 * @param ids
	 * @return
	 */
	@Query("SELECT a FROM Appointment a WHERE a.appointmentId in :ids")
	List<Appointment> findByIds(@Param("ids") List<Integer> ids);

	/**
	 * get appointment calendar
	 *
	 * @return
	 */
	@Query("SELECT a FROM Appointment a WHERE" + " a.appointmentStartDateTime BETWEEN :startDate AND :endDate"
			+ " AND a.appointmentStatus in :status" + " AND a.isDelete IS FALSE")
	List<Appointment> getAppointmentCalendar(@Param("startDate") Timestamp startDate,
			@Param("endDate") Timestamp endDate, @Param("status") List<Integer> status);

	/**
	 * update appointment status
	 *
	 * @param ids
	 * @param status
	 */
	@Transactional
	@Modifying
	@Query("UPDATE Appointment a SET a.appointmentStatus = :status" + " WHERE a.appointmentId in (:ids)")
	void updateAppointmentsStatus(@Param("ids") List<Integer> ids, @Param("status") Integer status);
}
