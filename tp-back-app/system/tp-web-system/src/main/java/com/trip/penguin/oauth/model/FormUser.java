package com.trip.penguin.oauth.model;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormUser implements ProviderUser {

	private String id;

	private String username;

	private String password;

	private String email;

	private String provider;

	private List<? extends GrantedAuthority> authorities;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
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
	public String getPassword() {
		return password;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public String getProvider() {
		return provider;
	}

	@Override
	public String getPicture() {
		return null;
	}

	@Override
	public String provider() {
		return null;
	}

	@Override
	public List<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public OAuth2User getOAuth2User() {
		return null;
	}
}
