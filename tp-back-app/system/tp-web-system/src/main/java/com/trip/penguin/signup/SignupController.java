package com.trip.penguin.signup;

import com.trip.penguin.account.service.CompanyUserService;
import com.trip.penguin.account.service.DefaultUserService;
import com.trip.penguin.response.JsonResponse;
import com.trip.penguin.account.dto.SignUpCompanyDTO;
import com.trip.penguin.account.dto.SignUpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupController {

    private final DefaultUserService defaultUserService;

    private final CompanyUserService companyUserService;

    @PostMapping(value = "/signup")
    public JsonResponse<HttpStatus> signUpUser(@RequestBody SignUpDTO signUpDTO) {

        defaultUserService.signUpUser(signUpDTO);

        return JsonResponse.success();
    }

    @PostMapping(value = "/com/signup")
    public JsonResponse<HttpStatus> signUpCompany(@RequestBody SignUpCompanyDTO signUpCompanyDTO) {

        companyUserService.signUpCompany(signUpCompanyDTO);

        return JsonResponse.success();
    }
}
