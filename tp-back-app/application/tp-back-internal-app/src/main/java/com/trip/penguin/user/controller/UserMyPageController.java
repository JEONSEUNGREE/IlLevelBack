package com.trip.penguin.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trip.penguin.interceptor.annotation.LoginCheck;
import com.trip.penguin.resolver.annotation.CurrentUser;
import com.trip.penguin.resolver.vo.LoginInfo;
import com.trip.penguin.response.JsonResponse;
import com.trip.penguin.user.dto.UserMyPageDTO;
import com.trip.penguin.user.service.UserMyPageService;
import com.trip.penguin.user.view.UserMyPageView;

@RestController
@RequestMapping(value = "/usr/mypage")
public class UserMyPageController {

	private final UserMyPageService userMyPageService;

	@Autowired
	public UserMyPageController(UserMyPageService userMyPageService) {
		this.userMyPageService = userMyPageService;
	}

	@LoginCheck
	@PostMapping("/modify")
	public JsonResponse<UserMyPageDTO> userMyPageModify(@CurrentUser LoginInfo loginInfo,
		@RequestPart(value = "userMyPageView") UserMyPageView userMyPageView,
		@RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile) throws
		IllegalAccessException {

		UserMyPageDTO userMyPageDto = userMyPageService.userMyPageModify(loginInfo, userMyPageView, multipartFile);

		return JsonResponse.success(userMyPageDto);
	}
}
