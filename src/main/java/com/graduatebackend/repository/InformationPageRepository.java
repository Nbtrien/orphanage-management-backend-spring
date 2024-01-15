package com.graduatebackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.graduatebackend.entity.InformationPage;

public interface InformationPageRepository extends JpaRepository<InformationPage, Integer> {
	/**
	 * find by id
	 *
	 * @param id
	 * @return
	 */
	Optional<InformationPage> findByInformationPageIdAndIsDeleteIsFalse(Integer id);

	/**
	 * find by pageTypeId
	 *
	 * @param pageTypeId
	 * @return
	 */
	@Query("SELECT p FROM InformationPage p WHERE p.pageType.pageTypeId = :pageTypeId AND p.isDelete IS FALSE")
	Optional<InformationPage> findByPageType(@Param("pageTypeId") Integer pageTypeId);
}
