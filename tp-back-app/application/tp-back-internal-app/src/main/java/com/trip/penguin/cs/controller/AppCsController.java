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
import com.trip.penguin.interceptor.annotation.LoginCheck;
import com.trip.penguin.resolver.annotation.CurrentUser;
import com.trip.penguin.resolver.vo.LoginInfo;
import com.trip.penguin.response.JsonResponse;

@RestController
@RequestMapping(value = "/cs")
public class AppCsController {

	private final AppCsService appCsService;

	@Autowired
	public AppCsController(AppCsService appCsService) {
		this.appCsService = appCsService;
	}

	@LoginCheck
	@PostMapping("/create")
	public JsonResponse<UserCsqDetailDTO> userMyPageCsqCreate(@CurrentUser LoginInfo loginInfo,
		@RequestBody UserCsqView csqView) throws UserNotFoundException {

		UserCsqDetailDTO userCsqDetailDTO = appCsService.userMyPageCsqCreate(loginInfo, csqView);

		return JsonResponse.success(userCsqDetailDTO);
	}

	@LoginCheck
	@GetMapping("/read/{csqId}")
	public JsonResponse<UserCsqDetailDTO> userMyPageCsqRead(@CurrentUser LoginInfo loginInfo,
		@PathVariable(value = "csqId") Integer csqId) throws UserNotFoundException {

		UserCsqDetailDTO userCsqDetailDTO = appCsService.userMyPageCsqRead(loginInfo, csqId);

		return JsonResponse.success(userCsqDetailDTO);
	}

	@LoginCheck
	@GetMapping("/list/{curPage}")
	public JsonResponse<UserCsqPageDTO> userMyPageCsqList(@CurrentUser LoginInfo loginInfo,
		@PathVariable(value = "curPage") Integer curPage) throws UserNotFoundException {

		UserCsqPageDTO userCsqPageDTO = appCsService.userMyPageCsqList(loginInfo, curPage - 1);

		return JsonResponse.success(userCsqPageDTO);
	}

	@LoginCheck
	@GetMapping("/delete/{csqId}")
	public JsonResponse userMyPageCsqDelete(@CurrentUser LoginInfo loginInfo,
		@PathVariable(value = "csqId") Integer csqId) throws UserNotFoundException {

		appCsService.userMyPageCsqDelete(loginInfo, csqId);

		return JsonResponse.success();
	}
}
