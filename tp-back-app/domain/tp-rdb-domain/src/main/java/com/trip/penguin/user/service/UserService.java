package com.trip.penguin.user.service;

import java.util.Optional;

import com.trip.penguin.user.domain.UserMS;

public interface UserService {

	public UserMS signUpUser(UserMS userMS);

	public Optional<UserMS> getUserByUserEmail(String userEmail);

	public Optional<UserMS> getUser(UserMS userMS);

	public UserMS updateUser(UserMS userMS);

	public void deleteUser(UserMS userMS);

}
