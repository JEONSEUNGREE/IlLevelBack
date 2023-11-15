package com.trip.penguin.user.service;

import com.trip.penguin.user.domain.UserMS;

import java.util.Optional;


public interface UserService {

    public UserMS signUpUser(UserMS userMS);

    public Optional<UserMS> getUser(UserMS userMS);

    public UserMS updateUser(UserMS userMS);

    public void deleteUser(UserMS userMS);

}
