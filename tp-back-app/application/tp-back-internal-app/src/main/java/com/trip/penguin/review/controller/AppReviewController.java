package com.trip.penguin.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trip.penguin.interceptor.annotation.LoginUserCheck;
import com.trip.penguin.resolver.annotation.CurrentUser;
import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.response.JsonResponse;
import com.trip.penguin.review.dto.AppReviewDTO;
import com.trip.penguin.review.service.AppReviewService;
import com.trip.penguin.review.view.AppReviewView;

@RestController
@RequestMapping(value = "/review")
public class AppReviewController {

	private final AppReviewService appReviewService;

	@Autowired
	public AppReviewController(AppReviewService appReviewService) {
		this.appReviewService = appReviewService;
	}

	@LoginUserCheck
	@PostMapping("/create")
	public JsonResponse<AppReviewDTO> mainRecRoomList(
		@RequestBody AppReviewView appReviewView,
		@CurrentUser LoginUserInfo loginUserInfo) {

		AppReviewDTO appReviewDTO = appReviewService.reviewCreate(appReviewView, loginUserInfo);

		return JsonResponse.success(appReviewDTO);
	}
}
