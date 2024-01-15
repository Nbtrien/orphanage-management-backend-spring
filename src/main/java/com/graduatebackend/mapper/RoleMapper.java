package com.graduatebackend.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import com.graduatebackend.dto.user.AddNewRoleDto;
import com.graduatebackend.dto.user.PermissionDto;
import com.graduatebackend.dto.user.RoleDetailDto;
import com.graduatebackend.dto.user.RoleDto;
import com.graduatebackend.entity.Permission;
import com.graduatebackend.entity.Role;

@Mapper
public interface RoleMapper {
	RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

	/**
	 * Convert Role entity to User entity
	 * 
	 * @param role
	 * @return
	 */
	@Named("toDto")
	RoleDto toDto(Role role);

	/**
	 * Convert Role entity to Role detail dto with permission
	 * 
	 * @param role
	 * @return
	 */
	@Named("toDetailDto")
	RoleDetailDto toDetailDto(Role role);

	/**
	 * Convert AddNewRole dto to Role entity
	 * 
	 * @param roleDto
	 * @return
	 */
	@Named("toEntity")
	Role toEntity(AddNewRoleDto roleDto);

	/**
	 * update Role entity from AddNewRole dto
	 * 
	 * @param role
	 * @param requestDto
	 */
	void updateEntity(@MappingTarget Role role, AddNewRoleDto requestDto);

	/**
	 * Convert Role entity list to Role dto list
	 * 
	 * @param roles
	 * @return
	 */
	@IterableMapping(qualifiedByName = "toDto")
	List<RoleDto> toDtoList(List<Role> roles);

	/**
	 * Convert Page of Role entity to Page of Role dto
	 * 
	 * @param roles
	 * @return
	 */
	default Page<RoleDetailDto> toDetailDtoPage(Page<Role> roles) {
		return roles.map(this::toDetailDto);
	}

	/**
	 * Convert Permissions Id list to Permissions entity set
	 * 
	 * @param permissionsId
	 * @return
	 */
	@Named("toPermissionSet")
	default Set<Permission> toArrayList(List<Integer> permissionsId) {
		return permissionsId.stream().map(id -> Permission.builder().permissionId(id).build())
				.collect(Collectors.toSet());
	}

	/**
	 * Convert Permission entity to Permission dto
	 * 
	 * @param permission
	 * @return
	 */
	@Named("toPermissionDto")
	PermissionDto toPermissionDto(Permission permission);

	/**
	 * Convert Permission entity list to Permission dto list
	 * 
	 * @param permissions
	 * @return
	 */
	@IterableMapping(qualifiedByName = "toPermissionDto")
	List<PermissionDto> toPermissionDtoList(List<Permission> permissions);
}
