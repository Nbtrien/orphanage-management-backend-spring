package com.graduatebackend.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduatebackend.dto.ErrorDto;
import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserDetailsService userDetailsService;

	@Qualifier("accountDetailsService")
	@Autowired
	UserDetailsService accountDetailsService;

	private static final String[] EXCLUDED_URLS = new String[] { "/api/admin/login", "/api/admin/register",
			"/api/address/**", "/api/provinces", "/api/adoption-application", "/api/files", "/api/register",
			"/api/account/verify", "/api/applicant/register", "/api/applicant/account/verify", "/api/login",
			"/api/pdf/**", "/api/donations", "/api/donations/callback", "/api/donations/monthly", "/api/donors",
			"/api/donors/{id}/donations", "/api/donation-purposes", "/api/donation-programs/**", "/api/families/**",
			"/api/donations/families", "/api/volunteers/events/**", "/api/volunteers/event-apply",
			"/api/article-categories/**", "/api/articles/**", "/api/content/faq", "/api/content/**",
			"/api/content/website-contact", "/api/content/statistics", "/api/chat-bot/response",
			"/api/dialogflow-webhook" };

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String origin = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", origin);
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS, PATCH");
		response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, "
				+ "X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, "
				+ "Authorization");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		if (request.getMethod().equals("OPTIONS")) {
			filterChain.doFilter(request, response);
			return;
		}

		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			new ObjectMapper().writeValue(response.getOutputStream(),
					ResponseDto.fail(401, new ErrorDto("401", "UNAUTHORIZED")));
			return;
		}
		try {
			String token = authHeader.substring(7);
			String email = jwtService.extractUsername(token);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (email != null && (SecurityContextHolder.getContext().getAuthentication() == null
					|| (authentication instanceof AnonymousAuthenticationToken))) {
				UserDetails userDetails = null;
				if (jwtService.extractRole(token).contains("ROLE_ADMIN")) {
					userDetails = userDetailsService.loadUserByUsername(email);
				} else if (jwtService.extractRole(token).contains("ROLE_USER")) {
					userDetails = accountDetailsService.loadUserByUsername(email);
				}
				if (jwtService.isTokenValid(token, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				} else {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(),
							ResponseDto.fail(401, new ErrorDto("401", "UNAUTHORIZED")));
					return;
				}
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			log.info(e.getMessage());
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			new ObjectMapper().writeValue(response.getOutputStream(),
					ResponseDto.fail(401, new ErrorDto("401", "UNAUTHORIZED")));
			return;
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return Arrays.stream(EXCLUDED_URLS).anyMatch(e -> new AntPathMatcher().match(e, request.getServletPath()));
	}
}
