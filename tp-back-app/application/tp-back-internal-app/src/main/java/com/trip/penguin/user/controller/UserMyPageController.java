package com.trip.penguin.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trip.penguin.interceptor.annotation.LoginUserCheck;
import com.trip.penguin.resolver.annotation.CurrentUser;
import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.response.JsonResponse;
import com.trip.penguin.user.dto.UserMyPageDTO;
import com.trip.penguin.user.service.UserMyPageService;
import com.trip.penguin.user.view.UserMyPageProfileDTO;
import com.trip.penguin.user.view.UserMyPageView;

@RestController
@RequestMapping(value = "/usr/mypage")
public class UserMyPageController {

	private final UserMyPageService userMyPageService;

	@Autowired
	public UserMyPageController(UserMyPageService userMyPageService) {
		this.userMyPageService = userMyPageService;
	}

	@LoginUserCheck
	@PostMapping("/modify")
	public JsonResponse<UserMyPageDTO> userMyPageModify(@CurrentUser LoginUserInfo loginUserInfo,
		@RequestPart(value = "userMyPageView") UserMyPageView userMyPageView,
		@RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile) throws
		IllegalAccessException {

		UserMyPageDTO userMyPageDto = userMyPageService.userMyPageModify(loginUserInfo, userMyPageView, multipartFile);

		return JsonResponse.success(userMyPageDto);
	}

	@LoginUserCheck
	@PostMapping("/profile")
	public JsonResponse<UserMyPageProfileDTO> userMyPageProfile(@CurrentUser LoginUserInfo loginUserInfo) throws
		IllegalAccessException {

		UserMyPageProfileDTO userMyPageProfile = userMyPageService.getUserMyPageProfile(loginUserInfo);

		return JsonResponse.success(userMyPageProfile);
	}
}
