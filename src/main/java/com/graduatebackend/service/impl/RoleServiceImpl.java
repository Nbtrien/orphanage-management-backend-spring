package com.graduatebackend.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.dto.user.AddNewRoleDto;
import com.graduatebackend.dto.user.PermissionDto;
import com.graduatebackend.dto.user.RoleDetailDto;
import com.graduatebackend.dto.user.RoleDto;
import com.graduatebackend.entity.Permission;
import com.graduatebackend.entity.Role;
import com.graduatebackend.exception.ResourceExistedException;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.RoleMapper;
import com.graduatebackend.repository.PermissionRepository;
import com.graduatebackend.repository.RoleRepository;
import com.graduatebackend.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
	private final RoleRepository roleRepository;

	private final PermissionRepository permissionRepository;

	@Override
	public List<RoleDto> fetchAll() {
		List<Role> roles = roleRepository.findByIsDeleteIsFalse();
		return RoleMapper.INSTANCE.toDtoList(roles);
	}

	@Override
	public Page<RoleDetailDto> fetch(PageRequest pageRequest, String keyword, Integer permissionId) {
		Page<Role> rolePage = roleRepository.findAll(pageRequest, keyword, permissionId);
		return RoleMapper.INSTANCE.toDetailDtoPage(rolePage);
	}

	@Override
	public Page<RoleDetailDto> search(PageRequest pageRequest, String key) {
		Page<Role> roles = roleRepository.searchRoles(pageRequest, key);
		return RoleMapper.INSTANCE.toDetailDtoPage(roles);
	}

	@Override
	public RoleDetailDto getDetail(Integer id) {
		Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("role not found."));
		return RoleMapper.INSTANCE.toDetailDto(role);
	}

	@Override
	@Transactional
	public void addNew(AddNewRoleDto roleDto) {
		Optional<Role> roleCheck = roleRepository.findByRoleTitle(roleDto.getRoleTitle());
		if (roleCheck.isPresent()) {
			throw new ResourceExistedException("This role title is already in use. Please use another one.");
		}
		Role role = RoleMapper.INSTANCE.toEntity(roleDto);

		Set<Permission> permissions = roleDto.getPermissionsId().stream()
				.map(id -> permissionRepository.findById(id).orElse(null)).filter(Objects::nonNull)
				.collect(Collectors.toSet());
		role.setPermissions(permissions);
		roleRepository.saveAndFlush(role);
	}

	@Override
	public RoleDetailDto update(Integer id, AddNewRoleDto roleDto) {
		Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("role not found."));
		RoleMapper.INSTANCE.updateEntity(role, roleDto);

		Set<Permission> permissions = roleDto.getPermissionsId().stream()
				.map(i -> permissionRepository.findById(i).orElse(null)).filter(Objects::nonNull)
				.collect(Collectors.toSet());
		role.setPermissions(permissions);

		Role roleSaved = roleRepository.save(role);
		return RoleMapper.INSTANCE.toDetailDto(roleSaved);
	}

	@Override
	public void delete(List<Integer> ids) {
		roleRepository.softDeleteByIds(ids);
	}

	@Override
	public List<PermissionDto> fetchAllPermissions() {
		List<Permission> permissions = permissionRepository.findByIsDeleteIsFalse();
		return RoleMapper.INSTANCE.toPermissionDtoList(permissions);
	}
}
