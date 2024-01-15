package com.graduatebackend.repository;

import com.graduatebackend.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {
    /**
     * find by mail address
     *
     * @param mailAddress
     * @return
     */
    Optional<Applicant> findByApplicantMailAddressAndIsDeleteIsFalse(String mailAddress);

    /**
     * find by mail address or citizen id number
     *
     * @param mailAddress
     * @param citizenIdNumber
     * @return
     */
    @Query("SELECT a FROM Applicant a WHERE (a.applicantMailAddress = :mailAddress OR a.citizenIdNumber = " +
            ":citizenIdNumber) AND a.isDelete IS FALSE")
    Optional<Applicant> findByMailAddressOrCitizenId(@Param("mailAddress") String mailAddress,
                                                     @Param("citizenIdNumber") String citizenIdNumber);
}
