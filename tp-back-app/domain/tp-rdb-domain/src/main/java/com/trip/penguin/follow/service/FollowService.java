package com.trip.penguin.follow.service;

import com.trip.penguin.follow.domain.FollowMS;
import com.trip.penguin.user.domain.UserMS;

import java.util.List;

public interface FollowService {


    FollowMS followCreate(FollowMS followMS);

    FollowMS getFollowList(UserMS userMS);

    void deleteFollowByUserIdAndId(Long followId, UserMS userMS);
}
