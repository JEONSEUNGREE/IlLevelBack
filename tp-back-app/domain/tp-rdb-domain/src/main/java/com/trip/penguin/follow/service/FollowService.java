package com.trip.penguin.follow.service;

import com.trip.penguin.follow.domain.FollowMS;
import com.trip.penguin.user.domain.UserMS;

public interface FollowService {


    public FollowMS setFollow(FollowMS followMS);

    public FollowMS getFollowList(UserMS userMS);
}
