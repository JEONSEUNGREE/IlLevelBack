package com.trip.penguin.cs.service;

import com.trip.penguin.cs.dto.UserCsqDetailDTO;
import com.trip.penguin.cs.dto.UserCsqPageDTO;
import com.trip.penguin.cs.view.UserCsqView;
import com.trip.penguin.exception.UserNotAllowedException;
import com.trip.penguin.exception.UserNotFoundException;
import com.trip.penguin.resolver.vo.LoginUserInfo;

public interface AppCsService {

	UserCsqDetailDTO userMyPageCsqCreate(LoginUserInfo loginUserInfo, UserCsqView userCsqView) throws UserNotFoundException;

	UserCsqDetailDTO userMyPageCsqRead(LoginUserInfo loginUserInfo, Integer csqId);

	UserCsqPageDTO userMyPageCsqList(LoginUserInfo loginUserInfo, Integer curPage);

	void userMyPageCsqDelete(LoginUserInfo loginUserInfo, Integer csqId) throws UserNotAllowedException;
}
