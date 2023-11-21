package com.trip.penguin.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.penguin.oauth.model.ProviderUser;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.service.UserService;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class OauthUserService {
	private UserService userService;

	@Autowired
	public OauthUserService(UserService userService) {
		this.userService = userService;
	}

	public void signUpUser(String registrationId, ProviderUser providerUser) {
		userService.signUpUser(UserMS.builder()
			.offYn("N")
			.userCity("NotSet")
			.userImg("default")
			.userEmail(providerUser.getId())
			.userRole(providerUser.getAuthorities().get(0).getAuthority())
			.userNick(registrationId)
			.userFirst(providerUser.getUsername())
			.userLast(providerUser.getUsername())
			.build()
		);
	}

	public UserMS getUserByUserEmail(String userEmail) {
		return userService.getUserByUserEmail(userEmail);
	}
}
