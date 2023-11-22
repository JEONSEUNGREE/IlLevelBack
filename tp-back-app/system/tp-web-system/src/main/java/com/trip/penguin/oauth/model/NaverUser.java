package com.trip.penguin.oauth.model;

import com.trip.penguin.constant.OAuthVendor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class NaverUser extends OAuth2ProviderUser {

	public NaverUser(Attributes attributes, OAuth2User oAuth2User, ClientRegistration clientRegistration) {
		// 네이버는 response 한 뎁스가 더 있음
		super(attributes.getSubAttributes(), oAuth2User, clientRegistration);
	}

	@Override
	public String getId() {
		// 식별자
		return (String)getAttributes().get("id");
	}

	@Override
	public String getUsername() {
		// 실제 아이디
		return (String)getAttributes().get("name");
	}

	@Override
	public String getFirstName() {
		return null;
	}

	@Override
	public String getLastName() {
		return null;
	}

	@Override
	public String getPicture() {
		return (String)getAttributes().get("profile_image");
	}

	@Override
	public String provider() {
		return OAuthVendor.NAVER.getOAuthVendor();
	}

}
