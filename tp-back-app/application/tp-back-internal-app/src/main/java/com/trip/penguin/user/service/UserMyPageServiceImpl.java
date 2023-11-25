package com.trip.penguin.user.service;

import com.trip.penguin.resolver.vo.LoginInfo;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.dto.UserMyPageDto;
import com.trip.penguin.user.view.UserMyPageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMyPageServiceImpl implements UserMyPageService {

    private UserService userService;

    @Autowired
    public UserMyPageServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserMyPageDto userMyPageModify(LoginInfo loginInfo, UserMyPageView userMyPageView) throws IllegalAccessException {

        UserMS userMS = userService.getUserByUserEmail(loginInfo.getUserEmail()).orElseThrow(IllegalAccessException::new);

        userMS.setUserLast(userMyPageView.getUserLast());
        userMS.setUserFirst(userMyPageView.getUserFirst());
        userMS.setUserPwd(userMyPageView.getUserPwd());
        userMS.setUserImg(userMyPageView.getUserImg());
        userMS.setUserNick(userMyPageView.getUserNick());
        userMS.setUserCity(userMyPageView.getUserCity());

        UserMS updatedUser = userService.updateUser(userMS);

        return UserMyPageDto.builder()
                .userLast(updatedUser.getUserLast())
                .userFirst(updatedUser.getUserFirst())
                .userNick(updatedUser.getUserNick())
                .userImg(updatedUser.getUserImg())
                .userCity(updatedUser.getUserCity())
                .build();
    }
}
