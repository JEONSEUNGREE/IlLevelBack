package com.trip.penguin.room.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.trip.penguin.resolver.vo.LoginCompanyInfo;
import com.trip.penguin.room.dto.AppRoomDTO;
import com.trip.penguin.room.view.AppRoomView;

public interface AppRoomService {

	AppRoomDTO companyRoomCreate(LoginCompanyInfo loginCompanyInfo, AppRoomView appRoomView,
		MultipartFile thumbNailImg, List<MultipartFile> roomImgList);

	AppRoomDTO getRoomDetail(Long roomId);
}
