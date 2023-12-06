package com.trip.penguin.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trip.penguin.booking.dto.AppBookingDTO;
import com.trip.penguin.booking.service.AppBookingService;
import com.trip.penguin.booking.view.AppBookingView;
import com.trip.penguin.interceptor.annotation.LoginUserCheck;
import com.trip.penguin.resolver.annotation.CurrentUser;
import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.response.JsonResponse;

@RestController
@RequestMapping(value = "/booking")
public class AppBookingController {

	private final AppBookingService appBookingService;

	@Autowired
	public AppBookingController(AppBookingService appBookingService) {
		this.appBookingService = appBookingService;
	}

	@LoginUserCheck
	@PostMapping("/create")
	public JsonResponse<AppBookingDTO> appBookingCreate(
		@CurrentUser LoginUserInfo loginUserInfo,
		@RequestBody AppBookingView appBookingView) {

		AppBookingDTO appBookingDTO = appBookingService.bookingCreate(appBookingView, loginUserInfo);

		return JsonResponse.success(appBookingDTO);
	}
}
