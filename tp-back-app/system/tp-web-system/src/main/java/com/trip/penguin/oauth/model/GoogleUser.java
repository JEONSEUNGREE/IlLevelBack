package com.trip.penguin.oauth.model;

import com.trip.penguin.constant.OAuthVendor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleUser extends OAuth2ProviderUser {

	public GoogleUser(Attributes mainAttributes, OAuth2User oAuth2User, ClientRegistration clientRegistration) {
		super(mainAttributes.getMainAttributes(), oAuth2User, clientRegistration);
	}

	@Override
	public String getId() {
		// 식별자
		return (String)getAttributes().get("sub");
	}

	@Override
	public String getUsername() {
		// 실제 아이디
		return (String)getAttributes().get("sub");
	}

	@Override
	public String getLastName() {
		return (String)getAttributes().get("family_name");
	}

	@Override
	public String getFirstName() {
		return (String)getAttributes().get("given_name");
	}

	@Override
	public String getPicture() {
		return (String)getAttributes().get("picture");
	}

	@Override
	public String provider() {
		return OAuthVendor.GOOGLE.getOAuthVendor();
	}
}
