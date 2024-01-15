package com.graduatebackend.repository;

import com.graduatebackend.entity.AdoptionApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdoptionApplicationRepository extends JpaRepository<AdoptionApplication, Integer> {

    /**
     * find all AdoptionApplication
     *
     * @param pageable
     * @param applicationStatus
     * @return
     */
    @Query("SELECT a FROM AdoptionApplication a WHERE (:applicationStatus IS NULL OR a.applicationStatus = " +
            ":applicationStatus) AND a.isDelete IS FALSE ")
    Page<AdoptionApplication> findAll(Pageable pageable, @Param("applicationStatus") Integer applicationStatus);

    /**
     * find by id
     *
     * @param id
     * @return
     */
    Optional<AdoptionApplication> findByAdoptionApplicationIdAndIsDeleteIsFalse(Integer id);
}
