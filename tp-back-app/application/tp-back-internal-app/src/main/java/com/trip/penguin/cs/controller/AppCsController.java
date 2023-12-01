package com.trip.penguin.cs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trip.penguin.cs.dto.UserCsqDetailDTO;
import com.trip.penguin.cs.dto.UserCsqPageDTO;
import com.trip.penguin.cs.service.AppCsService;
import com.trip.penguin.cs.view.UserCsqView;
import com.trip.penguin.exception.UserNotFoundException;
import com.trip.penguin.interceptor.annotation.LoginUserCheck;
import com.trip.penguin.resolver.annotation.CurrentUser;
import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.response.JsonResponse;

@RestController
@RequestMapping(value = "/cs")
public class AppCsController {

	private final AppCsService appCsService;

	@Autowired
	public AppCsController(AppCsService appCsService) {
		this.appCsService = appCsService;
	}

	@LoginUserCheck
	@PostMapping("/create")
	public JsonResponse<UserCsqDetailDTO> userMyPageCsqCreate(@CurrentUser LoginUserInfo loginUserInfo,
		@RequestBody UserCsqView csqView) throws UserNotFoundException {

		UserCsqDetailDTO userCsqDetailDTO = appCsService.userMyPageCsqCreate(loginUserInfo, csqView);

		return JsonResponse.success(userCsqDetailDTO);
	}

	@LoginUserCheck
	@GetMapping("/read/{csqId}")
	public JsonResponse<UserCsqDetailDTO> userMyPageCsqRead(@CurrentUser LoginUserInfo loginUserInfo,
		@PathVariable(value = "csqId") Integer csqId) throws UserNotFoundException {

		UserCsqDetailDTO userCsqDetailDTO = appCsService.userMyPageCsqRead(loginUserInfo, csqId);

		return JsonResponse.success(userCsqDetailDTO);
	}

	@LoginUserCheck
	@GetMapping("/list/{curPage}")
	public JsonResponse<UserCsqPageDTO> userMyPageCsqList(@CurrentUser LoginUserInfo loginUserInfo,
		@PathVariable(value = "curPage") Integer curPage) throws UserNotFoundException {

		UserCsqPageDTO userCsqPageDTO = appCsService.userMyPageCsqList(loginUserInfo, curPage - 1);

		return JsonResponse.success(userCsqPageDTO);
	}

	@LoginUserCheck
	@GetMapping("/delete/{csqId}")
	public JsonResponse userMyPageCsqDelete(@CurrentUser LoginUserInfo loginUserInfo,
		@PathVariable(value = "csqId") Integer csqId) throws UserNotFoundException {

		appCsService.userMyPageCsqDelete(loginUserInfo, csqId);

		return JsonResponse.success();
	}
}
