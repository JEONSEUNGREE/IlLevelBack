package com.trip.penguin.account.converter;

import com.trip.penguin.company.domain.CompanyMS;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.trip.penguin.user.domain.UserMS;

public record ProviderUserRequest(ClientRegistration clientRegistration, OAuth2User oAuth2User, UserMS userMS, CompanyMS companyMS) {

	public ProviderUserRequest(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
		this(clientRegistration, oAuth2User, null, null);
	}

	public ProviderUserRequest(UserMS userMS) {
		this(null, null, userMS, null);
	}

	public ProviderUserRequest(CompanyMS companyMS) {
		this(null, null, null, companyMS);
	}
}
