package com.trip.penguin.oauth.service;

import com.trip.penguin.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.penguin.oauth.model.ProviderUser;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.service.UserService;

import lombok.NoArgsConstructor;

import java.util.Optional;

@Service
@NoArgsConstructor
public class OauthUserService {

	@Autowired
	private UserService userService;

	@Autowired
	public OauthUserService(UserService userService) {
		this.userService = userService;
	}

	public void signUpUser(String registrationId, ProviderUser providerUser) {
		userService.signUpUser(UserMS.builder()
				.offYn(CommonConstant.N.getName())
				.userCity(null)
				.userImg(providerUser.getPicture())
				.userEmail(providerUser.getEmail())
				.userRole(providerUser.getAuthorities().get(0).getAuthority())
				.userNick(registrationId)
				.socialProvider(providerUser.getProvider())
				.socialProviderId(providerUser.getEmail())
				.userFirst(providerUser.getFirstName() == null ? providerUser.getUsername() : providerUser.getLastName())
				.userLast(providerUser.getLastName())
				.build()
		);
	}

	public Optional<UserMS> getUserByUserEmail(String userEmail) {
		return userService.getUserByUserEmail(userEmail);
	}
}
