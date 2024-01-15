package com.graduatebackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.graduatebackend.entity.Children;
import com.graduatebackend.entity.ChildrenStatus;
import com.graduatebackend.entity.ChildrenStatusHistory;

public interface StatusHistoryRepository extends JpaRepository<ChildrenStatusHistory, Integer> {
	/**
	 * find newest by children and status
	 *
	 * @param children
	 * @param status
	 * @return
	 */
	@Query("SELECT h FROM ChildrenStatusHistory h WHERE h.children = :children AND h.childrenStatus = :status"
			+ " AND h.endDate IS NULL AND h.isDelete IS FALSE")
	Optional<ChildrenStatusHistory> findNewestByChildrenAndStatus(@Param("children") Children children,
			@Param("status") ChildrenStatus status);

	/**
	 * get by children
	 *
	 * @param children
	 * @return
	 */
	@Query("SELECT h FROM ChildrenStatusHistory h WHERE h.children = :children"
			+ " AND h.isDelete IS FALSE ORDER BY h.startDate DESC")
	List<ChildrenStatusHistory> getAllByChildren(@Param("children") Children children);
}
