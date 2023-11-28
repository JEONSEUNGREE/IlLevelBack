package com.trip.penguin.user.service;

import com.trip.penguin.user.dto.*;
import org.springframework.web.multipart.MultipartFile;

import com.trip.penguin.exception.UserNotAllowedException;
import com.trip.penguin.exception.UserNotFoundException;
import com.trip.penguin.resolver.vo.LoginInfo;
import com.trip.penguin.user.view.UserCsqView;
import com.trip.penguin.user.view.UserMyPageView;

public interface UserMyPageService {

	UserMyPageDTO userMyPageModify(LoginInfo loginInfo, UserMyPageView userMyPageView,
		MultipartFile multipartFile) throws IllegalAccessException;

	UserCsqDetailDTO userMyPageCsqCreate(LoginInfo loginInfo, UserCsqView userCsqView) throws UserNotFoundException;

	UserCsqDetailDTO userMyPageCsqRead(LoginInfo loginInfo, Integer csqId);

	UserCsqPageDTO userMyPageCsqList(LoginInfo loginInfo, Integer curPage);

	void userMyPageCsqDelete(LoginInfo loginInfo, Integer csqId) throws UserNotAllowedException;

	UserFollowDTO userMyPageFollowAdd(LoginInfo loginInfo, Integer followId);

	UserFollowListDTO userMyPageFollowList(LoginInfo loginInfo, Integer curPage);

	void userMyPageFollowDelete(LoginInfo loginInfo, Long followId);
}
