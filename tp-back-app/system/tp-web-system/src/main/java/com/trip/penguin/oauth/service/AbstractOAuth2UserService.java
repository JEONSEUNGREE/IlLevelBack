package com.trip.penguin.oauth.service;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;

import com.trip.penguin.oauth.converter.ProviderUserConverter;
import com.trip.penguin.oauth.converter.ProviderUserRequest;
import com.trip.penguin.oauth.model.ProviderUser;
import com.trip.penguin.user.domain.UserMS;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service
@Getter
@RequiredArgsConstructor
public class AbstractOAuth2UserService {

	private final OauthUserService oauthUserService;

	private final ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter;

	protected ProviderUser providerUser(ProviderUserRequest providerUserRequest) {

		return providerUserConverter.converter(providerUserRequest);
	}

	protected void register(ProviderUser providerUser, OAuth2UserRequest userRequest) {

		UserMS userMS = oauthUserService.getUserByUserEmail(providerUser.getEmail());

		if (userMS == null) {
			oauthUserService.signUpUser(userRequest.getClientRegistration().getRegistrationId(), providerUser);
		} else {
			System.out.println("user = " + userMS);
		}

	}
}