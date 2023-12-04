package com.trip.penguin.room.service;

import com.trip.penguin.resolver.vo.LoginCompanyInfo;
import com.trip.penguin.room.dto.AppRoomDTO;
import com.trip.penguin.room.view.AppRoomView;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AppRoomService {

    AppRoomDTO companyRoomCreate(LoginCompanyInfo loginCompanyInfo, AppRoomView appRoomView,
                                 MultipartFile thumbNailImg, List<MultipartFile> roomImgList);
}
