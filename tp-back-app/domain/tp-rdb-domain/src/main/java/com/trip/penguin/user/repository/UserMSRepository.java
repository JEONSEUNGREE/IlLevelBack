package com.trip.penguin.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trip.penguin.user.domain.UserMS;

public interface UserMSRepository extends JpaRepository<UserMS, Long> {

	public Optional<UserMS> findByUserEmail(String userEmail);
}