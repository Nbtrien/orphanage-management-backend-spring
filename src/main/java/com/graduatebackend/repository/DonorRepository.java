package com.graduatebackend.repository;

import com.graduatebackend.entity.Donor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DonorRepository extends JpaRepository<Donor, Integer> {
    /**
     * find by token
     *
     * @param token
     * @return
     */
    Optional<Donor> findByDonorTokenAndIsDeleteIsFalse(String token);

    /**
     * find by id
     *
     * @param id
     * @return
     */
    Optional<Donor> findByDonorIdAndIsDeleteIsFalse(Integer id);

    /**
     * find all donors
     *
     * @param pageable
     * @return
     */
    @Query("SELECT d FROM Donor d"
            + " WHERE (:name IS NULL OR d.donorFullName LIKE %:name% ) "
            + " AND (:email IS NULL OR d.donorMailAddress LIKE %:email% ) "
            + " AND (:phoneNumber IS NULL OR d.donorPhoneNumber LIKE %:phoneNumber% ) "
            + " AND d.isDelete IS FALSE")
    Page<Donor> findAllDonors(Pageable pageable, @Param("name") String name, @Param("email") String email,
                              @Param("phoneNumber") String phoneNumber);

    /**
     * find top donor
     *
     * @param pageable
     * @return
     */
    @Query("SELECT d FROM Donor d LEFT JOIN Donation do ON do.donor = d AND do.paymentStatus = 1"
            + " AND do.isInBlockchain IS TRUE AND do.isDelete IS FALSE WHERE d.isDelete IS FALSE "
            + " GROUP BY d.donorId ORDER BY SUM(do.amount) DESC")
    List<Donor> findByTopDonation(Pageable pageable);
    
    /**
     * find all
     * @return
     */
    List<Donor> findByIsDeleteIsFalse();
}
