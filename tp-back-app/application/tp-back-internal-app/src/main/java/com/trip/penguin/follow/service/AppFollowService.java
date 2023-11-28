package com.trip.penguin.follow.service;

import com.trip.penguin.resolver.vo.LoginInfo;
import com.trip.penguin.user.dto.UserFollowDTO;
import com.trip.penguin.user.dto.UserFollowListDTO;

public interface AppFollowService {

	UserFollowDTO userMyPageFollowAdd(LoginInfo loginInfo, Integer followId);

	UserFollowListDTO userMyPageFollowList(LoginInfo loginInfo, Integer curPage);

	void userMyPageFollowDelete(LoginInfo loginInfo, Long followId);
}
