package com.graduatebackend.repository;

import com.graduatebackend.entity.FaqQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FaqQuestionRepository extends JpaRepository<FaqQuestion, Integer> {
    /**
     * find all
     *
     * @return
     */
    List<FaqQuestion> findAllByIsDeleteIsFalse();

    /**
     * find all
     *
     * @param pageable
     * @param keyword
     * @return
     */
    @Query("SELECT f FROM FaqQuestion f WHERE (:keyword IS NULL OR f.question LIKE %:keyword%)"
            + " AND f.isDelete IS FALSE")
    Page<FaqQuestion> findAll(Pageable pageable, @Param("keyword") String keyword);

    /**
     * soft delete by ids
     *
     * @param ids
     */
    @Modifying
    @Transactional
    @Query("UPDATE FaqQuestion f SET f.isDelete = TRUE WHERE f.faqId in :ids")
    void softDeleteByIds(@Param("ids") List<Integer> ids);
}
