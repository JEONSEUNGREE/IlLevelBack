package com.trip.penguin.oauth.converter;

import com.trip.penguin.constant.OAuthVendor;
import com.trip.penguin.oauth.model.KakaoUser;
import com.trip.penguin.oauth.model.ProviderUser;
import com.trip.penguin.oauth.util.OAuth2Utils;

public class OAuth2KakaoProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {
	@Override
	public ProviderUser converter(ProviderUserRequest providerUserRequest) {

		if (!providerUserRequest.clientRegistration()
			.getRegistrationId()
			.equals(OAuthVendor.KAKAO.getOAuthVendor())) {
			return null;
		}
		return new KakaoUser(
			OAuth2Utils.getOtherAttributes(providerUserRequest.oAuth2User(), "kakao_account", "profile"),
			providerUserRequest.oAuth2User(),
			providerUserRequest.clientRegistration());
	}
}
