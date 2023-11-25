package com.trip.penguin.user.controller;


import com.trip.penguin.interceptor.annotation.LoginCheck;
import com.trip.penguin.resolver.annotation.CurrentUser;
import com.trip.penguin.resolver.vo.LoginInfo;
import com.trip.penguin.response.JsonResponse;
import com.trip.penguin.user.dto.UserMyPageDto;
import com.trip.penguin.user.service.UserMyPageService;
import com.trip.penguin.user.view.UserMyPageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usr/mypage")
public class UserMyPageController {

    private final UserMyPageService userMyPageService;

    @Autowired
    public UserMyPageController(UserMyPageService userMyPageService) {
        this.userMyPageService = userMyPageService;
    }

//    @LoginCheck
    @PostMapping("/modify")
    public JsonResponse<UserMyPageDto> userMyPageModify(@CurrentUser LoginInfo loginInfo, @RequestBody UserMyPageView userMyPageView) throws IllegalAccessException {

        UserMyPageDto userMyPageDto = userMyPageService.userMyPageModify(loginInfo, userMyPageView);

        return JsonResponse.success(userMyPageDto);
    }


}
