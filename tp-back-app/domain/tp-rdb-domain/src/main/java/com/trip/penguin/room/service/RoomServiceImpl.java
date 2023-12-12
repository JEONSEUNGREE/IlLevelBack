package com.trip.penguin.room.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.penguin.company.domain.CompanyMS;
import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.room.repository.RoomMSRepository;
import com.trip.penguin.room.repository.RoomPicMsRepository;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class RoomServiceImpl implements RoomService {

	private RoomMSRepository roomMSRepository;

	private RoomPicMsRepository roomPicMsRepository;

	private RoomPicService roomPicService;

	@Autowired
	public RoomServiceImpl(RoomMSRepository roomMSRepository, RoomPicMsRepository roomPicMsRepository,
		RoomPicService roomPicService) {
		this.roomMSRepository = roomMSRepository;
		this.roomPicMsRepository = roomPicMsRepository;
		this.roomPicService = roomPicService;
	}

	@Override
	public RoomMS createRoom(RoomMS roomMS, CompanyMS companyMS, String thumbNail, List<String> roomImgFileNameList) {

		roomMS.createRoomMs();

		/* 객실에 해당 되는 회사 생성 */
		roomMS.setCompanyInfo(companyMS);
		roomMS.setThumbNailOrDefaultImg(thumbNail);
		RoomMS createdRoom = roomMSRepository.save(roomMS);

		/* 객실 이미지 생성 없는 경우 기본 이미지 생성 */
		roomPicService.createRoomPics(roomMS, roomImgFileNameList);
		return createdRoom;
	}

	@Override
	public Optional<RoomMS> getRoomById(RoomMS roomMS) {
		return roomMSRepository.findById(roomMS.getId());
	}

	@Override
	public RoomMS updateRoom(RoomMS roomMS) {
		return roomMSRepository.save(roomMS);
	}

	@Override
	public List<RoomMS> getRoomPicListByRoomId(RoomMS roomMS) {
		return null;
	}

	@Override
	public List<RoomMS> getReviewListByRoomId(RoomMS roomMS) {
		return roomMSRepository.getReviewListByRoomId(roomMS.getId());
	}

	@Override
	public RoomMS getRoomMsAndRoomPicByRoomId(Long roomId) {
		return roomMSRepository.getRoomMsAndRoomPicByRoomId(roomId);
	}

	@Override
	public Optional<RoomMS> getRoomBookingList(RoomMS roomMS) {
		return null;
	}

	@Override
	public void deleteRoom(RoomMS roomMS) {
		roomMSRepository.deleteById(roomMS.getId());
	}

}
