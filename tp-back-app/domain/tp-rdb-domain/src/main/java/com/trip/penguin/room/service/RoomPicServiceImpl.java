package com.trip.penguin.room.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.room.domain.RoomPicMS;
import com.trip.penguin.room.repository.RoomPicMsRepository;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class RoomPicServiceImpl implements RoomPicService {

	private RoomPicMsRepository roomPicMsRepository;

	@Autowired
	public RoomPicServiceImpl(RoomPicMsRepository roomPicMsRepository) {
		this.roomPicMsRepository = roomPicMsRepository;
	}

	@Override
	public List<RoomPicMS> createRoomPics(RoomMS roomMS, List<RoomPicMS> roomPicMSList) {
		/* 객실 사진 정보에 객실 정보 추가 */
		roomMS.addRoomPics(roomPicMSList);

		return roomPicMsRepository.saveAll(roomPicMSList);
	}

	@Override
	public List<RoomPicMS> getRoomPics(RoomMS roomMS) {
		return roomPicMsRepository.getRoomPicListByRoomMs(roomMS);
	}

	@Override
	public RoomPicMS getRoomThumbnail(RoomMS roomMS) {
		return roomPicMsRepository.getRoomThumbnailByRoomMs(roomMS);
	}

	@Override
	public List<RoomPicMS> updateRoomPics(RoomMS roomMS, List<RoomPicMS> roomPicMSList) {

		/* 추가, 수정, 삭제 할 이미지  */
		List<RoomPicMS> deleteRoomList = roomMS.addModifyRoomPics(roomPicMSList);

		roomPicMsRepository.deleteAll(deleteRoomList);

		return roomPicMsRepository.saveAll(roomPicMSList);
	}

	@Override
	public void deleteRoomPics(RoomMS roomMS, List<RoomPicMS> roomPicMSList) {

		/* 기본 디폴트 이미지 */
		RoomPicMS defaultImg = RoomPicMS.builder()
			.picLocation("default")
			.modifiedDate(LocalDateTime.now())
			.createdDate(LocalDateTime.now())
			.build();
		List<RoomPicMS> defaultImgList = new ArrayList<>();

		defaultImgList.add(defaultImg);

		/* 객실 사진 정보에 객실 정보 추가 */
		roomMS.addRoomPics(defaultImgList);

		roomPicMsRepository.deleteAll(roomPicMSList);
		roomPicMsRepository.save(defaultImg);
	}

	@Override
	public void deleteAllRoomPics(List<RoomPicMS> roomPicMSList) {
		roomPicMsRepository.deleteAll(roomPicMSList);
	}
}
