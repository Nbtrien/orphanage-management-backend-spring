package com.graduatebackend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.graduatebackend.dto.user.AddNewRoleDto;
import com.graduatebackend.dto.user.PermissionDto;
import com.graduatebackend.dto.user.RoleDetailDto;
import com.graduatebackend.dto.user.RoleDto;

public interface RoleService {
	/**
	 * get all roles
	 *
	 * @return
	 */
	List<RoleDto> fetchAll();

	/**
	 * get roles with pageable
	 *
	 * @return
	 */
	Page<RoleDetailDto> fetch(PageRequest pageRequest, String keyword, Integer permissionId);

	/**
	 * search roles
	 *
	 * @param pageRequest
	 * @param key
	 * @return
	 */
	Page<RoleDetailDto> search(PageRequest pageRequest, String key);

	/**
	 * get role detail
	 *
	 * @param id
	 * @return
	 */
	RoleDetailDto getDetail(Integer id);

	/**
	 * save new role
	 *
	 * @param roleDto
	 */
	void addNew(AddNewRoleDto roleDto);

	/**
	 * update role
	 *
	 * @param roleDto
	 * @return
	 */
	RoleDetailDto update(Integer id, AddNewRoleDto roleDto);

	/**
	 * delete multiple roles
	 *
	 * @param ids
	 */
	void delete(List<Integer> ids);

	/**
	 * get all permissions
	 *
	 * @return
	 */
	List<PermissionDto> fetchAllPermissions();
}
