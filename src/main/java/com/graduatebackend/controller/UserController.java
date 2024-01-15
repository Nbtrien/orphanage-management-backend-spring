package com.graduatebackend.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.constant.Role;
import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.dto.authentication.RegisterRequestDto;
import com.graduatebackend.dto.user.UpdateUserRequestDto;
import com.graduatebackend.dto.user.UserDto;
import com.graduatebackend.service.UserService;
import com.graduatebackend.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping()
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> fetch(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		Integer roleId = requestParams.get("roleId") == null ? null : Integer.valueOf(requestParams.get("roleId"));
		Page<UserDto> userDtoPage = userService.fetch(pageRequest, keyword, roleId);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(userDtoPage)));
	}

	@GetMapping(params = "search")
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> search(@RequestParam(required = false) final Map<String, String> requestParams) {
		String key = requestParams.get("search");
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		Page<UserDto> userDtoPage = userService.search(pageRequest, key);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(userDtoPage)));
	}

	@GetMapping("/{id}")
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> getDetail(@PathVariable(value = "id") Integer id) {
		UserDto userDto = userService.getDetail(id);
		return ResponseEntity.ok(ResponseDto.ok(userDto));
	}

	@PostMapping()
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> addNew(@Valid @RequestBody RegisterRequestDto requestDto) {
		userService.addNew(requestDto);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> update(@PathVariable(value = "id") Integer id,
			@Valid @RequestBody UpdateUserRequestDto requestDto) {
		UserDto userDto = userService.update(id, requestDto);
		return ResponseEntity.ok(ResponseDto.ok(userDto));
	}

	@DeleteMapping("/{id}")
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Integer id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> deleteUsers(@RequestParam("ids") List<Integer> ids) {
		userService.delete(ids);
		return ResponseEntity.noContent().build();
	}
}
