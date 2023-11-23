package com.trip.penguin.security.provider;

import com.trip.penguin.oauth.service.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    public JwtAuthenticationProvider(CustomUserDetailsService customUserDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userEmail = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

            if (userDetails == null) {
                throw new RuntimeException("USERNAME IS NOT FOUND. USERNAME=" + userEmail);
            }

            if (!this.passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new RuntimeException("PASSWORD IS NOT MATCHED");
            }

        return new UsernamePasswordAuthenticationToken(userEmail, password, userDetails.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}