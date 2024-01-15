package com.graduatebackend.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import com.graduatebackend.dto.user.UpdateUserRequestDto;
import com.graduatebackend.dto.user.UserDto;
import com.graduatebackend.entity.Role;
import com.graduatebackend.entity.User;

@Mapper
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	/**
	 * Convert user entity to user dto
	 * 
	 * @param user
	 * @return
	 */
	@Named("toDto")
	UserDto toDto(User user);

	/**
	 * update user dto to user entity
	 * 
	 * @param user
	 * @param requestDto
	 */
	@Mapping(source = "rolesId", target = "roles", qualifiedByName = "toRoleSet")
	void updateEntity(@MappingTarget User user, UpdateUserRequestDto requestDto);

	/**
	 * Convert User entity list to User dto list
	 * 
	 * @param users
	 * @return
	 */
	@IterableMapping(qualifiedByName = "toDto")
	List<UserDto> toDtoList(List<User> users);

	/**
	 * Convert Page of User entity to Page of User dto
	 * 
	 * @param users
	 * @return
	 */
	default Page<UserDto> toDtoPage(Page<User> users) {
		return users.map(this::toDto);
	}

	/**
	 * Convert Set Role entity to Roles Id list
	 * 
	 * @param rolesId
	 * @return
	 */
	@Named("toRoleSet")
	default Set<Role> toArrayList(List<Integer> rolesId) {
		return rolesId.stream().map(id -> Role.builder().roleId(id).build()).collect(Collectors.toSet());
	}
}
