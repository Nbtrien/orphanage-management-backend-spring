package com.graduatebackend.repository;

import com.graduatebackend.entity.AdoptionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.Optional;

public interface AdoptionHistoryRepository extends JpaRepository<AdoptionHistory, Integer> {
    /**
     * find all
     *
     * @param pageable
     * @param keyword
     * @param fromDate
     * @param toDate
     * @return
     */
    @Query("SELECT h FROM AdoptionHistory h WHERE (:keyword IS NULL OR (h.children.childrenFullName LIKE %:keyword%"
            + " OR h.applicant.applicantFullName LIKE %:keyword%))"
            + " AND (:fromDate IS NULL OR h.adoptionDate >= :fromDate)"
            + " AND (:toDate IS NULL OR h.adoptionDate <= :toDate)"
            + " AND h.isDelete IS FALSE")
    Page<AdoptionHistory> findAll(Pageable pageable, @Param("keyword") String keyword,
                                  @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    /**
     * get by id
     *
     * @param id
     * @return
     */
    Optional<AdoptionHistory> findByAdoptionHistoryIdAndIsDeleteIsFalse(Integer id);
}
