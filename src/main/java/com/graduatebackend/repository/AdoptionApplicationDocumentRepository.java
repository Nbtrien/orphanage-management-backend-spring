package com.graduatebackend.repository;

import com.graduatebackend.entity.AdoptionApplicationDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdoptionApplicationDocumentRepository extends JpaRepository<AdoptionApplicationDocument, Integer> {

    /**
     * find by AdoptionApplicationId
     *
     * @param applicationId
     * @return
     */
    @Query("SELECT d FROM AdoptionApplicationDocument d WHERE d.application.adoptionApplicationId = :applicationId " +
            "AND d.isDelete IS FALSE ")
    Optional<AdoptionApplicationDocument> findByAdoptionApplicationId(@Param("applicationId") Integer applicationId);
}
