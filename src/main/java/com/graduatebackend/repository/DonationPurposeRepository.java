package com.graduatebackend.repository;

import com.graduatebackend.entity.DonationPurpose;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DonationPurposeRepository extends JpaRepository<DonationPurpose, Integer> {
    /**
     * find all
     *
     * @return
     */
    List<DonationPurpose> findByIsDeleteIsFalse();

    /**
     * find all
     *
     * @param pageable Pageable
     * @return Page<DonationPurpose>
     */
    Page<DonationPurpose> findByIsDeleteIsFalse(Pageable pageable);

    /**
     * fetch all
     *
     * @param pageable
     * @param isActive
     * @param keyword
     * @return
     */
    @Query("SELECT p FROM DonationPurpose p WHERE (:keyword IS NULL OR p.purpose LIKE %:keyword%)"
            + " AND (:isActive IS NULL OR (:isActive = true AND p.isActive IS TRUE)"
            + " OR (:isActive = false AND p.isActive IS false)) AND p.isDelete IS FALSE")
    Page<DonationPurpose> findAll(Pageable pageable, @Param("keyword") String keyword,
                                  @Param("isActive") Boolean isActive);

    /**
     * find by id
     *
     * @param id
     * @return
     */
    Optional<DonationPurpose> findByDonationPurposeIdAndIsDeleteIsFalse(Integer id);

    /**
     * get donation programs
     *
     * @return
     */

    @Query("SELECT d FROM DonationPurpose d where d.post IS NOT NULL AND d.post.isActive IS TRUE AND d.isDelete IS " +
            "FALSE ")
    List<DonationPurpose> getDonationPrograms();
}
