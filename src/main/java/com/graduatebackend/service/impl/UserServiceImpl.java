package com.graduatebackend.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.dto.authentication.RegisterRequestDto;
import com.graduatebackend.dto.user.UpdateUserRequestDto;
import com.graduatebackend.dto.user.UserDto;
import com.graduatebackend.entity.Role;
import com.graduatebackend.entity.User;
import com.graduatebackend.exception.EmailExistedException;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.exception.UserExistsException;
import com.graduatebackend.mapper.UserMapper;
import com.graduatebackend.repository.RoleRepository;
import com.graduatebackend.repository.UserRepository;
import com.graduatebackend.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	@Override
	public Page<UserDto> fetch(PageRequest pageRequest, String keyword, Integer roleId) {
		Page<User> users = userRepository.findAll(pageRequest, keyword, roleId);
		return UserMapper.INSTANCE.toDtoPage(users);
	}

	@Override
	public UserDto getDetail(Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found."));
		return UserMapper.INSTANCE.toDto(user);
	}

	@Override
	public Page<UserDto> search(PageRequest pageRequest, String key) {
		Page<User> users = userRepository.searchUsers(pageRequest, key);
		return UserMapper.INSTANCE.toDtoPage(users);
	}

	@Override
	public void addNew(RegisterRequestDto registerRequestDto) {
		Optional<User> userCheck = userRepository.findByUserMailAddress(registerRequestDto.getEmail());
		if (userCheck.isPresent()) {
			throw new EmailExistedException();
		}
		Optional<Integer> userNameCheck = userRepository.checkUserIsExists(registerRequestDto.getUserName());
		if (userNameCheck.isPresent()) {
			throw new UserExistsException();
		}
		Set<Role> roles = registerRequestDto.getRolesId().stream().map(id -> roleRepository.findById(id).orElse(null))
				.filter(Objects::nonNull).collect(Collectors.toSet());

		roles.add(roleRepository.findByRoleTitle("ADMIN").orElse(null));

		User user = User.builder().userName(registerRequestDto.getUserName())
				.userMailAddress(registerRequestDto.getEmail())
				.userPassword(passwordEncoder.encode(registerRequestDto.getPassword())).roles(roles).build();
		userRepository.saveAndFlush(user);
	}

	@Transactional
	@Override
	public UserDto update(Integer id, UpdateUserRequestDto requestDto) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found."));
		UserMapper.INSTANCE.updateEntity(user, requestDto);

		Set<Role> newRoles = new HashSet<>();
		for (Role role : user.getRoles()) {
			Role role1 = roleRepository.findById(role.getRoleId()).orElseThrow(null);
			newRoles.add(role1);
		}
		user.setRoles(newRoles);
		if (requestDto.getPassword() != null) {
			user.setUserPassword(passwordEncoder.encode(requestDto.getPassword()));
		}
		User userSaved = userRepository.save(user);
		return UserMapper.INSTANCE.toDto(userSaved);
	}

	@Override
	public void delete(Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found."));
		user.setDelete(true);
		userRepository.save(user);
	}

	@Override
	public void delete(List<Integer> ids) {
		userRepository.softDeleteByIds(ids);
	}

}
