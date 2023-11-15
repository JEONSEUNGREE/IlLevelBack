package com.trip.penguin.user.service;

import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.repository.UserMSRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMSRepository userMSRepository;

    @Override
    public UserMS signUpUser(UserMS userMS) {
        return userMSRepository.save(userMS);
    }

    @Override
    public Optional<UserMS> getUser(UserMS userMS) {
        return userMSRepository.findById(userMS.getId());
    }

    @Override
    public UserMS updateUser(UserMS userMS) {
        return userMSRepository.save(userMS);
    }

    @Override
    public void deleteUser(UserMS userMS) {
        userMSRepository.deleteById(userMS.getId());
    }
}
