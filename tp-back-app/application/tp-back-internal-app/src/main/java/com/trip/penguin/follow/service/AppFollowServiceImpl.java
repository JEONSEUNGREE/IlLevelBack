package com.trip.penguin.follow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.penguin.exception.UserNotFoundException;
import com.trip.penguin.follow.domain.FollowMS;
import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.follow.dto.UserFollowDTO;
import com.trip.penguin.follow.dto.UserFollowListDTO;
import com.trip.penguin.user.repository.UserFollowCustomRepository;
import com.trip.penguin.user.service.UserService;
import com.trip.penguin.follow.view.UserFollowSchCdtView;

@Service
public class AppFollowServiceImpl implements AppFollowService {

	private UserService userService;

	private FollowService followService;

	private UserFollowCustomRepository userFollowCustomRepository;

	@Autowired
	public AppFollowServiceImpl(UserService userService, FollowService followService,
		UserFollowCustomRepository userFollowCustomRepository) {
		this.userService = userService;
		this.followService = followService;
		this.userFollowCustomRepository = userFollowCustomRepository;
	}

	@Override
	public UserFollowDTO userMyPageFollowAdd(LoginUserInfo loginUserInfo, Integer followId) {
		UserMS foundUser = userService.getUserByUserEmail(loginUserInfo.getUserEmail())
			.orElseThrow(UserNotFoundException::new);
		UserMS followUser = userService.getUserByUserId(followId.longValue()).orElseThrow(UserNotFoundException::new);

		FollowMS followMS1 = followService.followCreate(foundUser.addFollow(followUser));

		return UserFollowDTO
			.builder()
			.followId(followUser.getId())
			.userNick(followUser.getUserNick())
			.userImg(followUser.getUserImg())
			.build();
	}

	@Override
	public UserFollowListDTO userMyPageFollowList(LoginUserInfo loginUserInfo, Integer curPage) {
		UserMS foundUser = userService.getUserByUserEmail(loginUserInfo.getUserEmail())
			.orElseThrow(UserNotFoundException::new);

		// 가져올 크기 기본 설정
		Integer defaultPageSize = 5;

		List<UserFollowDTO> userFollowList =
			userFollowCustomRepository.getUserFollowList(foundUser.getId(),
				UserFollowSchCdtView.builder().curPage(curPage).pageSize(defaultPageSize).build());

		Integer userFollowCount = userFollowCustomRepository.getUserFollowCount(foundUser.getId());

		return UserFollowListDTO.builder()
			.userFollowList(userFollowList)
			.followCount(userFollowCount)
			.totalPage((int)Math.ceil((double)userFollowCount / (double)defaultPageSize))
			.build();
	}

	@Override
	public void userMyPageFollowDelete(LoginUserInfo loginUserInfo, Long followId) {
		UserMS foundUser = userService.getUserByUserEmail(loginUserInfo.getUserEmail())
			.orElseThrow(UserNotFoundException::new);

		followService.deleteFollowByUserIdAndId(followId, foundUser);
	}
}
