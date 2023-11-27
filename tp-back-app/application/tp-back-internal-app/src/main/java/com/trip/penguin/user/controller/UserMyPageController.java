package com.trip.penguin.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trip.penguin.exception.UserNotFoundException;
import com.trip.penguin.interceptor.annotation.LoginCheck;
import com.trip.penguin.resolver.annotation.CurrentUser;
import com.trip.penguin.resolver.vo.LoginInfo;
import com.trip.penguin.response.JsonResponse;
import com.trip.penguin.user.dto.UserCsqDetailDTO;
import com.trip.penguin.user.dto.UserCsqPageDTO;
import com.trip.penguin.user.dto.UserMyPageDTO;
import com.trip.penguin.user.service.UserMyPageService;
import com.trip.penguin.user.view.UserCsqView;
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

	@LoginCheck
	@PostMapping("/cs/create")
	public JsonResponse<UserCsqDetailDTO> userMyPageCsqCreate(@CurrentUser LoginInfo loginInfo,
		@RequestBody UserCsqView csqView) throws UserNotFoundException {

		UserCsqDetailDTO userCsqDetailDTO = userMyPageService.userMyPageCsqCreate(loginInfo, csqView);

		return JsonResponse.success(userCsqDetailDTO);
	}

	@LoginCheck
	@GetMapping("/cs/read/{csqId}")
	public JsonResponse<UserCsqDetailDTO> userMyPageCsqRead(@CurrentUser LoginInfo loginInfo,
		@PathVariable(value = "csqId") Integer csqId) throws UserNotFoundException {

		UserCsqDetailDTO userCsqDetailDTO = userMyPageService.userMyPageCsqRead(loginInfo, csqId);

		return JsonResponse.success(userCsqDetailDTO);
	}

	@LoginCheck
	@GetMapping("/cs/list/{curPage}")
	public JsonResponse<UserCsqPageDTO> userMyPageCsqList(@CurrentUser LoginInfo loginInfo,
		@PathVariable(value = "curPage") Integer curPage) throws UserNotFoundException {

		UserCsqPageDTO userCsqPageDTO = userMyPageService.userMyPageCsqList(loginInfo, curPage - 1);

		return JsonResponse.success(userCsqPageDTO);
	}

	@LoginCheck
	@GetMapping("/cs/delete/{csqId}")
	public JsonResponse userMyPageCsqDelete(@CurrentUser LoginInfo loginInfo,
		@PathVariable(value = "csqId") Integer csqId) throws UserNotFoundException {

		userMyPageService.userMyPageCsqDelete(loginInfo, csqId);

		return JsonResponse.success();
	}

}
