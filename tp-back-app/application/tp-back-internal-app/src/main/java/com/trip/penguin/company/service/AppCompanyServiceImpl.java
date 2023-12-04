package com.trip.penguin.company.service;

import com.trip.penguin.company.domain.CompanyMS;
import com.trip.penguin.company.dto.AppCompanyDTO;
import com.trip.penguin.company.view.AppCompanyView;
import com.trip.penguin.resolver.vo.LoginCompanyInfo;
import com.trip.penguin.util.ImgUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class AppCompanyServiceImpl implements AppCompanyService {


    @Value("file.request.company-path")
    private String defaultCompanyImgRoute;

    private CompanyService companyService;

    private ImgUtils imgUtils;

    @Autowired
    public AppCompanyServiceImpl(CompanyService companyService, ImgUtils imgUtils) {
        this.companyService = companyService;
        this.imgUtils = imgUtils;
    }

    @Override
    public AppCompanyDTO appCompanyModify(LoginCompanyInfo loginCompanyInfo, AppCompanyView appCompanyView, MultipartFile multipartFile) throws IOException {

        String companyImgUUID = "";
        AppCompanyDTO appCompanyDTO = new AppCompanyDTO();

        try {

            CompanyMS companyInfo = companyService.getCompanyByComEmail(loginCompanyInfo.getComEmail()).orElseThrow();

            companyInfo.setComAddress(appCompanyView.getComAddress());

            companyImgUUID = imgUtils.saveAndChangeFile(multipartFile, companyInfo, defaultCompanyImgRoute);

            companyService.updateCompanyInfo(companyInfo);

            BeanUtils.copyProperties(companyInfo, appCompanyDTO);

        } catch (Exception e) {

            imgUtils.deleteFile(companyImgUUID, defaultCompanyImgRoute);
            throw new RuntimeException("회사 정보 수정 실패");
        }

        return appCompanyDTO;
    }
}
