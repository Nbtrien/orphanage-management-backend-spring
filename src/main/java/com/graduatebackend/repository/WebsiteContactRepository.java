package com.graduatebackend.repository;

import com.graduatebackend.entity.WebsiteContact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WebsiteContactRepository extends JpaRepository<WebsiteContact, Integer> {
    /**
     * find all
     *
     * @return
     */
    List<WebsiteContact> findAllByIsDeleteIsFalse();

    /**
     * find all
     *
     * @param pageable the pageable to request a paged result, can be {@link Pageable#unpaged()}, must not be
     *                 {@literal null}.
     * @return
     */
    @Override
    Page<WebsiteContact> findAll(Pageable pageable);

    /**
     * find one
     *
     * @return
     */
    @Query("SELECT w FROM  WebsiteContact w WHERE w.isDelete IS FALSE")
    Optional<WebsiteContact> findOne();


    /**
     * get phone number
     *
     * @return
     */
    @Query("SELECT w.phoneNumber FROM  WebsiteContact w WHERE w.isDelete IS FALSE")
    String getPhoneNumber();

    /**
     * get address
     *
     * @return
     */
    @Query("SELECT w.address FROM  WebsiteContact w WHERE w.isDelete IS FALSE")
    String getAddress();

    /**
     * get email
     *
     * @return
     */
    @Query("SELECT w.mailAddress FROM  WebsiteContact w WHERE w.isDelete IS FALSE")
    String getEmail();
}
