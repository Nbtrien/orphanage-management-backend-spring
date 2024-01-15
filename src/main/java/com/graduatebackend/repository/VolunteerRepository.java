package com.graduatebackend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.graduatebackend.entity.Event;
import com.graduatebackend.entity.Volunteer;

public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {

	/**
	 * find event
	 *
	 * @param pageable
	 * @param event
	 * @param applicationStatus
	 * @param isMember
	 * @param keyword
	 * @return
	 */
	@Query("SELECT v FROM Volunteer v INNER JOIN EventApplication a ON a.volunteer = v AND a.isDelete IS FALSE"
			+ " WHERE a.event = :event"
			+ " AND (:applicationStatus IS NULL OR a.applicationStatus = :applicationStatus)"
			+ " AND (:isMember IS NULL OR (:isMember IS TRUE AND v.isMember IS TRUE)"
			+ " OR (:isMember IS FALSE AND v.isMember IS FALSE))"
			+ " AND (:keyword IS NULL OR v.applicant.applicantFullName LIKE LOWER(CONCAT('%', :keyword, '%')))"
			+ " AND v.isDelete IS FALSE ORDER BY a.applicationStatus ASC")
	Page<Volunteer> findAllByEvent(Pageable pageable, @Param("event") Event event,
			@Param("applicationStatus") Integer applicationStatus, @Param("isMember") Boolean isMember,
			@Param("keyword") String keyword);

	/**
	 * find all
	 *
	 * @param pageable
	 * @param isMember
	 * @param keyword
	 * @return
	 */
	@Query("SELECT v FROM Volunteer v" + " WHERE (:isMember IS NULL OR (:isMember IS TRUE AND v.isMember IS TRUE)"
			+ " OR (:isMember IS FALSE AND v.isMember IS FALSE))"
			+ " AND (:keyword IS NULL OR v.applicant.applicantFullName LIKE LOWER(CONCAT('%', :keyword, '%')))"
			+ " AND v.isDelete IS FALSE")
	Page<Volunteer> findAll(Pageable pageable, @Param("isMember") Boolean isMember, @Param("keyword") String keyword);

	/**
	 * find by id
	 *
	 * @param id
	 * @return
	 */
	Optional<Volunteer> findByVolunteerIdAndIsDeleteIsFalse(Integer id);
}
