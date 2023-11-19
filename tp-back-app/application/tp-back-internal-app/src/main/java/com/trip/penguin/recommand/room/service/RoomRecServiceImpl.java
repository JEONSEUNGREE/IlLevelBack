package com.trip.penguin.recommand.room.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.penguin.company.service.CompanyService;
import com.trip.penguin.recommand.room.dto.RoomRecDTO;
import com.trip.penguin.recommand.room.repository.RoomRecRepository;
import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.room.service.RoomService;

@Service
public class RoomRecServiceImpl {

	private final RoomRecRepository roomRecRepository;

	private final RoomService roomService;

	private final CompanyService companyService;

	@Autowired
	public RoomRecServiceImpl(RoomRecRepository roomRecRepository, RoomService roomService,
		CompanyService companyService) {
		this.companyService = companyService;
		this.roomRecRepository = roomRecRepository;
		this.roomService = roomService;
	}

	public List<RoomRecDTO> getRoomRecMain() {
		RoomMS roomMS = new RoomMS();
		roomMS.setId(0L);

		List<RoomMS> roomPicListByRoomId = roomService.getRoomPicListByRoomId(roomMS);
		return null;
	}
}
