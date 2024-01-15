package com.graduatebackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graduatebackend.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
	/**
	 * get all permissions
	 * 
	 * @return
	 */
	List<Permission> findByIsDeleteIsFalse();
}
