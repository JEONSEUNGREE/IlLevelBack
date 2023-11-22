package com.trip.penguin.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;

import com.trip.penguin.oauth.converter.ProviderUserConverter;
import com.trip.penguin.oauth.converter.ProviderUserRequest;
import com.trip.penguin.oauth.model.ProviderUser;
import com.trip.penguin.user.domain.UserMS;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class AbstractOAuth2UserService {

	@Autowired
	private OauthUserService oauthUserService;

	@Autowired
	private ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter;

	protected ProviderUser providerUser(ProviderUserRequest providerUserRequest) {

		return providerUserConverter.converter(providerUserRequest);
	}

	protected void register(ProviderUser providerUser, OAuth2UserRequest userRequest) {

		if (oauthUserService.getUserByUserEmail(providerUser.getEmail()).isEmpty()) {
			oauthUserService.signUpUser(userRequest.getClientRegistration().getRegistrationId(), providerUser);
		} else {
			System.out.println("user = " + "이미 가입된 유저 ");
		}
	}
}