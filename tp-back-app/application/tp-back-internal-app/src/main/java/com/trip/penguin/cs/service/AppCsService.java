package com.trip.penguin.cs.service;

import com.trip.penguin.cs.dto.UserCsqDetailDTO;
import com.trip.penguin.cs.dto.UserCsqPageDTO;
import com.trip.penguin.cs.view.UserCsqView;
import com.trip.penguin.exception.UserNotAllowedException;
import com.trip.penguin.exception.UserNotFoundException;
import com.trip.penguin.resolver.vo.LoginInfo;

public interface AppCsService {

	UserCsqDetailDTO userMyPageCsqCreate(LoginInfo loginInfo, UserCsqView userCsqView) throws UserNotFoundException;

	UserCsqDetailDTO userMyPageCsqRead(LoginInfo loginInfo, Integer csqId);

	UserCsqPageDTO userMyPageCsqList(LoginInfo loginInfo, Integer curPage);

	void userMyPageCsqDelete(LoginInfo loginInfo, Integer csqId) throws UserNotAllowedException;
}
