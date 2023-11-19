package com.trip.penguin.recommand.room.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.recommand.room.repository.RoomCustomRepository;
import com.trip.penguin.recommand.room.service.RoomRecServiceImpl;
import com.trip.penguin.response.JsonResponse;

@RestController
@RequestMapping(value = "/rec/room")
public class RoomRecController {

	private RoomCustomRepository roomCustomRepository;

	private RoomRecServiceImpl roomRecService;

	@Autowired
	public RoomRecController(RoomCustomRepository roomCustomRepository, RoomRecServiceImpl roomRecService) {

		this.roomCustomRepository = roomCustomRepository;
		this.roomRecService = roomRecService;
	}

	@GetMapping("/main")
	public JsonResponse recommandRoomMain() {
		roomRecService.getRoomRecMain();

		roomCustomRepository.getMainRecRoom(PageRequest.of(1, 2));
		return JsonResponse.builder()
			.message(CommonConstant.SUCCESS.name())
			.result(CommonConstant.SUCCESS)
			.data("String")
			.build();
	}
}
