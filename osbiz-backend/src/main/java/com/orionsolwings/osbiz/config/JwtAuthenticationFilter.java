package com.orionsolwings.osbiz.config;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.orionsolwings.osbiz.userManagement.model.User;
import com.orionsolwings.osbiz.userManagement.service.UserManagementService;
import com.orionsolwings.osbiz.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	private final UserManagementService userManagementService;

	public JwtAuthenticationFilter(UserManagementService userManagementService) {
		this.userManagementService = userManagementService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		logger.info("Incoming Request URI: {}", request.getRequestURI());

		// Debug: log all headers
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames != null && headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			logger.debug("Header: {} = {}", headerName, request.getHeader(headerName));
		}

		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);

			try {
				if (JwtUtil.validateToken(token)) {
					// Extract subject (email) from token
					String emailOrUsername = JwtUtil.extractEmail(token);

					// Fetch user from DB using username or email
					User user = userManagementService.getUserByUsernameOrEmail(emailOrUsername);

					if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
						logger.debug("JWT valid for user: {}", emailOrUsername);

						UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user,
								null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole())));

						authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

						// Set authentication in the context
						SecurityContextHolder.getContext().setAuthentication(authToken);
					} else if (user == null) {
						logger.warn("No user found for JWT subject: {}", emailOrUsername);
					}

				} else {
					logger.warn("Invalid or expired JWT token");
					sendUnauthorized(response, "Invalid or expired JWT token");
					return;
				}

			} catch (Exception e) {
				logger.error("JWT authentication failed", e);
				sendUnauthorized(response, "JWT authentication failed: " + e.getMessage());
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

	private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(message);
	}
}
