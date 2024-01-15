package com.graduatebackend.repository;

import com.graduatebackend.entity.Donation;
import com.graduatebackend.entity.DonationPurpose;
import com.graduatebackend.entity.Family;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation, Integer> {
    /**
     * find by id
     *
     * @param donationId
     * @return
     */
    Optional<Donation> findByDonationIdAndIsDeleteIsFalse(Integer donationId);

    /**
     * find all
     *
     * @param pageable
     * @param keyword
     * @param purposeId
     * @param minDonationDate
     * @param maxDonationDate
     * @return
     */
    @Query("SELECT d FROM Donation d WHERE"
            + "(:keyword IS NULL OR d.donor.donorFullName LIKE %:keyword%)"
            + " AND (:purposeId IS NULL OR d.purpose.donationPurposeId = :purposeId)"
            + " AND (:minDonationDate IS NULL OR d.donationTime >= :minDonationDate)"
            + " AND (:maxDonationDate IS NULL OR d.donationTime <= :maxDonationDate)"
            + " AND d.paymentStatus = 1 AND d.isInBlockchain IS TRUE AND d.isDelete is FALSE")
    Page<Donation> findAllDonations(Pageable pageable, @Param("keyword") String keyword,
                                    @Param("purposeId") Integer purposeId,
                                    @Param("minDonationDate") Date minDonationDate,
                                    @Param("maxDonationDate") Date maxDonationDate);

    /**
     * find by purpose
     *
     * @param pageable
     * @param purposeId
     * @return
     */
    @Query("SELECT d FROM Donation d WHERE d.purpose.donationPurposeId = :purposeId AND d.paymentStatus = 1 " +
            "AND d.isInBlockchain IS TRUE AND d.isUsed IS FALSE AND d.isDelete is FALSE")
    Page<Donation> findAvailableByPurpose(Pageable pageable, @Param("purposeId") Integer purposeId);

    /**
     * get donation statistics
     *
     * @return
     */
    @Query("SELECT COUNT(d.donationId), SUM(d.amount), SUM(d.usedAmount), COUNT(DISTINCT d.donor.donorId) FROM " +
            "Donation d " +
            "WHERE d.isDelete = false AND d.isInBlockchain = true")
    List<Object[]> getDonationStatistics();

    /**
     * get donation by month in year
     *
     * @param pageable
     * @param year
     * @param month
     * @return
     */
    @Query(value = "SELECT d FROM Donation d"
            + " WHERE FUNCTION('YEAR', d.donationTime) = :year"
            + " AND FUNCTION('MONTH', d.donationTime) = :month"
            + " AND d.isDelete = false AND d.paymentStatus = 1 AND d.isInBlockchain = true")
    Page<Donation> getDonationByMonthInYear(Pageable pageable, @Param("year") int year, @Param("month") int month);

    /**
     * get by month in year
     *
     * @param year
     * @param month
     * @return
     */
    @Query(value = "SELECT d FROM Donation d"
            + " WHERE FUNCTION('YEAR', d.donationTime) = :year"
            + " AND FUNCTION('MONTH', d.donationTime) = :month"
            + " AND d.isDelete = false AND d.paymentStatus = 1 AND d.isInBlockchain = true")
    List<Donation> getDonationByMonth(@Param("year") int year, @Param("month") int month);

    /**
     * count donation
     *
     * @return
     */
    @Query("SELECT COUNT(d.donationId) FROM Donation d WHERE d.paymentStatus = 1 AND d.isDelete IS FALSE")
    long count();

    /**
     * get total amount
     *
     * @return
     */
    @Query("SELECT SUM (d.amount) FROM Donation d WHERE d.paymentStatus = 1 AND d.isDelete IS FALSE")
    double getDonationAmount();

    /**
     * count by purpose
     *
     * @param purpose
     * @param year
     * @return
     */
    @Query("SELECT COUNT(d.donationId) FROM Donation d WHERE d.purpose = :purpose"
            + " AND (:year IS NULL OR FUNCTION('YEAR', d.donationTime) = :year)"
            + " AND d.paymentStatus = 1 AND d.isDelete IS FALSE")
    long count(@Param("purpose") DonationPurpose purpose, @Param("year") Integer year);

    /**
     * get amount by purpose
     *
     * @param purpose
     * @param year
     * @return
     */
    @Query("SELECT SUM (d.amount) FROM Donation d WHERE d.purpose = :purpose"
            + " AND (:year IS NULL OR FUNCTION('YEAR', d.donationTime) = :year)"
            + " AND d.paymentStatus = 1 AND d.isDelete IS FALSE")
    Optional<Double> getDonationAmount(@Param("purpose") DonationPurpose purpose, @Param("year") Integer year);

    /**
     * count by family
     *
     * @param family
     * @param year
     * @return
     */
    @Query("SELECT COUNT(d.donationId) FROM Donation d WHERE d.family = :family"
            + " AND (:year IS NULL OR FUNCTION('YEAR', d.donationTime) = :year)"
            + " AND d.paymentStatus = 1 AND d.isDelete IS FALSE")
    long count(@Param("family") Family family, @Param("year") Integer year);

    /**
     * get amount by family
     *
     * @param family
     * @param year
     * @return
     */
    @Query("SELECT SUM (d.amount) FROM Donation d WHERE d.family = :family"
            + " AND (:year IS NULL OR FUNCTION('YEAR', d.donationTime) = :year)"
            + " AND d.paymentStatus = 1 AND d.isDelete IS FALSE")
    Optional<Double> getDonationAmount(@Param("family") Family family, @Param("year") Integer year);

    /**
     * find all
     *
     * @return
     */
    @Query("SELECT d FROM Donation d WHERE d.paymentStatus = 1 AND d.isInBlockchain IS TRUE AND d.isDelete IS FALSE")
    List<Donation> findAll();
}
