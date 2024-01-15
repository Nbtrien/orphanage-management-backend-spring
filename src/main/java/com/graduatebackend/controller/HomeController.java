package com.graduatebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.constant.Role;
import com.graduatebackend.repository.UserRepository;

@RestController
public class HomeController {
	@Autowired
	UserRepository userRepository;

	@GetMapping("/test")
	public ResponseEntity<?> test() {
		return ResponseEntity.ok(userRepository.findById(1));
	}

	@GetMapping("/api/user-role")
	@Secured({ Role.Code.USER })
	public ResponseEntity<?> allRole() {
		return ResponseEntity.ok("user role");
	}

	@GetMapping("/api/admin-role")
	@Secured({ Role.Code.ADMIN })
	public ResponseEntity<?> adminRole() {
		return ResponseEntity.ok("admin role");
	}

	@GetMapping("/api/super-role")
	@Secured({ Role.Code.SUPER_ADMIN })
	public ResponseEntity<?> superRole() {
		return ResponseEntity.ok("super role");
	}
}
