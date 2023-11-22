package com.trip.penguin.oauth.converter;

import com.trip.penguin.constant.OAuthVendor;
import com.trip.penguin.oauth.model.DefaultUser;
import com.trip.penguin.oauth.model.ProviderUser;
import com.trip.penguin.user.domain.UserMS;

public class UserDetailsProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {

	@Override
	public ProviderUser converter(ProviderUserRequest providerUserRequest) {

		if (!providerUserRequest.clientRegistration()
			.getRegistrationId()
			.equals(OAuthVendor.DEFAULT.getOAuthVendor())) {
			return null;
		}
		UserMS userMS = providerUserRequest.userMS();

		return DefaultUser.builder()
			.username(userMS.getUserFirst())
			.userLast(userMS.getUserLast())
			.provider(OAuthVendor.DEFAULT.getOAuthVendor())
			.email(userMS.getUserEmail())
			.userFirst(userMS.getUserFirst())
			.password(userMS.getUserPwd())
			.build();
	}

}
