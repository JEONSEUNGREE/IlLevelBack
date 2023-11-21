package com.trip.penguin.recommand.room.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trip.penguin.recommand.room.dao.RoomRecDAO;
import com.trip.penguin.recommand.room.service.RoomRecService;
import com.trip.penguin.recommand.room.view.MainRecRoomSchCdt;
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
	public JsonResponse<List<RoomRecDAO>> mainRecRoomList(MainRecRoomSchCdt mainRecRoomSchCdt) {

		List<RoomRecDAO> mainRecRoomList = roomRecService.getMainRecRoomListWithPaging(mainRecRoomSchCdt);

		return JsonResponse.success(mainRecRoomList);
	}

	@GetMapping("/test")
	public JsonResponse<List<Map<String, String>>> test() {
		List<Map<String, String>> result = new ArrayList<>();
		Map<String, String> test = new HashMap<>();
		Map<String, String> test1 = new HashMap<>();
		test.put("test0", "test0");
		test.put("value0", "vaue0");

		test1.put("test1", "test1");
		test1.put("value1", "vaue1");

		result.add(test);
		result.add(test1);

		result.get(0).get(0);

		return null;
	}
}
