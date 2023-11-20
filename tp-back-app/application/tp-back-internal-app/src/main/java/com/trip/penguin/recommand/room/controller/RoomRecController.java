package com.trip.penguin.recommand.room.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.response.JsonResponse;

@RestController
@RequestMapping(value = "/rec/room")
public class RoomRecController {

	@GetMapping("/main")
	public JsonResponse recommandRoomMain() {

		return JsonResponse.builder()
			.message(CommonConstant.SUCCESS.name())
			.result(CommonConstant.SUCCESS)
			.data("String")
			.build();
	}
}
