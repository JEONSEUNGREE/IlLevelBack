package com.trip.penguin.oauth.converter;

import com.trip.penguin.constant.OAuthVendor;
import com.trip.penguin.oauth.model.NaverUser;
import com.trip.penguin.oauth.model.ProviderUser;
import com.trip.penguin.oauth.util.OAuth2Utils;

public class OAuth2NaverProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {

	@Override
	public ProviderUser converter(ProviderUserRequest providerUserRequest) {

		if (!providerUserRequest.clientRegistration().getRegistrationId().equals(OAuthVendor.NAVER.getOAuthVendor())) {
			return null;
		}
		return new NaverUser(
			OAuth2Utils.getSubAttributes(providerUserRequest.oAuth2User(), "response"),
			providerUserRequest.oAuth2User(),
			providerUserRequest.clientRegistration());
	}
}
