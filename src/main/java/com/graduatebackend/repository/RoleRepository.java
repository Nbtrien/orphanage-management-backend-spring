package com.graduatebackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.entity.Role;
import com.graduatebackend.entity.User;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	/**
	 * find by role title
	 * 
	 * @param roleTile
	 * @return
	 */
	Optional<Role> findByRoleTitle(String roleTile);

	/**
	 * get all roles
	 * 
	 * @return
	 */
	List<Role> findByIsDeleteIsFalse();

	/**
	 * get roles page is not deleted
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Role> findByIsDeleteIsFalse(Pageable pageable);

	/**
	 * find all
	 * 
	 * @param pageable
	 * @param key
	 * @param roleId
	 * @return
	 */
	@Query("SELECT DISTINCT r FROM Role r JOIN r.permissions p WHERE (:keyword IS NULL OR r.roleTitle LIKE %:keyword%) AND (:permissionId IS NULL OR p.permissionId = :permissionId) AND r.isDelete IS FALSE")
	Page<Role> findAll(Pageable pageable, @Param("keyword") String keyword,
			@Param("permissionId") Integer permissionId);

	/**
	 * search roles
	 * 
	 * @param pageable
	 * @param key
	 * @return
	 */
	@Query(value = "SELECT r FROM Role r " + "WHERE ((r.roleTitle LIKE %:key%)) AND r.isDelete IS FALSE ")
	Page<Role> searchRoles(Pageable pageable, @Param("key") String key);

	/**
	 * delete users by ids
	 * 
	 * @param ids
	 */
	@Modifying
	@Transactional
	@Query("UPDATE Role r SET r.isDelete = TRUE WHERE r.roleId in(?1)")
	void softDeleteByIds(List<Integer> ids);
}
