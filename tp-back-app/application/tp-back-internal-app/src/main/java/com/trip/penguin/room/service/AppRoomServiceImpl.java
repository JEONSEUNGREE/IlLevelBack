package com.trip.penguin.room.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trip.penguin.company.domain.CompanyMS;
import com.trip.penguin.company.service.CompanyService;
import com.trip.penguin.exception.NotCompanyUserException;
import com.trip.penguin.resolver.vo.LoginCompanyInfo;
import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.room.dto.AppRoomDTO;
import com.trip.penguin.room.view.AppRoomView;
import com.trip.penguin.util.ImgUtils;

import jakarta.transaction.Transactional;

@Service
public class AppRoomServiceImpl implements AppRoomService {

	private RoomService roomService;

	private RoomPicService roomPicService;

	private CompanyService companyService;

	private ImgUtils imgUtils;

	//    @Value("file.save.company-path")
	private String defaultCompanyImgRoute = "/home/tory/test/TestImg";

	@Autowired
	public AppRoomServiceImpl(RoomService roomService, RoomPicService roomPicService, CompanyService companyService,
		ImgUtils imgUtils) {
		this.roomService = roomService;
		this.roomPicService = roomPicService;
		this.companyService = companyService;
		this.imgUtils = imgUtils;
	}

	@Override
	@Transactional
	public AppRoomDTO companyRoomCreate(LoginCompanyInfo loginCompanyInfo, AppRoomView appRoomView,
		MultipartFile thumbNailImg, List<MultipartFile> roomImgList) {
		CompanyMS companyMS = companyService.getCompanyByComEmail(loginCompanyInfo.getComEmail())
			.orElseThrow(NotCompanyUserException::new);

		/* 복사용 객실 엔티티 생성, 복사용 객실 사진 목록 엔티티 생성 */
		RoomMS roomMS = new RoomMS();
		AppRoomDTO appRoomDTO = new AppRoomDTO();
		String thumbNailFileName = "";
		List<String> roomImgFileNameList = new ArrayList<>();

		/* 썸네일 이미지 이름 추출 */
		if (thumbNailImg != null) {
			thumbNailFileName = imgUtils.fileNameCreate(thumbNailImg.getOriginalFilename());
			roomMS.setThumbNail(thumbNailFileName);
		}

		/* 객실 이미지 이름들 추출 */
		if (roomImgList != null) {
			roomImgFileNameList = roomImgList.stream()
				.map(item -> imgUtils.fileNameCreate(item.getOriginalFilename()))
				.toList();
		}

		/* 썸네일 이미지 파일 이름 저장 */
		BeanUtils.copyProperties(appRoomView, roomMS);

		/* 객실, 객실 사진 생성 */
		RoomMS createdRoom = roomService.createRoom(roomMS, companyMS, thumbNailFileName, roomImgFileNameList);

		/* 파일 저장 */
		try {
			imgUtils.saveAndChangeFile(thumbNailImg, "", thumbNailFileName, defaultCompanyImgRoute);
			if (roomImgList != null) {
				for (int i = 0; i < roomImgList.size(); i++) {
					imgUtils.saveAndChangeFile(roomImgList.get(i), "", roomImgFileNameList.get(i),
						defaultCompanyImgRoute);
				}
			}
		} catch (Exception e) {
			/* 파일 이미지 업로드 실패 시 전체 삭제 할지 OR 부분 파일만 삭제 할지  */
			System.out.println(Arrays.toString(e.getStackTrace()));
		}

		/* 반환 객체 */
		BeanUtils.copyProperties(createdRoom, appRoomDTO);
		appRoomDTO.setComId(createdRoom.getCom().getId());
		appRoomDTO.addRoomPicList(createdRoom.getRoomPicList());

		return appRoomDTO;
	}
}
