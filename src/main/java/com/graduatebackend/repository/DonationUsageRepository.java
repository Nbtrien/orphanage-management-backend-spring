package com.graduatebackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.graduatebackend.entity.DonationUsage;

public interface DonationUsageRepository extends JpaRepository<DonationUsage, Integer> {
    /**
     * get total usage amount by month in year
     *
     * @param year
     * @param month
     * @return
     */
    @Query(value = "SELECT SUM(u.amount) FROM DonationUsage u"
            + " WHERE FUNCTION('YEAR', u.usageTime) = :year"
            + " AND FUNCTION('MONTH', u.usageTime) = :month"
            + " AND u.isDelete = false AND u.isInBlockchain = true")
    Optional<Long> getUsageAmountByMonthInYear(@Param("year") int year, @Param("month") int month);
}
