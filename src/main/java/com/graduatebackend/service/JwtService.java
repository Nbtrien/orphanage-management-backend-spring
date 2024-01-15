package com.graduatebackend.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	private String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
	private long jwtExpiration = 864000000;

	private long refreshExpiration = 86400000;

	/**
	 * get username by token
	 *
	 * @param token
	 * @return
	 */
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * get roles from token
	 *
	 * @param token
	 * @return
	 */
	public List<String> extractRole(String token) {
		final Claims claims = extractAllClaims(token);
		return (List<String>) claims.get("roles");
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * generate new token
	 *
	 * @param userDetails
	 * @return
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> extraClaims = new HashMap<>();
		List<String> roles = userDetails.getAuthorities().stream().map(a -> a.getAuthority())
				.collect(Collectors.toList());
		extraClaims.put("roles", roles);
		return generateToken(extraClaims, userDetails);
	}

	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return buildToken(extraClaims, userDetails, jwtExpiration);
	}

	public String generateRefreshToken(UserDetails userDetails) {
		return buildToken(new HashMap<>(), userDetails, refreshExpiration);
	}

	private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration * 10))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}

	/**
	 * check token is valid
	 *
	 * @param token
	 * @param userDetails
	 * @return
	 */
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	/**
	 * check token is expired
	 *
	 * @param token
	 * @return
	 */
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/**
	 * get token expired date
	 *
	 * @param token
	 * @return
	 */
	public Date extractExpiration(String token) {
		return (Date) extractClaim(token, Claims::getExpiration);
	}

	/**
	 * get token issue date
	 *
	 * @param token
	 * @return
	 */
	public Date extractIssuedAt(String token) {
		return extractClaim(token, Claims::getIssuedAt);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
