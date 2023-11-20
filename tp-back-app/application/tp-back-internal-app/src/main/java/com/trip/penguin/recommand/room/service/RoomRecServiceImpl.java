package com.trip.penguin.recommand.room.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.penguin.recommand.room.dao.RoomRecDAO;
import com.trip.penguin.recommand.room.repository.RoomRecCustomRepository;
import com.trip.penguin.recommand.room.repository.RoomRecRepository;
import com.trip.penguin.recommand.room.view.MainRecRoomSchCdt;
import com.trip.penguin.room.service.RoomService;

@Service
public class RoomRecServiceImpl implements RoomRecService {

	private final RoomRecRepository roomRecRepository;

	private final RoomRecCustomRepository roomRecCustomRepository;

	private final RoomService roomService;

	@Autowired
	public RoomRecServiceImpl(RoomRecRepository roomRecRepository, RoomRecCustomRepository roomRecCustomRepository,
		RoomService roomService) {
		this.roomRecRepository = roomRecRepository;
		this.roomRecCustomRepository = roomRecCustomRepository;
		this.roomService = roomService;
	}

	/**
	 * 메인 추천 객실 데이터 조회
	 * @param mainRecRoomSchCdt - 검색 조건
	 * @return List<RoomRecDAO>
	 */
	@Override
	public List<RoomRecDAO> getMainRecRoomListWithPaging(MainRecRoomSchCdt mainRecRoomSchCdt) {
		return roomRecCustomRepository.getMainRecRoomListWithPaging(mainRecRoomSchCdt.getPageable());
	}

}
