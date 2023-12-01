package com.trip.penguin.account.model;

import java.util.Map;

import com.trip.penguin.constant.OAuthVendor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class KakaoUser extends OAuth2ProviderUser {

	private Map<String, Object> otherAttributes;

	public KakaoUser(Attributes attributes, OAuth2User oAuth2User, ClientRegistration clientRegistration) {
		super(attributes.getSubAttributes(), oAuth2User, clientRegistration);
		this.otherAttributes = attributes.getOtherAttributes();
	}

	@Override
	public String getId() {
		return (String)getAttributes().get("id");
	}

	@Override
	public String getUsername() {
		return (String)otherAttributes.get("nickname");
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
		return (String)otherAttributes.get("profile_image_url");
	}

	@Override
	public String provider() {
		return OAuthVendor.KAKAO.getOAuthVendor();
	}

}
