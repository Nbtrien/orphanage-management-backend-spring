package com.graduatebackend.repository;

import com.graduatebackend.entity.InformationPageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InformationPageTypeRepository extends JpaRepository<InformationPageType, Integer> {
    /**
     * find by id
     *
     * @param id
     * @return
     */
    Optional<InformationPageType> findByPageTypeIdAndIsDeleteIsFalse(Integer id);

    /**
     * find all
     *
     * @param pageable
     * @param keyword
     * @return
     */
    @Query("SELECT t FROM InformationPageType t WHERE (:keyword IS NULL OR t.pageType LIKE %:keyword%)"
            + " AND t.isDelete IS FALSE ")
    Page<InformationPageType> findAll(Pageable pageable, @Param("keyword") String keyword);
}
