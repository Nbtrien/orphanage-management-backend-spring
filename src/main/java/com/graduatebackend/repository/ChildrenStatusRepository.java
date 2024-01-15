package com.graduatebackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graduatebackend.entity.ChildrenStatus;

public interface ChildrenStatusRepository extends JpaRepository<ChildrenStatus, Integer> {
	/**
	 * get all children status
	 *
	 * @return
	 */
	List<ChildrenStatus> findByIsDeleteIsFalse();

	/**
	 * find by id
	 *
	 * @param id
	 * @return
	 */
	Optional<ChildrenStatus> findChildrenStatusByChildrenStatusIdAndIsDeleteIsFalse(Integer id);
}
