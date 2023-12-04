package com.trip.penguin.security.provider;

import com.trip.penguin.constant.CommonUserRole;
import com.trip.penguin.exception.UserNotFoundException;
import com.trip.penguin.account.service.CustomCompanyDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.account.service.CustomUserDetailsService;

public class JwtAuthenticationProvider implements AuthenticationProvider {
	private final CustomUserDetailsService customUserDetailsService;

	private final CustomCompanyDetailsService customCompanyDetailsService;

	private final BCryptPasswordEncoder passwordEncoder;

	public JwtAuthenticationProvider(CustomUserDetailsService customUserDetailsService,
									 CustomCompanyDetailsService customCompanyDetailsService,
									 BCryptPasswordEncoder passwordEncoder) {
		this.customUserDetailsService = customUserDetailsService;
		this.customCompanyDetailsService = customCompanyDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userEmail = authentication.getName();
		String password = (String)authentication.getCredentials();
		String authority = authentication.getAuthorities().stream().findFirst().orElseThrow(UserNotFoundException::new).getAuthority();

		UserDetails userDetails = null;

		switch (CommonUserRole.valueOf(authority.toUpperCase())) {
			case ROLE_USER ->
					userDetails = customUserDetailsService.loadUserByUsername(userEmail);
			case ROLE_COM ->
					userDetails = customCompanyDetailsService.loadUserByCompanyName(userEmail);
			default -> throw new UserNotFoundException();
		}

		if (userDetails == null) {
			throw new RuntimeException("USERNAME IS NOT FOUND. USERNAME=" + userEmail);
		}

		if (!CommonConstant.ACCOUNT_TOKEN.toString().equalsIgnoreCase(password) && !this.passwordEncoder.matches(
			password, userDetails.getPassword())) {
			throw new RuntimeException("PASSWORD IS NOT MATCHED");
		}

		return new UsernamePasswordAuthenticationToken(userEmail, password, userDetails.getAuthorities());

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}