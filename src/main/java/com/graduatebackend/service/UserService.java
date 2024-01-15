package com.graduatebackend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.graduatebackend.dto.authentication.RegisterRequestDto;
import com.graduatebackend.dto.user.UpdateUserRequestDto;
import com.graduatebackend.dto.user.UserDto;

public interface UserService {
	/**
	 * get all user
	 *
	 * @return
	 */
	Page<UserDto> fetch(PageRequest pageRequest, String keyword, Integer roleId);

	/**
	 * get user by id
	 *
	 * @param id
	 * @return
	 */
	UserDto getDetail(Integer id);

	/**
	 * search users
	 *
	 * @param pageRequest
	 * @param key
	 * @return
	 */
	Page<UserDto> search(PageRequest pageRequest, String key);

	/**
	 * save new user
	 *
	 * @param registerRequestDto
	 */
	void addNew(RegisterRequestDto registerRequestDto);

	/**
	 * update user
	 *
	 * @param id
	 */
	UserDto update(Integer id, UpdateUserRequestDto requestDto);

	/**
	 * delete user
	 *
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * delete multiple users
	 *
	 * @param ids
	 */
	void delete(List<Integer> ids);

}
