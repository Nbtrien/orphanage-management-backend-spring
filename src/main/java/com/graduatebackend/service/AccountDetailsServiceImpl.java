package com.graduatebackend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.graduatebackend.entity.Account;
import com.graduatebackend.entity.AccountPrincipal;
import com.graduatebackend.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Component
@Service("accountDetailsService")
@RequiredArgsConstructor
public class AccountDetailsServiceImpl implements UserDetailsService {
	private final AccountRepository accountRepository;

	/**
	 * load accountDetail by email
	 *
	 * @param username the username identifying the user whose data is required.
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.loadByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Your email address or password is incorrect."));
		return AccountPrincipal.create(account);
	}
}
