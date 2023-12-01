package com.trip.penguin.account.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.trip.penguin.account.converter.ProviderUserRequest;
import com.trip.penguin.account.model.PrincipalUser;
import com.trip.penguin.account.model.ProviderUser;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService extends AbstractOAuth2UserService implements UserDetailsService {

	private final UserService userService;

	/**
	 * loadByUserName은 로그인시에만 provider에서 사용
	 * @param userEmail - 이메일 정보
	 * @return PrincipalUser - 인증 객체
	 * @throws UsernameNotFoundException - 없는 유저 예외 처리
	 */
	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

		UserMS user = userService.getUserByUserEmail(userEmail).orElse(null);

		if (user != null && user.isOAuthUser()) {
			return null;
		}

		ProviderUserRequest providerUserRequest = new ProviderUserRequest(user);

		ProviderUser providerUser = providerUser(providerUserRequest);

		return new PrincipalUser(providerUser);
	}
}
