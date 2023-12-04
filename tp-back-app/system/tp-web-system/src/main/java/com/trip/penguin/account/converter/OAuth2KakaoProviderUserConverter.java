package com.trip.penguin.account.converter;

import com.trip.penguin.constant.OAuthVendor;
import com.trip.penguin.account.model.KakaoUser;
import com.trip.penguin.account.model.ProviderUser;
import com.trip.penguin.account.util.OAuth2Utils;

public class OAuth2KakaoProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {
	@Override
	public ProviderUser converter(ProviderUserRequest providerUserRequest) {

		if (!OAuthVendor.KAKAO.getOAuthVendor().equals(providerUserRequest.clientRegistration().getRegistrationId())) {
			return null;
		}
		return new KakaoUser(
			OAuth2Utils.getOtherAttributes(providerUserRequest.oAuth2User(), "kakao_account", "profile"),
			providerUserRequest.oAuth2User(),
			providerUserRequest.clientRegistration());
	}
}
