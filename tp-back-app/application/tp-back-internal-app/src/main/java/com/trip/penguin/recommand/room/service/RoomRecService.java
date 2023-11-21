package com.trip.penguin.recommand.room.service;

import java.util.List;

import com.trip.penguin.recommand.room.dao.RoomRecDAO;
import com.trip.penguin.recommand.room.view.MainRecRoomSchCdt;

public interface RoomRecService {

	public List<RoomRecDAO> getMainRecRoomListWithPaging(MainRecRoomSchCdt mainRecRoomSchCdt);
}
