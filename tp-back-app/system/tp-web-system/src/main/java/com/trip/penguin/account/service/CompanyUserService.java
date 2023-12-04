package com.trip.penguin.account.service;

import com.trip.penguin.company.domain.CompanyMS;
import com.trip.penguin.company.service.CompanyService;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.constant.CommonUserRole;
import com.trip.penguin.account.dto.SignUpCompanyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CompanyUserService {

    private final CompanyService companyService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signUpCompany(SignUpCompanyDTO signUpCompanyDTO) {

        companyService.createCompany(
                CompanyMS.builder()
                        .com_nm(signUpCompanyDTO.getCom_nm())
                        .comImg("default")
                        .userRole(CommonUserRole.ROLE_COM)
                        .comApproval(CommonConstant.N.getName())
                        .comAddress(signUpCompanyDTO.getComAddress())
                        .comPwd(bCryptPasswordEncoder.encode(signUpCompanyDTO.getComPwd()))
                        .comEmail(signUpCompanyDTO.getComEmail())
                        .build());
    }
}
