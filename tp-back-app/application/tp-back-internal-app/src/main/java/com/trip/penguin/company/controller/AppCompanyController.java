package com.trip.penguin.company.controller;


import com.trip.penguin.company.dto.AppCompanyDTO;
import com.trip.penguin.company.service.AppCompanyService;
import com.trip.penguin.company.view.AppCompanyView;
import com.trip.penguin.exception.UserNotFoundException;
import com.trip.penguin.interceptor.annotation.LoginCompanyCheck;
import com.trip.penguin.resolver.annotation.CurrentCompany;
import com.trip.penguin.resolver.vo.LoginCompanyInfo;
import com.trip.penguin.response.JsonResponse;
import com.trip.penguin.user.view.UserMyPageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/company")
public class AppCompanyController {

    private final AppCompanyService appCompanyService;

    @Autowired
    public AppCompanyController(AppCompanyService appCompanyService) {
        this.appCompanyService = appCompanyService;
    }

    @LoginCompanyCheck
    @PostMapping("/modify")
    public JsonResponse<AppCompanyDTO> appCompanyCreate(@CurrentCompany LoginCompanyInfo loginCompanyInfo,
                                                        @RequestPart(value = "appCompanyView") AppCompanyView appCompanyView,
                                                        @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile)
            throws UserNotFoundException, IOException {

        AppCompanyDTO appCompanyDTO = appCompanyService.appCompanyModify(loginCompanyInfo, appCompanyView, multipartFile);

        return JsonResponse.success(appCompanyDTO);
    }
}
