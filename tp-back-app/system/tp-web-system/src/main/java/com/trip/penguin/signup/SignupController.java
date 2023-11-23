package com.trip.penguin.signup;

import com.trip.penguin.oauth.service.DefaultUserService;
import com.trip.penguin.response.JsonResponse;
import com.trip.penguin.security.dto.SignUpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupController {

    private final DefaultUserService defaultUserService;

    @PostMapping(value = "/signup")
    public JsonResponse<HttpStatus> signUp(@RequestBody SignUpDTO signUpDTO) {

        defaultUserService.signUpUser(signUpDTO);

        return JsonResponse.success();
    }
}
