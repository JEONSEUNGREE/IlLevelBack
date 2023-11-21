package com.trip.penguin.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.penguin.user.service.UserService;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Service
@Getter
@NoArgsConstructor
public class AbstractOAuth2UserService {

	private UserService userService;

	@Autowired
	public AbstractOAuth2UserService(UserService userService) {
		this.userService = userService;
	}

}
