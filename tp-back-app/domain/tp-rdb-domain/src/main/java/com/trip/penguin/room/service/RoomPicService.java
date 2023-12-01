package com.trip.penguin.room.service;

import java.util.List;

import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.room.domain.RoomPicMS;

public interface RoomPicService {

	public List<RoomPicMS> createRoomPics(RoomMS roomMS, List<String> roomPicNameList);

	public List<RoomPicMS> getRoomPics(RoomMS roomMS);

	public RoomPicMS getRoomThumbnail(RoomMS roomMS);

	public List<RoomPicMS> updateRoomPics(RoomMS roomMS, List<RoomPicMS> roomPicMSList);

	public void deleteRoomPics(RoomMS roomMS, List<RoomPicMS> roomPicMSList);

	public void deleteAllRoomPics(List<RoomPicMS> roomPicMSList);
}
