package com.trip.penguin.follow.repository;

import com.trip.penguin.follow.domain.FollowMS;
import com.trip.penguin.user.domain.UserMS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowMSRepository extends JpaRepository<FollowMS, UserMS> {
}