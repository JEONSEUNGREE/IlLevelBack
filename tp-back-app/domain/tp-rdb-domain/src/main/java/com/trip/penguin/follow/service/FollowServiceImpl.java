package com.trip.penguin.follow.service;

import com.trip.penguin.exception.UserNotAllowedException;
import com.trip.penguin.follow.domain.FollowMS;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.follow.repository.FollowMSRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowMSRepository followMSRepository;

    /**
     * 팔로워 등록
     * @param followMS - 팔로워 엔티티
     * @return FollowMS
     */
    @Override
    public FollowMS followCreate(FollowMS followMS) {
        return followMSRepository.save(followMS);
    }

    @Override
    public FollowMS getFollowList(UserMS userMS) {
        return followMSRepository.getReferenceById(userMS);
    }

    @Override
    public void deleteFollowByUserIdAndId(Long followId, UserMS userMs) {
        followMSRepository.deleteFollowByFollowIdAndUserId(followId, userMs.getId());
    }

}
