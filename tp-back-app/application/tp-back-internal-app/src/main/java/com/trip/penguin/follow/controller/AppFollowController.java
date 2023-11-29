package com.trip.penguin.follow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trip.penguin.follow.service.AppFollowService;
import com.trip.penguin.interceptor.annotation.LoginCheck;
import com.trip.penguin.resolver.annotation.CurrentUser;
import com.trip.penguin.resolver.vo.LoginInfo;
import com.trip.penguin.response.JsonResponse;
import com.trip.penguin.user.dto.UserFollowDTO;
import com.trip.penguin.user.dto.UserFollowListDTO;

@RestController
@RequestMapping(value = "/follow")
public class AppFollowController {

	private AppFollowService appFollowService;

	@Autowired
	public AppFollowController(AppFollowService appFollowService) {
		this.appFollowService = appFollowService;
	}

	@LoginCheck
	@GetMapping("/add/{followId}")
	public JsonResponse<UserFollowDTO> userMyPageFollowAdd(@CurrentUser LoginInfo loginInfo,
		@PathVariable(value = "followId") Integer followId) {

		UserFollowDTO userFollowDTO = appFollowService.userMyPageFollowAdd(loginInfo, followId);

		return JsonResponse.success(userFollowDTO);
	}

	@LoginCheck
	@GetMapping("/list/{curPage}")
	public JsonResponse<UserFollowListDTO> userMyPageFollowList(@CurrentUser LoginInfo loginInfo,
		@PathVariable(value = "curPage") Integer curPage) {

		UserFollowListDTO userFollowListDTO = appFollowService.userMyPageFollowList(loginInfo, curPage - 1);

		return JsonResponse.success(userFollowListDTO);
	}

	@LoginCheck
	@GetMapping("/delete/{followId}")
	public JsonResponse<UserFollowListDTO> userMyPageDelete(@CurrentUser LoginInfo loginInfo,
		@PathVariable(value = "followId") Integer followId) {

		appFollowService.userMyPageFollowDelete(loginInfo, followId.longValue());

		return JsonResponse.success();
	}
}
