package com.graduatebackend.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.constant.AccountCode;
import com.graduatebackend.constant.ChildrenCode;
import com.graduatebackend.dto.account.ActiveAccountRequestDto;
import com.graduatebackend.dto.account.AddNewAccountRequestDto;
import com.graduatebackend.dto.account.GetAccountDetailResponseDto;
import com.graduatebackend.dto.account.GetAccountResponseDto;
import com.graduatebackend.dto.account.VerifyAccountRequestDto;
import com.graduatebackend.dto.account.VerifyAccountResponseDto;
import com.graduatebackend.dto.adoption.GetAccountFistAdoptionResponseDto;
import com.graduatebackend.dto.authentication.AdminLoginRequestDto;
import com.graduatebackend.dto.authentication.LoginResponseDto;
import com.graduatebackend.dto.donation.GetAccountDonorResponseDto;
import com.graduatebackend.entity.Account;
import com.graduatebackend.entity.AccountPrincipal;
import com.graduatebackend.entity.Applicant;
import com.graduatebackend.entity.Token;
import com.graduatebackend.exception.EmailExistedException;
import com.graduatebackend.exception.ResourceExistedException;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.AccountMapper;
import com.graduatebackend.mapper.ApplicantMapper;
import com.graduatebackend.mapper.DonationMapper;
import com.graduatebackend.repository.AccountRepository;
import com.graduatebackend.repository.ApplicantRepository;
import com.graduatebackend.repository.TokenRepository;
import com.graduatebackend.service.AccountService;
import com.graduatebackend.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	private final AccountRepository accountRepository;
	private final TokenRepository tokenRepository;
	private final ApplicantRepository applicantRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Object authenticate(AdminLoginRequestDto requestDto) {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(authority);
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword(), authorities));
		Account account = accountRepository.loadByEmail(requestDto.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException(""));
		AccountPrincipal accountPrincipal = AccountPrincipal.create(account);
		String jwtToken = jwtService.generateToken(accountPrincipal);
		return LoginResponseDto.builder().accessToken(jwtToken).type("bearer").userId(account.getAccountId())
				.roles(authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())).build();
	}

	@Override
	@Transactional
	public Token applicantRegister(AddNewAccountRequestDto requestDto) {
		Applicant applicant = applicantRepository
				.findByApplicantMailAddressAndIsDeleteIsFalse(requestDto.getApplicantMailAddress())
				.orElseThrow(() -> new ResourceNotFoundException("Applicant not found!"));

		Account account = Account.builder().accountMailAddress(applicant.getApplicantMailAddress())
				.accountPassword(passwordEncoder.encode(requestDto.getAccountPassword())).applicant(applicant)
				.registerDateTime(new Timestamp(System.currentTimeMillis())).build();

		Account accountSaved = accountRepository.save(account);

		String tokenFromEmail = generateTokenFromEmail(account.getAccountMailAddress());
		if (tokenFromEmail == null) {
			throw new RuntimeException("Something went wrong");
		}

		Timestamp now = new Timestamp(System.currentTimeMillis());

		Token token = Token.builder().token(tokenFromEmail).account(accountSaved)
				.accountMailAddress(accountSaved.getAccountMailAddress()).expirationDateTimeStart(now)
				.expirationDateTimeEnd(new Timestamp(now.getTime() + (30L * 24 * 60 * 60 * 1000))).build();
		tokenRepository.save(token);
		return token;
	}

	@Override
	@Transactional
	public Token addNew(AddNewAccountRequestDto requestDto) {
		Optional<Account> accountCheck = accountRepository
				.findByAccountMailAddressAndIsDeleteIsFalse(requestDto.getApplicantMailAddress());

		if (accountCheck.isPresent()) {
			throw new EmailExistedException();
		}

		Optional<Applicant> applicantOptional = applicantRepository
				.findByApplicantMailAddressAndIsDeleteIsFalse(requestDto.getApplicantMailAddress());
		if (applicantOptional.isPresent()) {
			throw new ResourceExistedException("This email is used in adoption application form.", "408");
		}

		Account account = AccountMapper.INSTANCE.toEntity(requestDto);
		account.setAccountMailAddress(account.getApplicant().getApplicantMailAddress());

		Applicant applicant = account.getApplicant();
		applicant.setApplicantFullName(applicant.getApplicantLastName() + " " + applicant.getApplicantFirstName());
		Applicant applicantSaved = applicantRepository.save(applicant);

		account.setApplicant(applicantSaved);
		account.setRegisterDateTime(new Timestamp(System.currentTimeMillis()));
		account.setAccountPassword(passwordEncoder.encode(account.getAccountPassword()));
		Account accountSaved = accountRepository.save(account);

		String tokenFromEmail = generateTokenFromEmail(account.getAccountMailAddress());
		if (tokenFromEmail == null) {
			throw new RuntimeException("Something went wrong");
		}
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Token token = Token.builder().token(tokenFromEmail).account(accountSaved)
				.accountMailAddress(accountSaved.getAccountMailAddress()).expirationDateTimeStart(now)
				.expirationDateTimeEnd(new Timestamp(now.getTime() + (30L * 24 * 60 * 60 * 1000))).build();
		tokenRepository.save(token);

		return token;
	}

	@Override
	public VerifyAccountResponseDto verifyAccount(VerifyAccountRequestDto requestDto) {
		accountRepository.findByAccountMailAddressAndIsDeleteIsFalse(requestDto.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("Mail address not found!"));
		Optional<Token> tokenOptional = tokenRepository.findTokenValidByToken(requestDto.getToken());
		if (!tokenOptional.isPresent()) {
			throw new ResourceNotFoundException("Token not found!", "410");
		}
		Token token = tokenOptional.get();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		if (token.getExpirationDateTimeStart().after(now) && token.getExpirationDateTimeEnd().before(now)) {
			throw new ResourceNotFoundException("The token has expired.!", "411");
		}

		Account account = token.getAccount();

		if (account.getAccountStatus() != AccountCode.AccountStatus.NOT_AUTHENTICATED.getCode()) {
			throw new ResourceNotFoundException("You are already registered as a member. Please log in again.", "414");
		}

		return AccountMapper.INSTANCE.toVerifyAccountResponseDto(account);
	}

	@Override
	public void verifyApplicant(VerifyAccountRequestDto requestDto) {
		Optional<Token> tokenOptional = tokenRepository.findTokenValidByToken(requestDto.getToken(),
				requestDto.getEmail());
		if (!tokenOptional.isPresent()) {
			throw new ResourceNotFoundException("Token not found!", "410");
		}
		Token token = tokenOptional.get();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		if (token.getExpirationDateTimeStart().after(now) && token.getExpirationDateTimeEnd().before(now)) {
			throw new ResourceNotFoundException("The token has expired.", "411");
		}
		Account account = token.getAccount();

		if (account.getAccountStatus() != AccountCode.AccountStatus.NOT_AUTHENTICATED.getCode()) {
			throw new ResourceNotFoundException("You are already registered as a member. Please log in again.", "414");
		}

		account.setAccountStatus(AccountCode.AccountStatus.ACTIVE.getCode());
		account.setFirstLogin(true);

		accountRepository.save(account);
	}

	@Override
	@Transactional
	public Object activeAccount(ActiveAccountRequestDto requestDto) {
		Optional<Token> tokenOptional = tokenRepository.findTokenValidByToken(requestDto.getToken(),
				requestDto.getEmail());
		if (!tokenOptional.isPresent()) {
			throw new ResourceNotFoundException("Token not found!", "410");
		}
		Token token = tokenOptional.get();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		if (token.getExpirationDateTimeStart().after(now) && token.getExpirationDateTimeEnd().before(now)) {
			throw new ResourceNotFoundException("The token has expired.", "411");
		}
		Account account = token.getAccount();

		if (account.getAccountStatus() != AccountCode.AccountStatus.NOT_AUTHENTICATED.getCode()) {
			throw new ResourceNotFoundException("You are already registered as a member. Please log in again.", "414");
		}
		ApplicantMapper.INSTANCE.updateEntity(account.getApplicant(), requestDto.getApplicant());

		account.setAccountStatus(AccountCode.AccountStatus.ACTIVE.getCode());
		account.setFirstLogin(true);

		accountRepository.save(account);
		return null;
	}

	@Override
	public GetAccountDetailResponseDto getAccountDetail(Integer id) {
		Account account = accountRepository.findByAccountIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found."));
		GetAccountDetailResponseDto responseDto = AccountMapper.INSTANCE.toGetAccountDetailResponseDto(account);
		responseDto
				.setApplicantGender(ChildrenCode.Gender.of(account.getApplicant().getApplicantGender()).getDisplay());
		if (account.getVolunteer() != null)
			responseDto.setBiography(account.getVolunteer().getBiography());
		return responseDto;
	}

	@Override
	public GetAccountFistAdoptionResponseDto getAccountAdoptionInfo(Integer id) {
		Account account = accountRepository.findByAccountIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found."));
		GetAccountFistAdoptionResponseDto responseDto = ApplicantMapper.INSTANCE
				.toGetAccountFistAdoptionResponseDto(account.getApplicant());
		if (account.getApplicant().getAdoptionApplications().size() == 0) {
			responseDto.setFirstTime(true);
		} else {
			responseDto.setFirstTime(false);
			if (account.getApplicant().getSpouse() != null)
				responseDto.setSpouseResponseDto(
						ApplicantMapper.INSTANCE.toGetAccountSpouseResponseDto(account.getApplicant().getSpouse()));
		}
		return responseDto;
	}

	@Override
	public GetAccountDonorResponseDto getAccountDonorInfo(Integer id) {
		Account account = accountRepository.findByAccountIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found."));
		GetAccountDonorResponseDto responseDto;
		if (account.getDonor() != null) {
			responseDto = DonationMapper.INSTANCE.toGetAccountDonorResponseDto(account.getDonor());
			responseDto.setFirstTime(false);
		} else {
			responseDto = DonationMapper.INSTANCE.toGetAccountDonorResponseDto(account.getApplicant());
			responseDto.setFirstTime(true);
		}
		return responseDto;
	}

	@Override
	public Page<GetAccountResponseDto> fetchAccounts(PageRequest pageRequest, String keyword, Integer accountType,
			Integer accountStatus) {
		Page<Account> accounts = accountRepository.findAll(pageRequest, keyword, accountType, accountStatus);
		Page<GetAccountResponseDto> responseDtoPage = accounts.map(account -> {
			GetAccountResponseDto responseDto = GetAccountResponseDto.builder().accountId(account.getAccountId())
					.accountMailAddress(account.getAccountMailAddress())
					.applicantFullName(account.getApplicant().getApplicantFullName())
					.applicantPhoneNumber(account.getApplicant().getApplicantPhoneNumber())
					.registerDateTime(account.getRegisterDateTime()).accountStatus(account.getAccountStatus()).build();
			if (account.getVolunteer() != null)
				responseDto.setVolunteerId(account.getVolunteer().getVolunteerId());
			if (account.getDonor() != null)
				responseDto.setDonorId(account.getDonor().getDonorId());
			return responseDto;
		});
		return responseDtoPage;
	}

	@Override
	public void updateAccountStatus(Integer accountId, Integer status) {
		accountRepository.updateAccountStatus(accountId, status);
	}

	private String generateTokenFromEmail(String email) {
		try {
			String uniqueString = email + System.currentTimeMillis();
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(uniqueString.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
