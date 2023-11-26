package com.trip.penguin.user.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.penguin.oauth.service.DefaultUserService;
import com.trip.penguin.resolver.vo.LoginInfo;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.dto.UserMyPageDto;
import com.trip.penguin.user.view.UserMyPageView;

@Service
public class UserMyPageServiceImpl implements UserMyPageService {

	private UserService userService;

	private DefaultUserService defaultUserService;

	@Autowired
	public UserMyPageServiceImpl(UserService userService, DefaultUserService defaultUserService) {
		this.userService = userService;
		this.defaultUserService = defaultUserService;
	}

	@Override
	public UserMyPageDto userMyPageModify(LoginInfo loginInfo, UserMyPageView userMyPageView) throws
		IllegalAccessException {
		// 여러 모듈에 의존 하여 좋지 못한 방법이라고 생각됨

		// 도메인모듈에서 조회
		UserMS userMS = userService.getUserByUserEmail(loginInfo.getUserEmail())
			.orElseThrow(IllegalAccessException::new);

		// webSystem 모듈에서 패스워드 인코딩 후 업데이트
		BeanUtils.copyProperties(userMyPageView, userMS);
		UserMS updatedUser = defaultUserService.modifyUser(userMS);

		// 리턴 객체 생성
		UserMyPageDto userMyPageDto = new UserMyPageDto();
		BeanUtils.copyProperties(updatedUser, userMyPageDto);

		return userMyPageDto;
	}
}
