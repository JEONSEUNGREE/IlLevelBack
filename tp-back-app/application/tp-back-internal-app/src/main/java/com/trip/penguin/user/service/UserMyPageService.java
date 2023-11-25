package com.trip.penguin.user.service;

import com.trip.penguin.resolver.vo.LoginInfo;
import com.trip.penguin.user.dto.UserMyPageDto;
import com.trip.penguin.user.view.UserMyPageView;

public interface UserMyPageService {

    UserMyPageDto userMyPageModify(LoginInfo loginInfo, UserMyPageView userMyPageView) throws IllegalAccessException;

}
