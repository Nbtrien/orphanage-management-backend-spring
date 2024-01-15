package com.graduatebackend.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
	/**
	 * find by id
	 *
	 * @param id
	 * @return
	 */
	Optional<Article> findByArticleIdAndIsDeleteIsFalse(Integer id);

	/**
	 * find all
	 *
	 * @param pageable
	 * @param keyword
	 * @param categoryId
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	@Query("SELECT a FROM Article a WHERE (:keyword IS NULL OR a.articleTitle LIKE %:keyword%)"
			+ " AND (:categoryId IS NULL OR a.category.articleCategoryId = :categoryId)"
			+ " AND (:fromDate IS NULL OR a.publicationStartDateTime >= :fromDate)"
			+ " AND (:toDate IS NULL OR a.publicationStartDateTime <= :toDate)" + " AND a.isDelete IS FALSE")
	Page<Article> findAll(Pageable pageable, @Param("keyword") String keyword, @Param("categoryId") Integer categoryId,
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

	/**
	 * get all post
	 *
	 * @param pageable
	 * @param keyword
	 * @param categoryId
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	@Query("SELECT a FROM Article a WHERE (:keyword IS NULL OR a.articleTitle LIKE %:keyword%)"
			+ " AND (:categoryId IS NULL OR a.category.articleCategoryId = :categoryId)"
			+ " AND (:fromDate IS NULL OR a.publicationStartDateTime >= :fromDate)"
			+ " AND (:toDate IS NULL OR a.publicationStartDateTime <= :toDate)"
			+ " AND a.publicationStartDateTime <= current_timestamp"
			+ " AND (a.publicationEndDateTime IS NULL OR a.publicationEndDateTime >= current_timestamp)"
			+ " AND a.isDelete IS FALSE")
	Page<Article> findAllPost(Pageable pageable, @Param("keyword") String keyword,
			@Param("categoryId") Integer categoryId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

	/**
	 * get related articles
	 *
	 * @param pageable
	 * @param articleId
	 * @param categoryId
	 * @return
	 */
	@Query("SELECT a FROM Article a WHERE a.category.articleCategoryId = :categoryId AND a.articleId <> :articleId"
			+ " AND a.publicationStartDateTime <= current_timestamp"
			+ " AND (a.publicationEndDateTime IS NULL OR a.publicationEndDateTime >= current_timestamp)"
			+ " AND a.isDelete IS FALSE ORDER BY a.publicationStartDateTime DESC")
	List<Article> getRelatedArticles(Pageable pageable, @Param("articleId") Integer articleId,
			@Param("categoryId") Integer categoryId);

	/**
	 * get top articles
	 *
	 * @param pageable
	 * @return
	 */
	@Query("SELECT a FROM Article a WHERE a.isDelete IS FALSE" + " AND a.publicationStartDateTime <= current_timestamp"
			+ " AND (a.publicationEndDateTime IS NULL OR a.publicationEndDateTime >= current_timestamp)"
			+ " ORDER BY a.updateDateTime DESC")
	List<Article> getTopArticles(Pageable pageable);

	/**
	 * soft delete by ids
	 *
	 * @param ids
	 */
	@Modifying
	@Transactional
	@Query("UPDATE Article a SET a.isDelete = TRUE WHERE a.articleId in (:ids)")
	void deleteByIds(@Param("ids") List<Integer> ids);

	/**
	 * hide by ids
	 *
	 * @param ids
	 */
	@Modifying
	@Transactional
	@Query("UPDATE Article a SET a.publicationEndDateTime = current_timestamp WHERE a.articleId in (:ids)")
	void hideByIds(@Param("ids") List<Integer> ids);

	/**
	 * active by ids
	 *
	 * @param ids
	 */
	@Modifying
	@Transactional
	@Query("UPDATE Article a SET a.publicationEndDateTime = NULL WHERE a.articleId in (:ids)")
	void activeByIds(@Param("ids") List<Integer> ids);
}
