package com.graduatebackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.graduatebackend.entity.Children;
import com.graduatebackend.entity.ChildrenFamilyHistory;
import com.graduatebackend.entity.Family;

public interface ChildrenFamilyHistoryRepository extends JpaRepository<ChildrenFamilyHistory, Integer> {
	/**
	 * find by children and family
	 *
	 * @param children
	 * @param family
	 * @return
	 */
	Optional<ChildrenFamilyHistory> findByChildrenAndFamily(Children children, Family family);

	/**
	 * find newest by children and family
	 *
	 * @param children
	 * @param family
	 * @return
	 */
	@Query("SELECT h FROM ChildrenFamilyHistory h WHERE h.children = :children AND h.family = :family"
			+ " AND h.endDate IS NULL AND h.isDelete IS FALSE ")
	Optional<ChildrenFamilyHistory> findNewestByChildrenAndFamily(@Param("children") Children children,
			@Param("family") Family family);

	/**
	 * get by children
	 *
	 * @param children
	 * @return
	 */
	@Query("SELECT h FROM ChildrenFamilyHistory h WHERE h.children = :children"
			+ " AND h.isDelete is FALSE ORDER BY h.startDate DESC ")
	List<ChildrenFamilyHistory> getByChildren(@Param("children") Children children);
}
