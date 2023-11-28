package com.trip.penguin.follow.repository;

import com.trip.penguin.follow.domain.FollowMS;
import com.trip.penguin.user.domain.UserMS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FollowMSRepository extends JpaRepository<FollowMS, UserMS> {


    @Modifying
    @Query("delete from FollowMS f where f.followUser.id = :followId and f.userMS.id = :userId")
    void deleteFollowByFollowIdAndUserId(@Param(value = "followId") Long followId, @Param(value = "userId") Long userId);
}