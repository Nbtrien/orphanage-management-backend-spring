package com.graduatebackend.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.graduatebackend.entity.Family;
import com.graduatebackend.entity.FundingUsage;

public interface FundingUsageRepository extends JpaRepository<FundingUsage, Integer> {
    /**
     * get by id
     *
     * @param id
     * @return
     */
    Optional<FundingUsage> findByFundingUsageIdAndIsDeleteIsFalse(Integer id);

    /**
     * get all
     *
     * @param pageable
     * @return
     */
    @Query("SELECT f FROM FundingUsage f WHERE"
            + "(:keyword IS NULL OR f.purpose.purpose LIKE %:keyword% OR f.usageNote LIKE %:keyword%)"
            + " AND (:purposeId IS NULL OR f.purpose.donationPurposeId = :purposeId)"
            + " AND (:minUsageDate IS NULL OR f.usageTime >= :minUsageDate)"
            + " AND (:maxUsageDate IS NULL OR f.usageTime <= :maxUsageDate)"
            + " AND f.isDelete is FALSE")
    Page<FundingUsage> getAllByIsDeleteIsFalse(Pageable pageable, @Param("keyword") String keyword,
                                               @Param("purposeId") Integer purposeId,
                                               @Param("minUsageDate") Date minUsageDate,
                                               @Param("maxUsageDate") Date maxUsageDate);

    /**
     * get amount by family
     *
     * @param family
     * @param year
     * @return
     */
    @Query("SELECT SUM(f.amount) FROM FundingUsage f WHERE f.family = :family"
            + " AND (:year IS NULL OR FUNCTION('YEAR', f.usageTime) = :year)"
            + " AND f.isDelete IS FALSE")
    Optional<Double> getUsageAmount(@Param("family") Family family, @Param("year") Integer year);
    
    /**
     * find all
     *
     * @return
     */
    @Query("SELECT f FROM FundingUsage f WHERE f.isDelete IS FALSE")
    List<FundingUsage> findAll();
}
