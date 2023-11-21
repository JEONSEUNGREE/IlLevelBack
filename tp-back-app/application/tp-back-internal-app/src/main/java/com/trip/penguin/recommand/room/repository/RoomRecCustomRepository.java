package com.trip.penguin.recommand.room.repository;

import java.util.List;

import com.trip.penguin.recommand.room.dao.RoomRecDAO;
import com.trip.penguin.recommand.room.view.MainRecRoomSchCdt;

public interface RoomRecCustomRepository {

	List<RoomRecDAO> getMainRecRoomListWithPaging(MainRecRoomSchCdt mainRecRoomSchCdt);
}
