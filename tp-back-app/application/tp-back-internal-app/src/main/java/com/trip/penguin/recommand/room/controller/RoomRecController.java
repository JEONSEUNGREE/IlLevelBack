package com.trip.penguin.recommand.room.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trip.penguin.recommand.room.dao.RoomRecDAO;
import com.trip.penguin.recommand.room.service.RoomRecService;
import com.trip.penguin.recommand.room.view.MainRecRoomSchCdtView;
import com.trip.penguin.response.JsonResponse;

@RestController
@RequestMapping(value = "/rec/room")
public class RoomRecController {

	private final RoomRecService roomRecService;

	@Autowired
	public RoomRecController(RoomRecService roomRecService) {
		this.roomRecService = roomRecService;
	}

	@PostMapping("/mainList")
	public JsonResponse<List<RoomRecDAO>> mainRecRoomList(@RequestBody MainRecRoomSchCdtView mainRecRoomSchCdt) {

		List<RoomRecDAO> mainRecRoomList = roomRecService.getMainRecRoomListWithPaging(mainRecRoomSchCdt);

		return JsonResponse.success(mainRecRoomList);
	}
}
