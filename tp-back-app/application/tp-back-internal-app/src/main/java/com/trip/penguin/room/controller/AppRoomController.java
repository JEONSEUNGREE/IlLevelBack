package com.trip.penguin.room.controller;


import com.trip.penguin.company.dto.AppCompanyDTO;
import com.trip.penguin.company.view.AppCompanyView;
import com.trip.penguin.exception.UserNotFoundException;
import com.trip.penguin.interceptor.annotation.LoginCompanyCheck;
import com.trip.penguin.resolver.annotation.CurrentCompany;
import com.trip.penguin.resolver.vo.LoginCompanyInfo;
import com.trip.penguin.response.JsonResponse;
import com.trip.penguin.room.dto.AppRoomDTO;
import com.trip.penguin.room.service.AppRoomService;
import com.trip.penguin.room.view.AppRoomView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/room")
public class AppRoomController {

    private AppRoomService appRoomService;

    @Autowired
    public AppRoomController(AppRoomService appRoomService) {
        this.appRoomService = appRoomService;
    }

    @LoginCompanyCheck
    @PostMapping("/create")
    public JsonResponse<AppRoomDTO> appCompanyCreate(@CurrentCompany LoginCompanyInfo loginCompanyInfo,
                                                        @RequestPart(value = "appRoomView") AppRoomView appRoomView,
                                                        @RequestPart(value = "thumbNailImg", required = false) MultipartFile thumbNailImg,
                                                        @RequestPart(value = "roomImgList", required = false) List<MultipartFile> roomImgList)
            throws UserNotFoundException {

        AppRoomDTO appRoomDTO = appRoomService.companyRoomCreate(loginCompanyInfo, appRoomView, thumbNailImg, roomImgList);

        return JsonResponse.success(appRoomDTO);
    }
}
