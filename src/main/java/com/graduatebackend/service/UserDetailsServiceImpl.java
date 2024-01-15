package com.graduatebackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.graduatebackend.entity.User;
import com.graduatebackend.entity.UserPrincipal;
import com.graduatebackend.repository.UserRepository;

@Component
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	/**
	 * load user principal by email
	 * 
	 * @param username the username identifying the user whose data is required.
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserMailAddressAndIsDeleteIsFalse(username)
				.orElseThrow(() -> new UsernameNotFoundException("Your email address or password is incorrect."));
		return UserPrincipal.create(user);
	}
}
