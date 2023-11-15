package com.trip.penguin.user.repository;

import com.trip.penguin.user.domain.UserMS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMSRepository extends JpaRepository<UserMS, Long> {
}