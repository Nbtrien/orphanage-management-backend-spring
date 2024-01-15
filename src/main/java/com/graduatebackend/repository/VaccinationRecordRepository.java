package com.graduatebackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.graduatebackend.entity.Children;
import com.graduatebackend.entity.VaccinationRecord;

public interface VaccinationRecordRepository extends JpaRepository<VaccinationRecord, Integer> {
	/**
	 * find by id
	 *
	 * @param id
	 * @return
	 */
	Optional<VaccinationRecord> findByVaccinationRecordIdAndIsDeleteIsFalse(Integer id);

	/**
	 * get by children
	 *
	 * @param children
	 * @return
	 */
	@Query("SELECT v FROM VaccinationRecord v WHERE v.children = :children AND v.isDelete IS FALSE"
			+ " ORDER BY v.vaccinationDate DESC ")
	List<VaccinationRecord> getByChildren(@Param("children") Children children);
}
