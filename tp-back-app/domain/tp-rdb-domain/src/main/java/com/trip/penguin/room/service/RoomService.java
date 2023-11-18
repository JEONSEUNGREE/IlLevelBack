package com.trip.penguin.room.service;

import java.util.List;
import java.util.Optional;

import com.trip.penguin.company.domain.CompanyMS;
import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.room.domain.RoomPicMS;

public interface RoomService {

	public RoomMS createRoom(RoomMS roomMS, CompanyMS companyMS, List<RoomPicMS> roomPicMSList);

	public Optional<RoomMS> getRoomById(RoomMS roomMS);

	public List<RoomMS> getRoomPicListByRoomId(RoomMS roomMS);

	public List<RoomMS> getReviewListByRoomId(RoomMS roomMS);

	public Optional<RoomMS> getRoomBookingList(RoomMS roomMS);

	public RoomMS updateRoom(RoomMS roomMS);

	public void deleteRoom(RoomMS roomMS);

}
