package com.trip.penguin.user.service;

import org.springframework.web.multipart.MultipartFile;

import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.user.dto.UserMyPageDTO;
import com.trip.penguin.user.view.UserMyPageProfileDTO;
import com.trip.penguin.user.view.UserMyPageView;

public interface UserMyPageService {

	UserMyPageDTO userMyPageModify(LoginUserInfo loginUserInfo, UserMyPageView userMyPageView,
		MultipartFile multipartFile) throws IllegalAccessException;

	UserMyPageProfileDTO getUserMyPageProfile(LoginUserInfo loginUserInfo) throws IllegalAccessException;

	String checkEmailValidate(String email);
}
