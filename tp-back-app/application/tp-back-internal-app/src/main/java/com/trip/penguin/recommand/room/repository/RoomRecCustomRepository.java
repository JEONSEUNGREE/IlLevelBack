package com.trip.penguin.recommand.room.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.trip.penguin.recommand.room.dao.RoomRecDAO;

public interface RoomRecCustomRepository {

	List<RoomRecDAO> getMainRecRoomListWithPaging(Pageable pageable);
}
