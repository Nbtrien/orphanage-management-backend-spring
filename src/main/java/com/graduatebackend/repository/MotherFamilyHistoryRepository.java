package com.graduatebackend.repository;

import com.graduatebackend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface MotherFamilyHistoryRepository extends JpaRepository<MotherFamilyHistory, Integer> {
    /**
     * find newest by family and mother
     *
     * @param mother
     * @param family
     * @return
     */
    @Query("SELECT h FROM MotherFamilyHistory h WHERE h.mother = :mother AND h.family = :family"
            + " AND h.endDate IS NULL AND h.isDelete IS FALSE ")
    Optional<MotherFamilyHistory> findNewestByMotherAndFamily(@Param("mother") Mother mother,
                                                              @Param("family") Family family);

    @Query("SELECT h FROM MotherFamilyHistory h WHERE h.family = :family"
            + " AND ( (h.startDate BETWEEN :startDate AND :endDate)"
            + " OR (h.endDate BETWEEN :startDate AND :endDate))"
            + " AND h.isDelete IS FALSE ORDER BY h.startDate DESC")
    List<MotherFamilyHistory> getByFamilyAndDateRange(@Param("family") Family family,
                                                      @Param("startDate") Date startDate,
                                                      @Param("endDate") Date endDate);

    @Query("SELECT h FROM MotherFamilyHistory h WHERE h.family = :family"
            + " AND (h.startDate <= :startDate AND (h.endDate >= :startDate OR h.endDate IS NULL))"
            + " AND h.isDelete IS FALSE ORDER BY h.startDate DESC")
    List<MotherFamilyHistory> getByFamilyAndStartDate(@Param("family") Family family,
                                                      @Param("startDate") Date startDate);
}
