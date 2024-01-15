package com.graduatebackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.graduatebackend.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {
	/**
	 * find token valid
	 *
	 * @param token
	 * @return
	 */
	@Query("SELECT t FROM Token t WHERE t.token = :token AND t.isDelete IS FALSE")
	Optional<Token> findTokenValidByToken(@Param("token") String token);

	/**
	 * find token valid
	 *
	 * @param token
	 * @return
	 */
	@Query("SELECT t FROM Token t WHERE t.token = :token AND t.accountMailAddress = :mailAddress AND t.isDelete IS "
			+ "FALSE")
	Optional<Token> findTokenValidByToken(@Param("token") String token, @Param("mailAddress") String mailAddress);
}
