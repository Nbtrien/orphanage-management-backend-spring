package com.graduatebackend.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.entity.Family;

public interface FamilyRepository extends JpaRepository<Family, Integer> {

	/**
	 * find family by name
	 *
	 * @param name
	 * @return
	 */
	Optional<Family> findFamilyByFamilyName(String name);

	/**
	 * find family by id
	 *
	 * @param id
	 * @return
	 */
	Optional<Family> findFamilyByFamilyIdAndIsDeleteIsFalse(Integer id);

	/**
	 * find all families
	 *
	 * @return
	 */
	Page<Family> findByIsDeleteIsFalse(Pageable pageable);

	/**
	 * search families
	 *
	 * @param pageable
	 * @param keyword
	 * @param familyCondition
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	@Query("SELECT f FROM Family f WHERE"
			+ " (:keyword IS NULL OR f.familyName LIKE %:keyword% OR f.mother.motherName LIKE %:keyword% )"
			+ " AND (:familyCondition IS NULL OR f.condition.familyConditionId = :familyCondition)"
			+ " AND (:fromDate IS NULL OR f.dateOfFormation >= :fromDate)"
			+ " AND (:toDate IS NULL OR f.dateOfFormation <= :toDate)" + " AND f.isDelete IS FALSE")
	Page<Family> searchByKeyword(Pageable pageable, @Param("keyword") String keyword,
			@Param("familyCondition") Integer familyCondition, @Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate);

	/**
	 * find all families
	 *
	 * @return
	 */
	List<Family> findByIsDeleteIsFalse();

	/**
	 * fetch families by age condition
	 *
	 * @param age
	 * @return
	 */
	@Query(value = "SELECT * FROM family as f INNER JOIN family_condition as c ON f.FAMILY_CONDITION_ID = c"
			+ ".FAMILY_CONDITION_ID WHERE c.AGE_FROM <= :age AND c.AGE_TO >= :age AND f.is_delete IS FALSE", nativeQuery = true)
	List<Family> findFamilyByAgeCondition(@Param("age") double age);

	/**
	 * get family with donations information
	 *
	 * @param familyId
	 * @return
	 */
	@Query("SELECT COUNT(d.donationId), SUM(d.amount) FROM Family f"
			+ " LEFT JOIN Donation d ON d.family = f AND d.isDelete IS FALSE" + " WHERE f.familyId = :familyId")
	List<Object[]> getFamilyDonationInfo(@Param("familyId") Integer familyId);

	/**
	 * get family with funding usage information
	 *
	 * @param familyId
	 * @return
	 */
	@Query("SELECT SUM(fd.amount) FROM Family f"
			+ " LEFT JOIN FundingUsage fd ON fd.family = f AND fd.isDelete IS FALSE" + " WHERE f.familyId = :familyId")
	List<Object[]> getFamilyFundingInfo(@Param("familyId") Integer familyId);

	/**
	 * get all family posts
	 *
	 * @return
	 */
	@Query("SELECT f FROM Family f where f.post IS NOT NULL AND f.post.isActive IS TRUE AND f.isDelete IS " + "FALSE ")
	List<Family> getFamilyPosts();

	/**
	 * Delete families in ids
	 *
	 * @param ids
	 */
	@Modifying
	@Transactional
	@Query("UPDATE Family f SET f.isDelete = TRUE WHERE f.familyId in(?1)")
	void softDeleteByIds(List<Integer> ids);
}
