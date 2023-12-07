package com.trip.penguin.user.repository;

import java.util.List;

import com.trip.penguin.follow.dto.UserFollowDTO;
import com.trip.penguin.follow.view.UserFollowSchCdtView;

public interface UserFollowCustomRepository {

	List<UserFollowDTO> getUserFollowList(Long userId, UserFollowSchCdtView userFollowSchCdtView);

	Integer getUserFollowCount(Long userId);

	Integer getUserFollowerCount(Long userId);

}
