package com.trip.penguin.account.converter;

import com.trip.penguin.constant.OAuthVendor;
import com.trip.penguin.account.model.NaverUser;
import com.trip.penguin.account.model.ProviderUser;
import com.trip.penguin.account.util.OAuth2Utils;

public class OAuth2NaverProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {

	@Override
	public ProviderUser converter(ProviderUserRequest providerUserRequest) {

		if (!(OAuthVendor.NAVER.getOAuthVendor().equals(providerUserRequest.clientRegistration().getRegistrationId()))) {
			return null;
		}
		return new NaverUser(
			OAuth2Utils.getSubAttributes(providerUserRequest.oAuth2User(), "response"),
			providerUserRequest.oAuth2User(),
			providerUserRequest.clientRegistration());
	}
}
