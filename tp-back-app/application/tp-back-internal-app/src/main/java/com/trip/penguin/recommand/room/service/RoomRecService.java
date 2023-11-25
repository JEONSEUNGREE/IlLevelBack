package com.trip.penguin.recommand.room.service;

import java.util.List;

import com.trip.penguin.recommand.room.dao.RoomRecDAO;
import com.trip.penguin.recommand.room.view.MainRecRoomSchCdtView;

public interface RoomRecService {

	public List<RoomRecDAO> getMainRecRoomListWithPaging(MainRecRoomSchCdtView mainRecRoomSchCdt);
}
