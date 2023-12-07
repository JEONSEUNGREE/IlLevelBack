package com.trip.penguin.follow.service;

import com.trip.penguin.follow.dto.UserFollowDTO;
import com.trip.penguin.follow.dto.UserFollowListDTO;
import com.trip.penguin.resolver.vo.LoginUserInfo;

public interface AppFollowService {

	UserFollowDTO userMyPageFollowAdd(LoginUserInfo loginUserInfo, Integer followId);

	UserFollowListDTO userMyPageFollowList(LoginUserInfo loginUserInfo, Integer curPage);

	void userMyPageFollowDelete(LoginUserInfo loginUserInfo, Long followId);

	Integer getUserFollowerCnt(Long userId);

	Integer getUserFollowCnt(Long userId);
}
