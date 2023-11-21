package com.trip.penguin.oauth.converter;

import com.trip.penguin.constant.OAuthVendor;
import com.trip.penguin.oauth.model.GoogleUser;
import com.trip.penguin.oauth.model.ProviderUser;
import com.trip.penguin.oauth.util.OAuth2Utils;

public class OAuth2GoogleProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {

	@Override
	public ProviderUser converter(ProviderUserRequest providerUserRequest) {

		if (!providerUserRequest.clientRegistration().getRegistrationId().equals(OAuthVendor.GOOGLE.getOAuthVendor())) {
			return null;
		}
		return new GoogleUser(
			OAuth2Utils.getMainAttributes(providerUserRequest.oAuth2User()),
			providerUserRequest.oAuth2User(),
			providerUserRequest.clientRegistration());
	}

}