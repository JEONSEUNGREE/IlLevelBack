package com.trip.penguin.follow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trip.penguin.follow.service.AppFollowService;
import com.trip.penguin.interceptor.annotation.LoginUserCheck;
import com.trip.penguin.resolver.annotation.CurrentUser;
import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.response.JsonResponse;
import com.trip.penguin.follow.dto.UserFollowDTO;
import com.trip.penguin.follow.dto.UserFollowListDTO;

@RestController
@RequestMapping(value = "/follow")
public class AppFollowController {

	private AppFollowService appFollowService;

	@Autowired
	public AppFollowController(AppFollowService appFollowService) {
		this.appFollowService = appFollowService;
	}

	@LoginUserCheck
	@GetMapping("/add/{followId}")
	public JsonResponse<UserFollowDTO> userMyPageFollowAdd(@CurrentUser LoginUserInfo loginUserInfo,
		@PathVariable(value = "followId") Integer followId) {

		UserFollowDTO userFollowDTO = appFollowService.userMyPageFollowAdd(loginUserInfo, followId);

		return JsonResponse.success(userFollowDTO);
	}

	@LoginUserCheck
	@GetMapping("/list/{curPage}")
	public JsonResponse<UserFollowListDTO> userMyPageFollowList(@CurrentUser LoginUserInfo loginUserInfo,
		@PathVariable(value = "curPage") Integer curPage) {

		UserFollowListDTO userFollowListDTO = appFollowService.userMyPageFollowList(loginUserInfo, curPage - 1);

		return JsonResponse.success(userFollowListDTO);
	}

	@LoginUserCheck
	@GetMapping("/delete/{followId}")
	public JsonResponse<UserFollowListDTO> userMyPageDelete(@CurrentUser LoginUserInfo loginUserInfo,
		@PathVariable(value = "followId") Integer followId) {

		appFollowService.userMyPageFollowDelete(loginUserInfo, followId.longValue());

		return JsonResponse.success();
	}
}
