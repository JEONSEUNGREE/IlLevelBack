package com.trip.penguin.oauth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trip.penguin.oauth.converter.ProviderUserConverter;
import com.trip.penguin.oauth.converter.ProviderUserRequest;
import com.trip.penguin.oauth.model.PrincipalUser;
import com.trip.penguin.oauth.model.ProviderUser;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.service.UserService;

@Service
public class CustomUserDetailsService extends AbstractOAuth2UserService implements UserDetailsService {

	private UserService userService;

	public CustomUserDetailsService(OauthUserService oauthUserService,
		ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter, UserService userService) {
		super(oauthUserService, providerUserConverter);
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

		UserMS user = userService.getUserByUserEmail(userEmail);

		ProviderUserRequest providerUserRequest = new ProviderUserRequest(user);

		ProviderUser providerUser = providerUser(providerUserRequest);

		return new PrincipalUser(providerUser);
	}
}
