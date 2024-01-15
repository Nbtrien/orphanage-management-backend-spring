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
import com.graduatebackend.dto.user.AddNewRoleDto;
import com.graduatebackend.dto.user.RoleDetailDto;
import com.graduatebackend.service.RoleService;
import com.graduatebackend.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/roles")
@CrossOrigin
@RequiredArgsConstructor
public class RoleController {
	private final RoleService roleService;

	@GetMapping(value = "/get-all")
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> fetchAll() {
		return ResponseEntity.ok(ResponseDto.ok(roleService.fetchAll()));
	}

	@GetMapping(value = "")
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> fetch(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		Integer permissionId = requestParams.get("permissionId") == null ? null
				: Integer.valueOf(requestParams.get("permissionId"));
		Page<RoleDetailDto> roleDtoPage = roleService.fetch(pageRequest, keyword, permissionId);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(roleDtoPage)));
	}

	@GetMapping(params = "search")
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> search(@RequestParam(required = false) final Map<String, String> requestParams) {
		String key = requestParams.get("search");
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		Page<RoleDetailDto> roleDtoPage = roleService.search(pageRequest, key);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(roleDtoPage)));
	}

	@GetMapping("/{id}")
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> getDetail(@PathVariable(value = "id") Integer id) {
		RoleDetailDto roleDto = roleService.getDetail(id);
		return ResponseEntity.ok(ResponseDto.ok(roleDto));
	}

	@PostMapping
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> addNew(@Valid @RequestBody AddNewRoleDto requestDto) {
		roleService.addNew(requestDto);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> update(@PathVariable(value = "id") Integer id,
			@Valid @RequestBody AddNewRoleDto requestDto) {
		RoleDetailDto roleDto = roleService.update(id, requestDto);
		return ResponseEntity.ok(ResponseDto.ok(roleDto));
	}

	@DeleteMapping
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> deleteRoles(@RequestParam("ids") List<Integer> ids) {
		roleService.delete(ids);
		return ResponseEntity.noContent().build();
	}
}
