package com.graduatebackend.repository;

import com.graduatebackend.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    /**
     * find by id
     *
     * @param id
     * @return
     */
    Optional<Account> findByAccountIdAndIsDeleteIsFalse(Integer id);

    /**
     * find by mail address
     *
     * @param mailAddress
     * @return
     */
    Optional<Account> findByAccountMailAddressAndIsDeleteIsFalse(String mailAddress);

    /**
     * load account by email
     *
     * @param mailAddress
     * @return
     */
    @Query("SELECT a FROM Account a WHERE a.accountMailAddress = :mailAddress AND a.isFirstLogin IS TRUE AND a" +
            ".isDelete IS FALSE")
    Optional<Account> loadByEmail(@Param("mailAddress") String mailAddress);

    /**
     * find all
     *
     * @param pageable
     * @param keyword
     * @param accountType
     * @param accountStatus
     * @return
     */
    @Query("SELECT a FROM Account a WHERE"
            + "(:keyword IS NULL OR a.accountMailAddress LIKE %:keyword%"
            + " OR a.applicant.applicantFullName LIKE %:keyword%)"
            + " AND (:accountType IS NULL OR (:accountType = 1 AND a.volunteer IS NOT NULL)"
            + " OR (:accountType = 2 AND a.donor IS NOT NULL))"
            + " AND (:accountStatus IS NULL OR a.accountStatus = :accountStatus)"
            + " AND a.isDelete IS FALSE")
    Page<Account> findAll(Pageable pageable, @Param("keyword") String keyword,
                          @Param("accountType") Integer accountType,
                          @Param("accountStatus") Integer accountStatus);

    /**
     * update account status
     *
     * @param accountId
     * @param status
     */
    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.accountStatus =:status WHERE a.accountId = :accountId")
    void updateAccountStatus(@Param("accountId") Integer accountId, @Param("status") Integer status);
}
