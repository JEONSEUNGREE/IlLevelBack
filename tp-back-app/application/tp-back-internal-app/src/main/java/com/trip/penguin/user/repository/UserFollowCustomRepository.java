package com.trip.penguin.user.repository;


import com.trip.penguin.follow.dto.UserFollowDTO;
import com.trip.penguin.follow.view.UserFollowSchCdtView;

import java.util.List;

public interface UserFollowCustomRepository {

    List<UserFollowDTO> getUserFollowList(Long userId, UserFollowSchCdtView userFollowSchCdtView);

    Integer getUserFollowCount(Long userId);

}
