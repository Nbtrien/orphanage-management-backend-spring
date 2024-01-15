package com.graduatebackend.service.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.dto.authentication.AdminLoginRequestDto;
import com.graduatebackend.dto.authentication.LoginResponseDto;
import com.graduatebackend.dto.authentication.RegisterRequestDto;
import com.graduatebackend.entity.Role;
import com.graduatebackend.entity.User;
import com.graduatebackend.entity.UserPrincipal;
import com.graduatebackend.exception.EmailExistedException;
import com.graduatebackend.exception.UserExistsException;
import com.graduatebackend.repository.RoleRepository;
import com.graduatebackend.repository.UserRepository;
import com.graduatebackend.service.AuthenticationService;
import com.graduatebackend.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	private final JwtService jwtService;

	@Override
	public ResponseDto<?> authenticateAdmin(AdminLoginRequestDto loginRequestDto) {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(authority);
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),
				loginRequestDto.getPassword(), authorities));
		User user = userRepository.findByUserMailAddressAndIsDeleteIsFalse(loginRequestDto.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException(""));
		UserPrincipal userPrincipal = UserPrincipal.create(user);
		String jwtToken = jwtService.generateToken(userPrincipal);
		LoginResponseDto loginResponseDto = LoginResponseDto.builder().accessToken(jwtToken).type("bearer")
				.userId(user.getUserId())
				.roles(user.getRoles().stream().map(Role::getRoleTitle).collect(Collectors.toList())).build();
		return ResponseDto.ok(loginResponseDto);
	}

	@Override
	public ResponseDto<?> registerAdmin(RegisterRequestDto registerRequestDto) {
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

		User user = User.builder().userName(registerRequestDto.getUserName())
				.userMailAddress(registerRequestDto.getEmail())
				.userPassword(passwordEncoder.encode(registerRequestDto.getPassword())).roles(roles).build();

		userRepository.saveAndFlush(user);
		return authenticateAdmin(AdminLoginRequestDto.builder().email(registerRequestDto.getEmail())
				.password(registerRequestDto.getPassword()).build());

	}
}
