package com.graduatebackend.repository;

import com.graduatebackend.entity.ArticleCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ArticleCategoryRepository extends JpaRepository<ArticleCategory, Integer> {
    /**
     * find all
     *
     * @return
     */
    List<ArticleCategory> findAllByIsDeleteIsFalse();

    /**
     * get all
     *
     * @param pageable
     * @param keyword
     * @return
     */
    @Query("SELECT c FROM ArticleCategory c WHERE (:keyword IS NULL OR c.categoryName LIKE %:keyword%)"
            + " AND c.isDelete IS FALSE")
    Page<ArticleCategory> findAll(Pageable pageable, @Param("keyword") String keyword);

    /**
     * find by id
     *
     * @param integer
     * @return
     */
    Optional<ArticleCategory> findByArticleCategoryIdAndIsDeleteIsFalse(Integer integer);

    /**
     * soft delete by ids
     *
     * @param ids
     */
    @Modifying
    @Transactional
    @Query("UPDATE ArticleCategory c SET c.isDelete = TRUE WHERE c.articleCategoryId in (:ids)")
    void deleteByIds(@Param("ids") List<Integer> ids);
}
