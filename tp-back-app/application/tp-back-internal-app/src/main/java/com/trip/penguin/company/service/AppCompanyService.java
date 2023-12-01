package com.trip.penguin.company.service;

import com.trip.penguin.company.dto.AppCompanyDTO;
import com.trip.penguin.company.view.AppCompanyView;
import com.trip.penguin.resolver.vo.LoginCompanyInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AppCompanyService {

    AppCompanyDTO appCompanyModify(LoginCompanyInfo loginCompanyInfo, AppCompanyView appCompanyView, MultipartFile multipartFile) throws IOException;

}
